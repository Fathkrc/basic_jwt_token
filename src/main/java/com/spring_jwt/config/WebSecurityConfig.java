package com.spring_jwt.config;

import com.spring_jwt.security.AuthTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    bu classta appliation config ayarlarımızı yapacağız önce deprecated classtan extend ederek yapacağım
    sonrasında yeni Spring yapısına adapte etmeye çalışacağım
    iki configure methodunu override ettik. birisinin parametresi HttpSecurity ,
    diğerinin ise Authorization veri tipinde.

     */

    private final AuthTokenFilter authTokenFilter;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register","/login").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        /*
        bu methodda HttpSecurity veri tipinde parametremiz üzerinden Session ayarını ve
        antMatchers ayarlarımızı authTokenFilter classsında belirtemize rağmen
        tekrar yaptık ??
        kendimiz oluşturduğumuz Filtremizi de springe tanıttık addFilterBefore() sanıyorum
        filtre hiyerarşisi ile alakalı
         */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder());
        // UserDetailsService ve password encoderımızı AuthenticationManagerBuildera tanıttık

    }
}
