package com.spring_jwt.dto;

import com.spring_jwt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
public class RegisterRequest {

    @NotNull
    @NotBlank
    @Size(min=1,max=15,message = "please insert valid name between '${min}' and '${max}' characters")
    private String name;


    @NotBlank
    @NotNull
    @Size(min=1,max=15,message = "Last name ${validatedValue} must be between '${min}' and '${max}' characters")
    private String lastName;

    @NotBlank
    @NotNull
    @Size(min=1,max=15,message = "username ${validatedValue} must be between '${min}' and '${max}' characters")
    private String userName;

    @NotBlank
    @NotNull
    @Size(min=1,max=15,message = "Password ${validatedValue} must be between '${min}' and '${max}' characters")
    private String password;

    private Set<Role> roles;

    public RegisterRequest(String name, String lastName, String userName, String password) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
}
