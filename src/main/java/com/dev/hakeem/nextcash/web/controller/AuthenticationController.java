package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.jwt.JwtUserDetailsService;
import com.dev.hakeem.nextcash.jwt.jwtToken;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.request.UserLoginDTO;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Authentication",description = "Recurso para proceder com a authentication na API")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private final JwtUserDetailsService service;
    @Autowired
    private final AuthenticationManager manager;

    @Operation(summary = "Authenticar na API ", description = "Recurso de authenticacao na API",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Authenticar realizado com sucesso e retornar de um bearer token",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserCreateResponse.class))),
                    @ApiResponse(responseCode = "400",description = "Crendencials invalidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo(s)  invalido(s)",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
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
