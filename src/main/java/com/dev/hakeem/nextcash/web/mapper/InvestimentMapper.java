package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Investiment;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.TipoInvestimento;
import com.dev.hakeem.nextcash.repository.InvestmentRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import com.dev.hakeem.nextcash.web.response.InvestmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        try {
            TipoInvestimento tipoInvestimento = TipoInvestimento.valueOf(request.getTipoInvestimento());
            investment.setTipoInvestimento(tipoInvestimento);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de Investimento nao existe : " + request.getTipoInvestimento());
        }
        try {
            LocalDate start_data = LocalDate.parse(request.getStartDate());
            LocalDate end_data = LocalDate.parse(request.getEndDate());
            investment.setStartDate(start_data);
            investment.setEndDate(end_data);
        }catch (
                DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido", e);
        }

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
        response.setTipoInvestimento(investment.getTipoInvestimento().name());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        response.setStartDate(LocalDate.parse(investment.getStartDate().format(formatter)));
        response.setEndDate(LocalDate.parse(investment.getEndDate().format(formatter)));
        return response;
    }
}
