package com.spring_jwt.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring_jwt.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String userName;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /*
    pojo classımızı UserDetails veri tipine dönüştürerek Springe tanıtacağız
    UserDetails(user.getName , getUsername vs) yapısını burada static olarak oluşturduk
    ve bu sayede clas adı ile diğer classımızda çekebileceğiz
     */
    public static UserDetailsImpl build(User user){
       List<SimpleGrantedAuthority> list= user.getRoles().stream()
                .map(t-> new SimpleGrantedAuthority(t.getName().name()))
                .collect(Collectors.toList());
       // User pojo classımızda istenen Role verilerimizi Granted authority tipine dönüştürdük
       return new UserDetailsImpl(user.getId(),
                            user.getUserName(),
                            user.getPassword(),
                            list );
       //burada Pojo classımızın fieldları ile bir UserService objesi oluşturduk ve return ettik

    }
    /*
    override ettiğimiz metodları classımızın fieldları ile setledik
    username password vs boolean methodları true a çektik
     */



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
