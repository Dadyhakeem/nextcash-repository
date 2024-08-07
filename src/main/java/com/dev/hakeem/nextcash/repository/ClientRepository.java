package com.dev.hakeem.nextcash.repository;

import com.dev.hakeem.nextcash.entity.Client;
import com.dev.hakeem.nextcash.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("select c from clientes c")
    Page<ClienteProjection> findAllPage(Pageable pageable);
}
