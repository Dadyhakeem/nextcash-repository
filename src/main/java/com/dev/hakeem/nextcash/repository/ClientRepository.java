package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
