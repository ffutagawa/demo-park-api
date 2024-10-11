package com.ffutagawa.demo_park_api.service;

import com.ffutagawa.demo_park_api.entity.Usuario;
import com.ffutagawa.demo_park_api.exception.EntityNotFoundException;
import com.ffutagawa.demo_park_api.exception.PasswordInvalidException;
import com.ffutagawa.demo_park_api.exception.UserNameUniqueViolationException;
import com.ffutagawa.demo_park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UserNameUniqueViolationException(String.format("username {%s} já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true) //Indica que o metodo é exclusico para banco de dados
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario id = {%s} não encontrado", id)));
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com cofirmação senha.");
        }
        Usuario user = buscarPorId(id);
        if(!user.getPassword().equals(senhaAtual)){
            throw new PasswordInvalidException("Sua senha não confere!");
        }
        user.setPassword(novaSenha);
        return user;
    }

    //Sem metodo TrocarSenha
//    @Transactional
//    public Usuario editarSenha(Long id, String password) {
//        Usuario user = buscarPorId(id);
//        user.setPassword(password);
//        return user;
//    }


}
