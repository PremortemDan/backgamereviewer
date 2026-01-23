package com.pagina.pagina.DTOs;

import com.pagina.pagina.Modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private Usuario user;
    private boolean success;
    private String message;
    
    public LoginResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public LoginResponseDTO(boolean success, String token, Usuario user) {
        this.success = success;
        this.token = token;
        this.user = user;
    }
}
