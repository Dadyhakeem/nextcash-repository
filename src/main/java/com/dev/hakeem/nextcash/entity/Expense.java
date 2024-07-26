package com.dev.hakeem.nextcash.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transactionId")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(name = "amount", nullable = false, precision = 2)
    private Double amount;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
