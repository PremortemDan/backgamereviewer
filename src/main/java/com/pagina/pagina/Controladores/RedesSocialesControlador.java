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

import com.pagina.pagina.Modelos.RedesSociales;
import com.pagina.pagina.Servicios.RedesSocialesServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/redes-sociales")
@Tag(name = "Redes Sociales", description = "API para la gestión de redes sociales")
public class RedesSocialesControlador {

    @Autowired
    private RedesSocialesServicio redesSocialesServicio;

    @Operation(summary = "Obtener todas las redes sociales", description = "Retorna una lista con todas las redes sociales registradas")
    @GetMapping
    public ResponseEntity<List<RedesSociales>> obtenerTodasLasRedes() {
        List<RedesSociales> redes = redesSocialesServicio.obtenerTodasLasRedes();
        return ResponseEntity.ok(redes);
    }

    @Operation(summary = "Obtener red social por ID", description = "Retorna una red social específica basada en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<RedesSociales> obtenerRedPorId(
            @Parameter(description = "ID de la red social a buscar", required = true) @PathVariable Long id) {
        return redesSocialesServicio.obtenerRedPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva red social", description = "Crea y registra una nueva red social en el sistema")
    @PostMapping
    public ResponseEntity<RedesSociales> crearRed(
            @Parameter(description = "Datos de la red social a crear", required = true) @RequestBody RedesSociales red) {
        RedesSociales nuevaRed = redesSocialesServicio.crearRed(red);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRed);
    }

    @Operation(summary = "Actualizar una red social", description = "Actualiza los datos de una red social existente")
    @PutMapping("/{id}")
    public ResponseEntity<RedesSociales> actualizarRed(
            @Parameter(description = "ID de la red social a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la red social", required = true) @RequestBody RedesSociales red) {
        try {
            RedesSociales redActualizada = redesSocialesServicio.actualizarRed(id, red);
            return ResponseEntity.ok(redActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una red social", description = "Elimina una red social del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRed(
            @Parameter(description = "ID de la red social a eliminar", required = true) @PathVariable Long id) {
        if (!redesSocialesServicio.existeRed(id)) {
            return ResponseEntity.notFound().build();
        }
        redesSocialesServicio.eliminarRed(id);
        return ResponseEntity.noContent().build();
    }
}
