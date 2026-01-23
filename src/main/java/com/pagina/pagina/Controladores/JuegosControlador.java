package com.pagina.pagina.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pagina.pagina.DTOs.JuegoDTO;
import com.pagina.pagina.Servicios.JuegosServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/juegos")
@Tag(name = "Juegos", description = "API para la gestión de videojuegos")
public class JuegosControlador {

    @Autowired
    private JuegosServicio juegosServicio;

    @Operation(summary = "Obtener todos los juegos", description = "Retorna una lista con todos los videojuegos registrados")
    @GetMapping
    public ResponseEntity<List<JuegoDTO>> obtenerTodosLosJuegos() {
        List<JuegoDTO> juegos = juegosServicio.obtenerTodosLosJuegos();
        return ResponseEntity.ok(juegos);
    }

    @Operation(summary = "Obtener juego por ID", description = "Retorna un videojuego específico basado en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<JuegoDTO> obtenerJuegoPorId(
            @Parameter(description = "ID del juego a buscar", required = true) @PathVariable Long id) {
        return juegosServicio.obtenerJuegoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo juego", description = "Crea y registra un nuevo videojuego en el sistema")
    @PostMapping
    public ResponseEntity<JuegoDTO> crearJuego(
            @Parameter(description = "Datos del juego a crear", required = true) @RequestBody JuegoDTO dto) {
        JuegoDTO nuevoJuego = juegosServicio.crearJuego(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJuego);
    }

    @Operation(summary = "Actualizar un juego", description = "Actualiza los datos de un videojuego existente")
    @PutMapping("/{id}")
    public ResponseEntity<JuegoDTO> actualizarJuego(
            @Parameter(description = "ID del juego a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del juego", required = true) @RequestBody JuegoDTO dto) {
        try {
            JuegoDTO juegoActualizado = juegosServicio.actualizarJuego(id, dto);
            return ResponseEntity.ok(juegoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un juego", description = "Elimina un videojuego del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(
            @Parameter(description = "ID del juego a eliminar", required = true) @PathVariable Long id) {
        if (!juegosServicio.existeJuego(id)) {
            return ResponseEntity.notFound().build();
        }
        juegosServicio.eliminarJuego(id);
        return ResponseEntity.noContent().build();
    }
}
