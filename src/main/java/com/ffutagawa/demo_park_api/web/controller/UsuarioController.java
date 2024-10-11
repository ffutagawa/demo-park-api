package com.ffutagawa.demo_park_api.web.controller;

import com.ffutagawa.demo_park_api.web.dto.UsuarioCreateDto;
import com.ffutagawa.demo_park_api.web.dto.UsuarioResponseDto;
import com.ffutagawa.demo_park_api.web.dto.UsuarioSenhaDto;
import com.ffutagawa.demo_park_api.web.dto.mapper.UsuarioMapper;
import com.ffutagawa.demo_park_api.entity.Usuario;
import com.ffutagawa.demo_park_api.service.UsuarioService;
import com.ffutagawa.demo_park_api.web.exception.ErroMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recurso para cadastro, edição e leitura de um usuario")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //SEM DTO
//    @PostMapping
//    public ResponseEntity<Usuario> create (@Valid @RequestBody UsuarioCreateDto createDto){
//        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
//        return ResponseEntity.status(HttpStatus.CREATED).body(user);
//    }

    //Usando metodo para não precisar cadastrar ROLE (ToDto
    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuario",
                responses ={
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuario e-mail ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))
                    )
                } )
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create (@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuário pelo ID", description = "Recuperar um usuário pelo ID",
            responses ={
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                     @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))
                    )
            } )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok().body(UsuarioMapper.toDto(user));
    }

    //SEM DTO
//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> getById(@PathVariable Long id){
//    Usuario user = usuarioService.buscarPorId(id);
//    return ResponseEntity.ok().body(user);
//    }

    @Operation(summary = "Recuperar um usuário pelo ID", description = "Recuperar um usuário pelo ID",
            responses ={
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

            } )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }

    //SEM DTO
//    @GetMapping
//    public ResponseEntity<List<Usuario>> getAll(){
//        List<Usuario> users = usuarioService.buscarTodos();
//        return ResponseEntity.ok().body(users);
//    }

    @Operation(summary = "Atualizar senha", description = "Recuperar para Atualizar senha",
            responses ={
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))

                    )
            } )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    //Sem metodo novaSenhaDto
//    @PatchMapping("/{id}")
//    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario){
//        Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
//        return ResponseEntity.ok().body(user);
//    }

}















