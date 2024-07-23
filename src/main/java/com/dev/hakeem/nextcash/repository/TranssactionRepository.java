package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranssactionRepository extends JpaRepository<Transaction , Long> {
}
