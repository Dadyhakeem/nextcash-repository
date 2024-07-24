package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EmailUniqueViolationExeption;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.exception.PasswordInvalidException;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.request.UserUpdatePasswordDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private  final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User cadastrar(User createDTO){
      try {


          User user = new User();
          user.setEmail(createDTO.getEmail());
          user.setUsername(createDTO.getUsername());
          user.setPassword(createDTO.getPassword());
          return repository.save(user);
      }catch (org.springframework.dao.DataIntegrityViolationException ex){
          throw  new EmailUniqueViolationExeption(String.format("Email  {%s} já cadastrado", createDTO.getEmail()));
      }


    }

    public User atualizarSenha(UserUpdatePasswordDTO updatePasswordDTO) {

        User user = repository.findById(updatePasswordDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        if (!updatePasswordDTO.getCurrentPassword().equals(user.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere");
        }
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmPassword())) {
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha");
        }
        user.setPassword(updatePasswordDTO.getNewPassword());

        return repository.save(user);
    }



    public User buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário id = %s não encontrado",id)));
    }



    public  List<User> listarTodos(){
        return repository.findAll();
    }

    public void  deleteUser(Long id ){
        User user = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Usuario nao encontrado"));
          repository.deleteById(id);
    }


}
