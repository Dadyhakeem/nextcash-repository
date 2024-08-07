package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.service.UserService;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.ToUserMapper;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.request.UserUpdatePasswordDTO;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Usuario", description = "Contem todos as operacoes relativas aos recursos para cadastro , edition e leitura de um Usuario.")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private  final UserService service;
    private  final ToUserMapper mapper;

    @Autowired
    public UserController(UserService service, ToUserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo Usuario",
    responses = {
            @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserCreateResponse.class))),
            @ApiResponse(responseCode = "409",description = "Usuario e-mail já cadastrado no sistema",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recursos nao processado por dados de entrada  invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UserCreateResponse> cadastrar(@Valid @RequestBody UserCreateDTO createDTO) {
            User user = ToUserMapper.CreateUserTO(createDTO);
        User userSalvo = service.cadastrar(user);
        UserCreateResponse obj = mapper.ResponseUserTO(userSalvo);
            return ResponseEntity.status(HttpStatus.CREATED).body(obj);

    }


    @Operation(summary = "Atualizar senha", description = " Requisicao exige um bearer Token . Acess restrito a ADMIN/CLIENT",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Senha atualizada com sucesso"),
                    @ApiResponse(responseCode = "400",description = "Senha nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> atualizarSenha(@Valid @RequestBody UserUpdatePasswordDTO update, @PathVariable Long id) {
           service.atualizarSenha(id,update.getCurrentPassword(),update.getNewPassword(),update.getConfirmPassword());
            return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar todos os usuarios cadastrado", description = "Requisicao exige um bearer Token . Acess retrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuarios cadastrado",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserCreateResponse.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserCreateResponse>> listarTodos() {
        List<User> usuarios = service.listarTodos();
        List<UserCreateResponse> response = usuarios.stream().map(mapper::ResponseUserTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recuperar um usuario pelo id", description = "Requisicao exige um bearer Token . Acess restrito a ADMIN/CLIENT ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserCreateResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404", description = "Recursos nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')OR (hasRole('CLIENT')AND #id == authentication.principal.id)")
    public ResponseEntity<UserCreateResponse> buscarPorId(@Valid @PathVariable Long id) {
        try {
            User user = service.buscarPorId(id);
            UserCreateResponse response = mapper.ResponseUserTO(user);
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar um Usuario", description = " Deletar um usuario",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos nao encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Long id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
