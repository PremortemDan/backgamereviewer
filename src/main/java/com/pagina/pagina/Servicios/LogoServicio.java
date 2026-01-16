package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.Logo;
import com.pagina.pagina.Repositorios.LogoRepositorio;

@Service
public class LogoServicio {

    @Autowired
    private LogoRepositorio logoRepositorio;

    public List<Logo> obtenerTodosLosLogos() {
        return logoRepositorio.findAll();
    }

    public Optional<Logo> obtenerLogoPorId(Long id) {
        return logoRepositorio.findById(id);
    }

    public Logo crearLogo(Logo logo) {
        return logoRepositorio.save(logo);
    }

    public Logo actualizarLogo(Long id, Logo logoActualizado) {
        return logoRepositorio.findById(id)
                .map(logo -> {
                    logo.setNombre(logoActualizado.getNombre());
                    logo.setDescripcion(logoActualizado.getDescripcion());
                    logo.setUrlImagen(logoActualizado.getUrlImagen());
                    logo.setFormato(logoActualizado.getFormato());
                    logo.setAncho(logoActualizado.getAncho());
                    logo.setAlto(logoActualizado.getAlto());
                    logo.setActivo(logoActualizado.getActivo());
                    return logoRepositorio.save(logo);
                })
                .orElseThrow(() -> new RuntimeException("Logo no encontrado con id: " + id));
    }

    public void eliminarLogo(Long id) {
        logoRepositorio.deleteById(id);
    }

    public boolean existeLogo(Long id) {
        return logoRepositorio.existsById(id);
    }
}
