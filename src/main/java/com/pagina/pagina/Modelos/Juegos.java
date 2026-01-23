package com.pagina.pagina.Modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Setter;
import lombok.Getter;


@Entity
@Setter
@Getter
@Table(name = "videojuegos")
public class Juegos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    // Cambiado a String separado por comas en lugar de @ElementCollection
    @Column(length = 500)
    private String plataformas;
    
    // Cambiado a String separado por comas en lugar de @ElementCollection
    @Column(length = 500)
    private String generos;
    
    @Column(nullable = false)
    private LocalDate fechaLanzamiento;
    
    @Column(length = 500)
    private String portadaUrl;
    
    @Column
    private Integer puntuacionUsuarios;
    
    @Column(length = 50)
    private String clasificacion;
    
    @Column(nullable = false, length = 100)
    private String desarrollador;
}
