package com.dev.hakeem.nextcash.entity;

import com.dev.hakeem.nextcash.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "tb_users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    private static final long serialversion= 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(name = "username")
    private String username;

    @Email(message = "Formato do email esta invalido",regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @Column(name = "email",nullable = false,unique = true, length = 100)
    private  String email;
    @Column(name = "Password",nullable = false, length = 200)
    private String password;
     @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.ROLE_CLIENT;
     @CreatedDate
    @Column(name = "created_at")
    private Timestamp created_at;
     @LastModifiedDate
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
