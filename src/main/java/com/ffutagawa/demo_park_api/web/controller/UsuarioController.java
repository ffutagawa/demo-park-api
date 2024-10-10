package com.ffutagawa.demo_park_api.controller;

import com.ffutagawa.demo_park_api.controller.dto.UsuarioCreateDto;
import com.ffutagawa.demo_park_api.controller.dto.UsuarioResponseDto;
import com.ffutagawa.demo_park_api.controller.dto.UsuarioSenhaDto;
import com.ffutagawa.demo_park_api.controller.dto.mapper.UsuarioMapper;
import com.ffutagawa.demo_park_api.entity.Usuario;
import com.ffutagawa.demo_park_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create (@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

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















