package com.dev.hakeem.nextcash.web.controller;

import com.dev.hakeem.nextcash.entity.Client;
import com.dev.hakeem.nextcash.jwt.JwtUserDetails;
import com.dev.hakeem.nextcash.repository.projection.ClienteProjection;
import com.dev.hakeem.nextcash.service.ClientService;
import com.dev.hakeem.nextcash.service.UserService;
import com.dev.hakeem.nextcash.web.exception.ErroMessage;
import com.dev.hakeem.nextcash.web.mapper.ClientMapper;
import com.dev.hakeem.nextcash.web.mapper.PageableMapper;
import com.dev.hakeem.nextcash.web.request.ClientResquest;
import com.dev.hakeem.nextcash.web.request.PageableDTO;
import com.dev.hakeem.nextcash.web.response.ClientResp;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Client", description = "Contem todos as operacoes relativas aos recursos para criar um novo cliente , edition e leitura de um client.")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClientController {

    private  final ClientService service;
    private  final UserService userService;
    @Autowired
    public ClientController(ClientService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @Operation(summary = "Criar um novo cliente", description = "Recurso para criar um novo cliente vinculado a um user cadastrada. "+
            "Requisicao exige use de um bearer token. Acesso restrito a Role='CLIENT",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = UserCreateResponse.class))),
                    @ApiResponse(responseCode = "409",description = "cliente CPF já possui cadastro no sistema",
                            content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos nao processado por dados de entrada  invalidos",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso nao permite perfil de ADMIN",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErroMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResp> create (@RequestBody @Valid ClientResquest resquest,
                                              @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(resquest);
        client.setUser(userService.buscarPorId(userDetails.getId()));
        service.salvar(client);
        return  ResponseEntity.status(201).body(ClientMapper.toReponse(client));
    }

    @Operation(summary = "Localizar um cliente", description = "Recurso para localizar pelo ID. "+
            "Requisicao exige use de um bearer token. Acesso restrito a Role='ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = UserCreateResponse.class))),
                    @ApiResponse(responseCode = "404",description = "Client nao encontrado",
                            content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso nao permite perfil de CLIENT",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErroMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ClientResp> busacrPorId(@Valid @PathVariable Long id){
        Client client = service.buscarPorId(id);
       return ResponseEntity.ok(ClientMapper.toReponse(client));
    }


    @Operation(summary = "Recuperar lista de clientes",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN' ",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientResp.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErroMessage.class))
                    )
            })

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<PageableDTO> busacrtodos(@Parameter(hidden = true)@PageableDefault(size = 5,sort = {"name"}) Pageable pageable){
        Page<ClienteProjection> client = service.buscartodos(pageable);
        return ResponseEntity.ok((PageableDTO) PageableMapper.toDTO(client));
    }

}
