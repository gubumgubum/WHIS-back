package org.example.whisauth.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private Integer grade;
    private String profileImageUrl;

    protected User() {}

    public User(
            String email,
            String password,
            String name,
            Integer grade,
            String profileImageUrl
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.grade = grade;
        this.profileImageUrl = profileImageUrl;
    }
}