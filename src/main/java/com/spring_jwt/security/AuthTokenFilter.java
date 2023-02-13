package com.spring_jwt.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    /* Bu classta kendi Filtremizi yazacağız ve filtremizi Springe bu classı extends
    edip methodunu override ederek sağlayacağız
     Springin kendi filtresini override ettik kendi filltremizi oluşturacağız */
    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }
    //-----------------FİlTRE override methodu-----------------------
    //bu method bize 3 parametre ile geliyor requestlerimizi ve response larımızı kendi filtremize katıp sonrasında
    //Springin filterchain yapısına tanıtacağız!!

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /*
        Requestimizin header kısmında bulunan ve Authorization "key" i ile bize gelen
        "Bearer ......" şeklindeki string ifadeden tokenımızı çekecek bir method oluşturduk
        tokenimizi çektik ve null durumunu handle ettik. ayrıca oluşturduğumuz jwtUtils classında
        kendi yazdığımız validatiton methodumuza arguman olarak tokenimizi verdik.
        aynı classta oluşturduğumuz username i tokenden alabildiğimiz methodumuza  token i verdik ve
        bize User değil Sprigin tanıdığı "UserDetails" verisi return etti.
        sonrasında spring security içerisinde bulunan kimlik doğrulama classı "UsernamePasswordAuthenticationToken"
        veri yapısında bir token oluşturup constructoruna az önce aldığımız UserDetails objesini setledik


         */

        String jwtToken=parseJwt(request);
        try {
            if(jwtToken!=null && jwtUtils.validateToken(jwtToken)){
                String userName=jwtUtils.getUserNameFromToken(jwtToken);
                UserDetails userDetails= userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                        null,
                                userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        //Spring yapısındaki filtre zincirine kendi oluşturduğumuz filtremizi request ve response ları tanıtarak
        //ekledik
filterChain.doFilter(request,response);
    }
    //Requestimizin header kısmında bulunan ve Authorization "key" i ile bize gelen
    //        Bearer ...... şeklindeki string ifadeden tokeni alıp return eden bir
    //method oluşturduk
    private String parseJwt(HttpServletRequest request){
       String header= request.getHeader("Authorization");
       if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.substring(7);
        }
return null;
    }


    /*
    shouldNotFilter methodunu override ettik ve basic auth işlemmlerinde request üzerinden
    yaptığımız antMatchers işlemlerimizi yaptık.
    Register ve login endpointi ile gelen requestleri filtreden muaf tuttuk!!!
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher=new AntPathMatcher();

        return antPathMatcher.match("/register",request.getServletPath()) ||
                antPathMatcher.match("/login",request.getServletPath());
    }
}
