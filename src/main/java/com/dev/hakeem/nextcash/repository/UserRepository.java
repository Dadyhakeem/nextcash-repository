package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("select u.role from User u where u.email like :email")
    Role findRoleByEmail(@Param("email") String email);
}
