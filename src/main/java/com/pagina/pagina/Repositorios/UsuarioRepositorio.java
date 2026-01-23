package com.pagina.pagina.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagina.pagina.Modelos.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
    Optional<Usuario> findByCorreo(String correo);
    
    boolean existsByCorreo(String correo);
    
    boolean existsByNombreUsuario(String nombreUsuario);
}
