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

import com.pagina.pagina.Modelos.MarcasAsociadas;
import com.pagina.pagina.Servicios.MarcasAsociadasServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/marcas")
@Tag(name = "Marcas Asociadas", description = "API para la gestión de marcas asociadas")
public class MarcasAsociadasControlador {

    @Autowired
    private MarcasAsociadasServicio marcasAsociadasServicio;

    @Operation(summary = "Obtener todas las marcas", description = "Retorna una lista con todas las marcas asociadas registradas")
    @GetMapping
    public ResponseEntity<List<MarcasAsociadas>> obtenerTodasLasMarcas() {
        List<MarcasAsociadas> marcas = marcasAsociadasServicio.obtenerTodasLasMarcas();
        return ResponseEntity.ok(marcas);
    }

    @Operation(summary = "Obtener marca por ID", description = "Retorna una marca específica basada en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<MarcasAsociadas> obtenerMarcaPorId(
            @Parameter(description = "ID de la marca a buscar", required = true) @PathVariable Long id) {
        return marcasAsociadasServicio.obtenerMarcaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva marca", description = "Crea y registra una nueva marca asociada en el sistema")
    @PostMapping
    public ResponseEntity<MarcasAsociadas> crearMarca(
            @Parameter(description = "Datos de la marca a crear", required = true) @RequestBody MarcasAsociadas marca) {
        MarcasAsociadas nuevaMarca = marcasAsociadasServicio.crearMarca(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    @Operation(summary = "Actualizar una marca", description = "Actualiza los datos de una marca existente")
    @PutMapping("/{id}")
    public ResponseEntity<MarcasAsociadas> actualizarMarca(
            @Parameter(description = "ID de la marca a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la marca", required = true) @RequestBody MarcasAsociadas marca) {
        try {
            MarcasAsociadas marcaActualizada = marcasAsociadasServicio.actualizarMarca(id, marca);
            return ResponseEntity.ok(marcaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una marca", description = "Elimina una marca del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(
            @Parameter(description = "ID de la marca a eliminar", required = true) @PathVariable Long id) {
        if (!marcasAsociadasServicio.existeMarca(id)) {
            return ResponseEntity.notFound().build();
        }
        marcasAsociadasServicio.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }
}
