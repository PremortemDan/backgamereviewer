package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.PiedePagina;
import com.pagina.pagina.Repositorios.PiedePaginaRepositorio;

@Service
public class PiedePaginaServicio {

    @Autowired
    private PiedePaginaRepositorio piedePaginaRepositorio;

    public List<PiedePagina> obtenerTodosLosPies() {
        return piedePaginaRepositorio.findAll();
    }

    public Optional<PiedePagina> obtenerPiePorId(Long id) {
        return piedePaginaRepositorio.findById(id);
    }

    public PiedePagina crearPie(PiedePagina pie) {
        return piedePaginaRepositorio.save(pie);
    }

    public PiedePagina actualizarPie(Long id, PiedePagina pieActualizado) {
        return piedePaginaRepositorio.findById(id)
                .map(pie -> {
                    pie.setTituloDescripcion(pieActualizado.getTituloDescripcion());
                    pie.setTextoDescripcion(pieActualizado.getTextoDescripcion());
                    pie.setCopyright(pieActualizado.getCopyright());
                    return piedePaginaRepositorio.save(pie);
                })
                .orElseThrow(() -> new RuntimeException("Pie de página no encontrado con id: " + id));
    }

    public void eliminarPie(Long id) {
        piedePaginaRepositorio.deleteById(id);
    }

    public boolean existePie(Long id) {
        return piedePaginaRepositorio.existsById(id);
    }
}
