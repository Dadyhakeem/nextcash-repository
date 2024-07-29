package com.dev.hakeem.nextcash.service;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.Role;
import com.dev.hakeem.nextcash.exception.EmailUniqueViolationExeption;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.exception.PasswordInvalidException;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.request.UserUpdatePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private  final UserRepository repository;
    private final PasswordEncoder encoder;
    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User cadastrar(User createDTO){
      try {


          User user = new User();
          user.setEmail(createDTO.getEmail());
          user.setUsername(createDTO.getUsername());
          user.setPassword(encoder.encode(createDTO.getPassword()));
          return repository.save(user);
      }catch (org.springframework.dao.DataIntegrityViolationException ex){
          throw  new EmailUniqueViolationExeption(String.format("Email  {%s} já cadastrado", createDTO.getEmail()));
      }


    }

    public User atualizarSenha(Long id,String CurrentPassword,String NewPassword , String ConfirmPassword) {
        if (!NewPassword.equals(ConfirmPassword)){
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha");
        }
       User user = buscarPorId(id);
        if (!encoder.matches(CurrentPassword,user.getPassword())){
            throw new PasswordInvalidException("Sua senha não confere");
        }
       user.setPassword(encoder.encode(NewPassword));
       return user;
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

    @Transactional(readOnly = true)
    public User BuscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Usario com 'email' nao encontrado",email)));
    }
    @Transactional(readOnly = true)
    public Role BuscarPorRoleByEmail(String email) {
        return repository.findRoleByEmail(email);
    }
}
