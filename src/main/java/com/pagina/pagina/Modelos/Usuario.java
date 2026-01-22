package com.pagina.pagina.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.Setter;
import lombok.Getter;


@Entity
@Setter
@Getter
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private Long version;
    
    @Column(nullable = false, unique = true, length = 50)
    private String nombreUsuario;
    
    @Column(nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(nullable = false)
    private String contrasenaHash;
    
    @Column(length = 255)
    private String avatarUrl;
    
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;
}
