package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.jwt.JwtUserDetailsService;
import com.dev.hakeem.nextcash.jwt.jwtToken;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final JwtUserDetailsService service;
    private final AuthenticationManager manager;


    @PostMapping("/auth")
    public ResponseEntity<?> authenticar(@Valid @RequestBody UserLoginDTO loginDTO, HttpServletRequest request){
        log.info("processo de authenticacao pelo login {}",loginDTO.getEmail());
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            manager.authenticate(authenticationToken);
            jwtToken token = service.gettokenAuthenticated(loginDTO.getEmail());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException e){
            log.warn("Bad credential from email '{}'",loginDTO.getEmail());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErroMessage(request, HttpStatus.BAD_REQUEST, " Crendencials invalidos"));
    }

}
