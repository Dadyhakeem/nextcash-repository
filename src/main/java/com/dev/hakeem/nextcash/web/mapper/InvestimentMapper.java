package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Investiment;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.InvestmentRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import com.dev.hakeem.nextcash.web.response.InvestmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvestimentMapper {

    private final InvestmentRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public InvestimentMapper(InvestmentRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Investiment toEntity(InvestmentRequest request) {
        Investiment investment = new Investiment();
        investment.setName(request.getName());
        investment.setAmount(request.getAmount());
        investment.setTipoInvestimento(request.getTipoInvestimento());
        investment.setStartDate(request.getStartDate());
        investment.setEndDate(request.getEndDate());

        Optional<User> user = userRepository.findById(request.getUserid());
        if (user.isEmpty()) {
            throw new BusinessException("Usuário não encontrado");
        }
        investment.setUserid(user.get());
        return investment;
    }

    public InvestmentResponse toResponse(Investiment investment) {
        InvestmentResponse response = new InvestmentResponse();
        response.setId(investment.getId());
        response.setName(investment.getName());
        response.setAmount(investment.getAmount());
        response.setTipoInvestimento(investment.getTipoInvestimento());
        response.setStartDate(investment.getStartDate());
        response.setEndDate(investment.getEndDate());
        return response;
    }
}
