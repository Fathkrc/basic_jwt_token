package com.spring_jwt.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@Data
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25,nullable = false)
    @NotBlank
    private String firstName;
    @Column(length = 25,nullable = false)
    private String lastName;
    @Column(length = 25,nullable = false, unique = true)
    private String userName;
    @Column(length = 255,nullable = false)// length 255 olma sebebi; password
    // haslemeye girerse karakter sayısı daha fazla olacaktır
    private String password;

    @JoinTable(name="tbl_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String userName, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }
}
