package com.dev.hakeem.nextcash.entity;

import com.dev.hakeem.nextcash.enums.CategoryExpense;
import com.dev.hakeem.nextcash.enums.CategoryIncome;
import com.dev.hakeem.nextcash.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_transaction")
public class Transaction implements Serializable {
    private static final long serialversion= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;
    private  String descricao;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private CategoryExpense categoryExpense;
    @Enumerated(EnumType.STRING)
    private TransactionType tipo;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;
    @ManyToOne
    @JoinColumn(name = "income_id")
    private Income income;
    @ManyToOne
    @JoinColumn(name = "transferencia_id")
    private Transferenca transferenca;
    @Enumerated(EnumType.STRING)
    private CategoryIncome categoryIncome;



    public  Transaction (Expense expense){
        this.tipo = TransactionType.DESPESA;
        this.valor = expense.getAmount();
        this.descricao = expense.getDescricao();
        this.data = expense.getCreatedAt();
        this.categoryExpense = expense.getCategoryExpense();
        this.expense = expense;
    }

    public  Transaction (Income income){
        this.tipo = TransactionType.RECEITA;
        this.valor = income.getAmount();
        this.descricao = income.getDescricao();
        this.data = income.getCreatedAt();
        this.categoryIncome = income.getCategoryIncome();
        this.income = income;
    }

    public Transaction (Transferenca transferenca){
        this.tipo = TransactionType.TRANSFERENCA;
        this.valor = transferenca.getValor();
        this.descricao = transferenca.getDescricao();
        this.data = transferenca.getCreatedAt();
        this.transferenca = transferenca;
    }



}
