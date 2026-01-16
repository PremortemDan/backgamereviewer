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

import com.pagina.pagina.Modelos.PiedePagina;
import com.pagina.pagina.Servicios.PiedePaginaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pie-pagina")
@Tag(name = "Pie de Página", description = "API para la gestión del pie de página")
public class PiedePaginaControlador {

    @Autowired
    private PiedePaginaServicio piedePaginaServicio;

    @Operation(summary = "Obtener todos los pies de página", description = "Retorna una lista con todos los pies de página registrados")
    @GetMapping
    public ResponseEntity<List<PiedePagina>> obtenerTodosLosPies() {
        List<PiedePagina> pies = piedePaginaServicio.obtenerTodosLosPies();
        return ResponseEntity.ok(pies);
    }

    @Operation(summary = "Obtener pie de página por ID", description = "Retorna un pie de página específico basado en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<PiedePagina> obtenerPiePorId(
            @Parameter(description = "ID del pie de página a buscar", required = true) @PathVariable Long id) {
        return piedePaginaServicio.obtenerPiePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo pie de página", description = "Crea y registra un nuevo pie de página en el sistema")
    @PostMapping
    public ResponseEntity<PiedePagina> crearPie(
            @Parameter(description = "Datos del pie de página a crear", required = true) @RequestBody PiedePagina pie) {
        PiedePagina nuevoPie = piedePaginaServicio.crearPie(pie);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPie);
    }

    @Operation(summary = "Actualizar un pie de página", description = "Actualiza los datos de un pie de página existente")
    @PutMapping("/{id}")
    public ResponseEntity<PiedePagina> actualizarPie(
            @Parameter(description = "ID del pie de página a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del pie de página", required = true) @RequestBody PiedePagina pie) {
        try {
            PiedePagina pieActualizado = piedePaginaServicio.actualizarPie(id, pie);
            return ResponseEntity.ok(pieActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un pie de página", description = "Elimina un pie de página del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPie(
            @Parameter(description = "ID del pie de página a eliminar", required = true) @PathVariable Long id) {
        if (!piedePaginaServicio.existePie(id)) {
            return ResponseEntity.notFound().build();
        }
        piedePaginaServicio.eliminarPie(id);
        return ResponseEntity.noContent().build();
    }
}
