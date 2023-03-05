package com.spring_jwt.security;


import com.spring_jwt.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    /* ilk olarak jwt üretecen bir method oluşturacağız
    2. olarak üretilen ya da gelen jwt tokenin uygunluğunu kontrol edeceğiz
    3. olarak da üretilmiş bir jwt token içerisinde parametre olarak kullandığımız fieldımızı yeniden çekebilecek
    bir method yazacağız


     */
    private String jwtSecret= "sboot";

    private long jwtExpirationMs= 86400000;

    // ----------------JWT TOKENİMİZİ OLUŞTURUYORUZ ----------------------------


  public String generateToken(Authentication auth){
        UserDetailsImpl userDetails= (UserDetailsImpl)auth.getPrincipal();
        // bu method UserDetailsService daya türünde login olan kullanıcıyı return ediyor
        // kendi classımızın parentı bir data tipinde geldiği için data türünü assign
        //ederken wrapper yapısını kullandık.
  return Jwts.builder() // methodumuz token return ediyor
         .setSubject(userDetails.getUsername())// username i çektik
         .setIssuedAt(new Date())//üretilme tarihi oluşturduk
         .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))//aynı tarihe 24 saat ekleyerek tokenin geçerlilik süresini düzenledik
         .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();//şifreleme algoritmasını set ettik ve compact ile bilgileri toparladık
    }//geri döndürülemeyen bir token oluşturduk


    //--------------- TOKEN KONTROLÜNÜ YAPACAK METHODU OLUŞTURUYORUZ-----------------------------
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
            /*
      parser ile başlayıp setSigningKey methodu ile bizim şifreleme algoritmasına soktuğumuz
      Stringimizi arguman olarak veriyoruz ve parseClaimsJws methoduna da token argumanımızı veriyoruz
      tokenin bulunamaması yapısı bozulmuş olması valid olmaması gibi durumları
      if döngüsü yerine try catcch ile handle ettik en sonunda eğer return işlemi olmaz ve exception fırlaması durumunda
      false return etmesini sağladık fakat uygulamamızı tıkamadık yalnızca exceptionların
      açıklamalarını görmek istdik.
       */
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
      return false;
    }

    // ---------------------JWT TOKEN İÇERİSİNDE GİZLENEN USERNAME'I  ÇEKECEK METHOD----------------------

    public String getUserNameFromToken(String token){
      return Jwts
              .parser().setSigningKey(jwtSecret)
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
      /*
      Yine Jwts yapısı üzerinden gidiyoruz ,
      JWT  token üretirken içine set ettiğimiz string değerini ve parametrede bulunan token'ın String
      değerini parseClaimsJws() methoduna yerleştirdik ve methodun body sinden setSubject() içerisine
      gönderdiğimiz variable'ı getSubject() ile çağırıyoruz.
      Password geri çağırılabilir olmasın diye userName ini aldık
      username unique olduğu için her kullanıcının tokeni unique oluyor!!!
       */

    }
}
