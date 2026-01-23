package com.pagina.pagina.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class JuegoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    
    @JsonProperty("plataformas")
    private Object plataformasRaw; // Puede ser String o List<String>
    
    @JsonProperty("generos")
    private Object generosRaw; // Puede ser String o List<String>
    
    private LocalDate fechaLanzamiento;
    private String portadaUrl;
    private Integer puntuacionUsuarios;
    private String clasificacion;
    private String desarrollador;
    
    // Métodos helper para convertir entre List y String
    public String getPlataformasString() {
        if (plataformasRaw instanceof List) {
            return String.join(",", (List<String>) plataformasRaw);
        } else if (plataformasRaw instanceof String) {
            return (String) plataformasRaw;
        }
        return "";
    }
    
    public String getGenerosString() {
        if (generosRaw instanceof List) {
            return String.join(",", (List<String>) generosRaw);
        } else if (generosRaw instanceof String) {
            return (String) generosRaw;
        }
        return "";
    }
    
    public List<String> getPlataformasList() {
        if (plataformasRaw instanceof List) {
            return (List<String>) plataformasRaw;
        } else if (plataformasRaw instanceof String) {
            return List.of(((String) plataformasRaw).split(","));
        }
        return List.of();
    }
    
    public List<String> getGenerosList() {
        if (generosRaw instanceof List) {
            return (List<String>) generosRaw;
        } else if (generosRaw instanceof String) {
            return List.of(((String) generosRaw).split(","));
        }
        return List.of();
    }
}
