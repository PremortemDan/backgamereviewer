package com.pagina.pagina.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    // Campos del frontend
    private String username;
    private String email;
    private String password;
    private String contrasenaHash;
    
    // Opcional
    private String avatarUrl;
    
    // Método helper para obtener el nombre de usuario desde cualquier campo
    public String obtenerNombreUsuario() {
        return username != null ? username : null;
    }
    
    // Método helper para obtener el correo desde cualquier campo
    public String obtenerCorreo() {
        return email != null ? email : null;
    }
    
    // Método helper para obtener la contraseña desde cualquier campo
    public String obtenerContrasena() {
        if (password != null && !password.isEmpty()) {
            return password;
        }
        if (contrasenaHash != null && !contrasenaHash.isEmpty()) {
            return contrasenaHash;
        }
        return null;
    }
}
