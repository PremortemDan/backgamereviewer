package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.DTOs.JuegoDTO;
import com.pagina.pagina.Modelos.Juegos;
import com.pagina.pagina.Repositorios.JuegosRepositorio;

@Service
public class JuegosServicio {

    @Autowired
    private JuegosRepositorio juegosRepositorio;

    public List<JuegoDTO> obtenerTodosLosJuegos() {
        return juegosRepositorio.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Optional<JuegoDTO> obtenerJuegoPorId(Long id) {
        return juegosRepositorio.findById(id)
            .map(this::convertirADTO);
    }

    public JuegoDTO crearJuego(JuegoDTO dto) {
        Juegos juego = convertirAEntidad(dto);
        Juegos guardado = juegosRepositorio.save(juego);
        return convertirADTO(guardado);
    }

    public JuegoDTO actualizarJuego(Long id, JuegoDTO dto) {
        return juegosRepositorio.findById(id)
                .map(juego -> {
                    actualizarEntidadDesdeDTO(juego, dto);
                    Juegos actualizado = juegosRepositorio.save(juego);
                    return convertirADTO(actualizado);
                })
                .orElseThrow(() -> new RuntimeException("Juego no encontrado con id: " + id));
    }

    public void eliminarJuego(Long id) {
        juegosRepositorio.deleteById(id);
    }

    public boolean existeJuego(Long id) {
        return juegosRepositorio.existsById(id);
    }
    
    // Métodos de conversión
    private JuegoDTO convertirADTO(Juegos juego) {
        JuegoDTO dto = new JuegoDTO();
        dto.setId(juego.getId());
        dto.setTitulo(juego.getTitulo());
        dto.setDescripcion(juego.getDescripcion());
        dto.setFechaLanzamiento(juego.getFechaLanzamiento());
        dto.setPortadaUrl(juego.getPortadaUrl());
        dto.setPuntuacionUsuarios(juego.getPuntuacionUsuarios());
        dto.setClasificacion(juego.getClasificacion());
        dto.setDesarrollador(juego.getDesarrollador());
        
        // Convertir strings separados por coma a listas
        if (juego.getPlataformas() != null && !juego.getPlataformas().isEmpty()) {
            dto.setPlataformasRaw(List.of(juego.getPlataformas().split(",")));
        } else {
            dto.setPlataformasRaw(List.of());
        }
        
        if (juego.getGeneros() != null && !juego.getGeneros().isEmpty()) {
            dto.setGenerosRaw(List.of(juego.getGeneros().split(",")));
        } else {
            dto.setGenerosRaw(List.of());
        }
        
        return dto;
    }
    
    private Juegos convertirAEntidad(JuegoDTO dto) {
        Juegos juego = new Juegos();
        actualizarEntidadDesdeDTO(juego, dto);
        return juego;
    }
    
    private void actualizarEntidadDesdeDTO(Juegos juego, JuegoDTO dto) {
        juego.setTitulo(dto.getTitulo());
        juego.setDescripcion(dto.getDescripcion());
        juego.setFechaLanzamiento(dto.getFechaLanzamiento());
        juego.setPortadaUrl(dto.getPortadaUrl());
        juego.setPuntuacionUsuarios(dto.getPuntuacionUsuarios());
        juego.setClasificacion(dto.getClasificacion());
        juego.setDesarrollador(dto.getDesarrollador());
        
        // Convertir listas a strings separados por coma
        juego.setPlataformas(dto.getPlataformasString());
        juego.setGeneros(dto.getGenerosString());
    }
}
