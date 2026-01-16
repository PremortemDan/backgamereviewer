package com.pagina.pagina.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;


@Entity
@Setter
@Getter
@Table(name = "logos")
public class Logo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(nullable = false, length = 500)
    private String urlImagen;
    
    @Column(nullable = false, length = 10)
    private String formato;
    
    @Column(nullable = false)
    private Integer ancho;
    
    @Column(nullable = false)
    private Integer alto;
    
    @Column(nullable = false)
    private Boolean activo;
}
