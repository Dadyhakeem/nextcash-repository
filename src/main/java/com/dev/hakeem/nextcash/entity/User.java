package com.dev.hakeem.nextcash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements Serializable {
    private static final long serialversion= 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email",nullable = false,length = 100)
    private  String email;
    @Column(name = "Password",nullable = false)
    private String password;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private  Timestamp updated_at;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
