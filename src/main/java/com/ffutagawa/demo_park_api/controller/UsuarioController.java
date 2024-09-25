package com.ffutagawa.demo_park_api.controller;

import com.ffutagawa.demo_park_api.entity.Usuario;
import com.ffutagawa.demo_park_api.service.UsuarioService;
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

    @PostMapping
    public ResponseEntity<Usuario> create (@RequestBody Usuario usuario){
        Usuario user = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
    Usuario user = usuarioService.buscarPorId(id);
    return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok().body(users);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
        return ResponseEntity.ok().body(user);
    }

}















