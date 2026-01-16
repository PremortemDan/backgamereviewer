package com.pagina.pagina.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.Reviews;
import com.pagina.pagina.Modelos.EstadoReview;
import com.pagina.pagina.Repositorios.ReviewRepositorio;

@Service
public class ReviewServicio {

    @Autowired
    private ReviewRepositorio reviewRepositorio;

    public List<Reviews> obtenerTodasLasReviews() {
        return reviewRepositorio.findAll();
    }

    public Optional<Reviews> obtenerReviewPorId(Long id) {
        return reviewRepositorio.findById(id);
    }

    public Reviews crearReview(Reviews review) {
        // Establecer valores por defecto
        if (review.getFecha() == null) {
            review.setFecha(LocalDateTime.now());
        }
        if (review.getLikes() == null) {
            review.setLikes(0);
        }
        if (review.getEstado() == null) {
            review.setEstado(EstadoReview.PENDIENTE);
        }
        return reviewRepositorio.save(review);
    }

    public Reviews actualizarReview(Long id, Reviews reviewActualizada) {
        return reviewRepositorio.findById(id)
                .map(review -> {
                    review.setTitulo(reviewActualizada.getTitulo());
                    review.setComentario(reviewActualizada.getComentario());
                    review.setPuntuacion(reviewActualizada.getPuntuacion());
                    if (reviewActualizada.getEstado() != null) {
                        review.setEstado(reviewActualizada.getEstado());
                    }
                    return reviewRepositorio.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review no encontrada con id: " + id));
    }

    // Eliminar una review
    public void eliminarReview(Long id) {
        reviewRepositorio.deleteById(id);
    }

    // Verificar si existe una review

    public boolean existeReview(Long id) {
        return reviewRepositorio.existsById(id);
    }

    // Incrementar likes de una review

    public Reviews incrementarLikes(Long id) {
        return reviewRepositorio.findById(id)
                .map(review -> {
                    review.setLikes(review.getLikes() + 1);
                    return reviewRepositorio.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review no encontrada con id: " + id));
    }

    // Cambiar estado de una review

    public Reviews cambiarEstado(Long id, EstadoReview nuevoEstado) {
        return reviewRepositorio.findById(id)
                .map(review -> {
                    review.setEstado(nuevoEstado);
                    return reviewRepositorio.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review no encontrada con id: " + id));
    }
}
