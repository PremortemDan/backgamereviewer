package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.Juegos;
import com.pagina.pagina.Repositorios.JuegosRepositorio;

@Service
public class JuegosServicio {

    @Autowired
    private JuegosRepositorio juegosRepositorio;

    public List<Juegos> obtenerTodosLosJuegos() {
        return juegosRepositorio.findAll();
    }

    public Optional<Juegos> obtenerJuegoPorId(Long id) {
        return juegosRepositorio.findById(id);
    }

    public Juegos crearJuego(Juegos juego) {
        return juegosRepositorio.save(juego);
    }

    public Juegos actualizarJuego(Long id, Juegos juegoActualizado) {
        return juegosRepositorio.findById(id)
                .map(juego -> {
                    juego.setTitulo(juegoActualizado.getTitulo());
                    juego.setDescripcion(juegoActualizado.getDescripcion());
                    juego.setPlataformas(juegoActualizado.getPlataformas());
                    juego.setGeneros(juegoActualizado.getGeneros());
                    juego.setFechaLanzamiento(juegoActualizado.getFechaLanzamiento());
                    juego.setPortadaUrl(juegoActualizado.getPortadaUrl());
                    juego.setPuntuacionUsuarios(juegoActualizado.getPuntuacionUsuarios());
                    juego.setClasificacion(juegoActualizado.getClasificacion());
                    juego.setDesarrollador(juegoActualizado.getDesarrollador());
                    return juegosRepositorio.save(juego);
                })
                .orElseThrow(() -> new RuntimeException("Juego no encontrado con id: " + id));
    }

    public void eliminarJuego(Long id) {
        juegosRepositorio.deleteById(id);
    }

    public boolean existeJuego(Long id) {
        return juegosRepositorio.existsById(id);
    }
}
