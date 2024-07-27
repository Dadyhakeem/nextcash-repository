package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.Investiment;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.InvestmentRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.InvestmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestmentService {

    private  final InvestmentRepository repository;
    private final UserRepository userRepository;
    @Autowired
    public InvestmentService(InvestmentRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Investiment createInvestment(InvestmentRequest request){
        User user = userRepository.findById(request.getUserid())
                .orElseThrow(()-> new EntityNotFoundException("Usuario nao encontrado"));

        Investiment investiment = new Investiment();
        investiment.setName(request.getName());
        investiment.setAmount(request.getAmount());
        investiment.setTipoInvestimento(request.getTipoInvestimento());
        investiment.setStartDate(request.getStartDate());
        investiment.setEndDate(request.getEndDate());
        return repository.save(investiment);
    }

    public Investiment atualizar(InvestmentRequest request) {
        // Verifica se o usuário associado ao investimento existe
        Optional<User> user = userRepository.findById(request.getUserid());
        if (!user.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        // Recupera o investimento existente pelo ID fornecido no request
        Investiment investiment = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Investimento não encontrado"));

        // Atualiza os campos do investimento com os valores do request
        investiment.setName(request.getName());
        investiment.setAmount(request.getAmount());
        investiment.setTipoInvestimento(request.getTipoInvestimento());
        investiment.setStartDate(request.getStartDate());
        investiment.setEndDate(request.getEndDate());
        investiment.setUserid(user.get()); // Atualiza o usuário associado ao investimento

        // Salva o investimento atualizado no repositório
        return repository.save(investiment);
    }


    public List<Investiment> listarTodos(){
        return repository.findAll();
    }

    public Investiment busrcarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Investimento id = %s não encontrado",id)));
    }

    public void deletarPorId(Long id ){
        if(!repository.existsById(id)){
            throw  new EntityNotFoundException(String.format("Investimento id = %s não encontrado",id));
        }
         repository.deleteById(id);

    }
}
