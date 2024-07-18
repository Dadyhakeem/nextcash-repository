package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
