package com.pagina.pagina.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagina.pagina.DTOs.RegistroUsuarioDTO;
import com.pagina.pagina.Modelos.Estado;
import com.pagina.pagina.Modelos.Rol;
import com.pagina.pagina.Modelos.Usuario;
import com.pagina.pagina.Repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id);
    }

    // Registrar un nuevo usuario usando DTO (para compatibilidad con frontend)
    @Transactional
    public Usuario registrarUsuario(RegistroUsuarioDTO dto) {
        // Validaciones
        String nombreUsuario = dto.obtenerNombreUsuario();
        String correo = dto.obtenerCorreo();
        String contrasena = dto.obtenerContrasena();
        
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        
        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario.trim());
        usuario.setCorreo(correo.trim());
        usuario.setContrasenaHash(passwordEncoder.encode(contrasena));
        usuario.setAvatarUrl(dto.getAvatarUrl());
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol(Rol.USUARIO);
        usuario.setEstado(Estado.ACTIVO);
        
        return usuarioRepositorio.save(usuario);
    }

    // Crear un nuevo usuario
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        // Asegurarse de que es una nueva entidad
        usuario.setId(null);
        usuario.setVersion(null);
        return usuarioRepositorio.save(usuario);
    }

    // Actualizar un usuario existente
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepositorio.findById(id)
            .map(usuarioExistente -> {
                usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
                usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
                usuarioExistente.setAvatarUrl(usuarioActualizado.getAvatarUrl());
                usuarioExistente.setRol(usuarioActualizado.getRol());
                usuarioExistente.setEstado(usuarioActualizado.getEstado());
                if (usuarioActualizado.getContrasenaHash() != null && !usuarioActualizado.getContrasenaHash().isEmpty()) {
                    usuarioExistente.setContrasenaHash(usuarioActualizado.getContrasenaHash());
                }
                return usuarioRepositorio.save(usuarioExistente);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // Eliminar un usuario
    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    // Verificar si un usuario existe
    public boolean existeUsuario(Long id) {
        return usuarioRepositorio.existsById(id);
    }
}
