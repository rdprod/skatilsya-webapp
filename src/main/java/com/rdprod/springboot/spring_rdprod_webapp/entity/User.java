package com.rdprod.springboot.spring_rdprod_webapp.entity;

import com.rdprod.springboot.spring_rdprod_webapp.validation.PasswordsEqualConstraint;
import com.rdprod.springboot.spring_rdprod_webapp.validation.UsernameExist;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@PasswordsEqualConstraint(message = "Пароли не совпадают!")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotBlank(message = "Имя не может быть пустым!")
    @UsernameExist(message = "Такое имя уже занято!")
    private String username;

    @Column(name = "email")
    @Pattern(regexp = ".*[a-zA-Z0-9]@.*[a-zA-Z0-9.]\\..*[a-zA-Z]",
            message = "Некорректный E-mail!")
    private String email;

    @Column(name = "password")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}",
            message = "Пароль должен быть минимум 6 символов и состоять из латинских " +
                    "букв нижнего и верхнего регистров, а также цифр!")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Comment> comments;

    @Column(name = "avatar")
    private String avatar;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addCommentToUser(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setUser(this);
    }

    public void addRoleToUser(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public final String rolesAsString() {
        return roles.stream().map(Role::getName)
                .collect(Collectors.joining(", "));
    }
}
