package com.pagina.pagina.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pagina.pagina.DTOs.RegistroUsuarioDTO;
import com.pagina.pagina.DTOs.LoginDTO;
import com.pagina.pagina.DTOs.LoginResponseDTO;
import com.pagina.pagina.Modelos.Usuario;
import com.pagina.pagina.Servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // GET - Obtener todos los usuarios
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista con todos los usuarios registrados en el sistema")
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioServicio.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET - Obtener usuario por ID
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico basado en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario a buscar", required = true) @PathVariable Long id) {
        return usuarioServicio.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Crear un nuevo usuario (con DTO para compatibilidad frontend)
    @Operation(summary = "Crear un nuevo usuario", description = "Crea y registra un nuevo usuario en el sistema. Acepta tanto nombres de campos del frontend como del backend.")
    @PostMapping
    public ResponseEntity<?> crearUsuario(
            @Parameter(description = "Datos del usuario a crear", required = true) @RequestBody RegistroUsuarioDTO dto) {
        try {
            Usuario nuevoUsuario = usuarioServicio.registrarUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error de validación: " + e.getMessage());
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario fue modificado por otra transacción. Por favor, intenta nuevamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el usuario: " + e.getMessage());
        }
    }

    // POST - Login de usuario
    @Operation(summary = "Login de usuario", description = "Autentica un usuario y retorna sus datos si las credenciales son correctas")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Parameter(description = "Credenciales de login", required = true) @RequestBody LoginDTO loginDTO) {
        try {
            var usuario = usuarioServicio.login(loginDTO.getUsername(), loginDTO.getPassword());
            
            if (usuario.isPresent()) {
                // Generar token simple (en producción usar JWT)
                String token = "Bearer-" + System.currentTimeMillis();
                LoginResponseDTO response = new LoginResponseDTO(true, token, usuario.get());
                return ResponseEntity.ok(response);
            } else {
                LoginResponseDTO response = new LoginResponseDTO(false, "Credenciales inválidas");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            LoginResponseDTO response = new LoginResponseDTO(false, "Error al iniciar sesión: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // POST - Logout de usuario
    @Operation(summary = "Logout de usuario", description = "Cierra la sesión del usuario")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // En una implementación real, aquí invalidarías el token
        return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Sesión cerrada correctamente\"}");
    }

    // PUT - Actualizar un usuario existente
    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario", required = true) @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario fue modificado por otra transacción. Por favor, recarga los datos e intenta nuevamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // DELETE - Eliminar un usuario
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Long id) {
        if (!usuarioServicio.existeUsuario(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
