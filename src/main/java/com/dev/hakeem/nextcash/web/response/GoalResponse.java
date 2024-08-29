package com.dev.hakeem.nextcash.web.response;

import com.dev.hakeem.nextcash.entity.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class GoalResponse {

    private Long id;

    private String name;


    private Double targetAmount;


    private Double currentAmount;


    private LocalDate deadline;




}
