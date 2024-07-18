package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget ,Long> {
}
