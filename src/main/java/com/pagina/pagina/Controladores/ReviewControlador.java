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

import com.pagina.pagina.Modelos.Reviews;
import com.pagina.pagina.Servicios.ReviewServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "API para la gestión de reseñas de videojuegos")
public class ReviewControlador {

    @Autowired
    private ReviewServicio reviewServicio;

    // GET - Obtener todas las reviews
    @Operation(summary = "Obtener todas las reviews", description = "Retorna una lista con todas las reseñas de videojuegos registradas en el sistema")
    @GetMapping
    public ResponseEntity<List<Reviews>> obtenerTodasLasReviews() {
        List<Reviews> reviews = reviewServicio.obtenerTodasLasReviews();
        return ResponseEntity.ok(reviews);
    }

    // GET - Obtener review por ID
    @Operation(summary = "Obtener review por ID", description = "Retorna una reseña específica basada en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Reviews> obtenerReviewPorId(
            @Parameter(description = "ID de la review a buscar", required = true) @PathVariable Long id) {
        return reviewServicio.obtenerReviewPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Crear una nueva review
    @Operation(summary = "Crear una nueva review", description = "Crea y registra una nueva reseña de videojuego en el sistema. La fecha, likes y estado se establecen automáticamente si no se proporcionan.")
    @PostMapping
    public ResponseEntity<Reviews> crearReview(
            @Parameter(description = "Datos de la review a crear", required = true) @RequestBody Reviews review) {
        Reviews nuevaReview = reviewServicio.crearReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReview);
    }

    // PUT - Actualizar una review existente
    @Operation(summary = "Actualizar una review", description = "Actualiza los datos de una reseña existente (título, comentario, puntuación y estado)")
    @PutMapping("/{id}")
    public ResponseEntity<Reviews> actualizarReview(
            @Parameter(description = "ID de la review a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la review", required = true) @RequestBody Reviews review) {
        try {
            Reviews reviewActualizada = reviewServicio.actualizarReview(id, review);
            return ResponseEntity.ok(reviewActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Eliminar una review
    @Operation(summary = "Eliminar una review", description = "Elimina una reseña del sistema basada en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReview(
            @Parameter(description = "ID de la review a eliminar", required = true) @PathVariable Long id) {
        if (!reviewServicio.existeReview(id)) {
            return ResponseEntity.notFound().build();
        }
        reviewServicio.eliminarReview(id);
        return ResponseEntity.noContent().build();
    }

}