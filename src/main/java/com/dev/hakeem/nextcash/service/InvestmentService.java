package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Investiment;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.TipoInvestimento;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.InvestmentRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class InvestmentService {

    private  final InvestmentRepository repository;
    private final UserRepository userRepository;
    private  final UserService userService;
    @Autowired
    public InvestmentService(InvestmentRepository repository, UserRepository userRepository, UserService userService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Investiment createInvestment(InvestmentRequest request){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        User user = userRepository.findById(request.getUserid())
                .orElseThrow(()-> new EntityNotFoundException("Usuario nao encontrado"));

        Investiment investiment = new Investiment();
        investiment.setName(request.getName());
        investiment.setAmount(request.getAmount());
        try {
            TipoInvestimento tipoInvestimento = TipoInvestimento.valueOf(request.getTipoInvestimento());
            investiment.setTipoInvestimento(tipoInvestimento);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de Investimento nao existe : " + request.getTipoInvestimento());
        }
        try {
            LocalDate start_data = LocalDate.parse(request.getStartDate());
            LocalDate end_data = LocalDate.parse(request.getEndDate());
            investiment.setStartDate(start_data);
            investiment.setEndDate(end_data);
        }catch (
    DateTimeParseException e) {
        throw new IllegalArgumentException("Formato de data inválido", e);
    }


        return repository.save(investiment);
    }

    public Investiment atualizar(Long id,InvestmentRequest request) {
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        // Verifica se o usuário associado ao investimento existe
        Optional<User> user = userRepository.findById(request.getUserid());
        if (!user.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        // Recupera o investimento existente pelo ID fornecido no request
        Investiment investiment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Investimento não encontrado"));

        // Atualiza os campos do investimento com os valores do request
        investiment.setName(request.getName());
        investiment.setAmount(request.getAmount());
        try {
            TipoInvestimento tipoInvestimento = TipoInvestimento.valueOf(request.getTipoInvestimento());
            investiment.setTipoInvestimento(tipoInvestimento);
        }catch (IllegalArgumentException e ){
            throw new BusinessException("Tipo de Investimento nao existe : " + request.getTipoInvestimento());
        }
        try {
            LocalDate start_data = LocalDate.parse(request.getStartDate());
            LocalDate end_data = LocalDate.parse(request.getEndDate());
            investiment.setStartDate(start_data);
            investiment.setEndDate(end_data);
        }catch (
                DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido", e);
        }
        investiment.setUserid(user.get()); // Atualiza o usuário associado ao investimento

        // Salva o investimento atualizado no repositório
        return repository.save(investiment);
    }


    public List<Investiment> listarTodos(){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        return repository.findAll();
    }

    public Investiment busrcarPorId(Long id){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Investimento id = %s não encontrado",id)));
    }

    public void deletarPorId(Long id ){
        String authenticatedEmail = getAuthenticatedEmail();
        User authenticatedUser = userService.BuscarPorEmail(authenticatedEmail);
        if(!repository.existsById(id)){
            throw  new EntityNotFoundException(String.format("Investimento id = %s não encontrado",id));
        }
         repository.deleteById(id);

    }

    // Método para obter o e-mail do usuário autenticado
    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername(); // Retorna o e-mail
        }
        throw new SecurityException("Usuário não autenticado.");
    }
}
