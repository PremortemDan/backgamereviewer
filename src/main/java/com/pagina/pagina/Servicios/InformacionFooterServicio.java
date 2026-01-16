package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.InformacionFooter;
import com.pagina.pagina.Repositorios.InformacionFooterRepositorio;

@Service
public class InformacionFooterServicio {

    @Autowired
    private InformacionFooterRepositorio informacionFooterRepositorio;

    public List<InformacionFooter> obtenerTodaLaInformacion() {
        return informacionFooterRepositorio.findAll();
    }

    public Optional<InformacionFooter> obtenerInformacionPorId(Long id) {
        return informacionFooterRepositorio.findById(id);
    }

    public InformacionFooter crearInformacion(InformacionFooter informacion) {
        return informacionFooterRepositorio.save(informacion);
    }

    public InformacionFooter actualizarInformacion(Long id, InformacionFooter informacionActualizada) {
        return informacionFooterRepositorio.findById(id)
                .map(informacion -> {
                    informacion.setTexto(informacionActualizada.getTexto());
                    informacion.setUrl(informacionActualizada.getUrl());
                    informacion.setOrden(informacionActualizada.getOrden());
                    return informacionFooterRepositorio.save(informacion);
                })
                .orElseThrow(() -> new RuntimeException("Información footer no encontrada con id: " + id));
    }

    public void eliminarInformacion(Long id) {
        informacionFooterRepositorio.deleteById(id);
    }

    public boolean existeInformacion(Long id) {
        return informacionFooterRepositorio.existsById(id);
    }
}
