package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.Investiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investiment,Long> {
}
