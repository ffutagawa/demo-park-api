package com.ffutagawa.demo_park_api.controller.dto.mapper;

import com.ffutagawa.demo_park_api.controller.dto.UsuarioCreateDto;
import com.ffutagawa.demo_park_api.controller.dto.UsuarioResponseDto;
import com.ffutagawa.demo_park_api.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario (UsuarioCreateDto createDto){
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UsuarioResponseDto toDto (Usuario usuario){
        String role = usuario.getRole().name().substring("Role_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRoler(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(x -> toDto(x)).collect(Collectors.toList());
    }

}
























