package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense , Long> {
}
