package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.service.UserService;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.mapper.ToUserMapper;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.request.UserUpdatePasswordDTO;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private  final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<UserCreateResponse> cadastrar(@RequestBody UserCreateDTO createDTO) {
            User user = ToUserMapper.CreateUserTO(createDTO);
        User userSalvo = service.cadastrar(user);
        UserCreateResponse obj = ToUserMapper.ResponseUserTO(userSalvo);
            return ResponseEntity.status(HttpStatus.CREATED).body(obj);

    }
    @PutMapping("{id}/update-password")
    public ResponseEntity<Void> atualizarSenha(@RequestBody UserUpdatePasswordDTO updatePasswordDTO) {
        try {
            service.atualizarSenha(updatePasswordDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listarTodos() {
        List<User> usuarios = service.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
        try {
            User user = service.buscarPorId(id);
            return ResponseEntity.ok(user);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
