package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.RedesSociales;
import com.pagina.pagina.Repositorios.RedesSocialesRepositorio;

@Service
public class RedesSocialesServicio {

    @Autowired
    private RedesSocialesRepositorio redesSocialesRepositorio;

    public List<RedesSociales> obtenerTodasLasRedes() {
        return redesSocialesRepositorio.findAll();
    }

    public Optional<RedesSociales> obtenerRedPorId(Long id) {
        return redesSocialesRepositorio.findById(id);
    }

    public RedesSociales crearRed(RedesSociales red) {
        return redesSocialesRepositorio.save(red);
    }

    public RedesSociales actualizarRed(Long id, RedesSociales redActualizada) {
        return redesSocialesRepositorio.findById(id)
                .map(red -> {
                    red.setNombre(redActualizada.getNombre());
                    red.setIconoUrl(redActualizada.getIconoUrl());
                    red.setUrl(redActualizada.getUrl());
                    return redesSocialesRepositorio.save(red);
                })
                .orElseThrow(() -> new RuntimeException("Red social no encontrada con id: " + id));
    }

    public void eliminarRed(Long id) {
        redesSocialesRepositorio.deleteById(id);
    }

    public boolean existeRed(Long id) {
        return redesSocialesRepositorio.existsById(id);
    }
}
