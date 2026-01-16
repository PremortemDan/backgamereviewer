package com.pagina.pagina.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagina.pagina.Modelos.MarcasAsociadas;
import com.pagina.pagina.Repositorios.MarcasAsociadasRepositorio;

@Service
public class MarcasAsociadasServicio {

    @Autowired
    private MarcasAsociadasRepositorio marcasAsociadasRepositorio;

    public List<MarcasAsociadas> obtenerTodasLasMarcas() {
        return marcasAsociadasRepositorio.findAll();
    }

    public Optional<MarcasAsociadas> obtenerMarcaPorId(Long id) {
        return marcasAsociadasRepositorio.findById(id);
    }

    public MarcasAsociadas crearMarca(MarcasAsociadas marca) {
        return marcasAsociadasRepositorio.save(marca);
    }

    public MarcasAsociadas actualizarMarca(Long id, MarcasAsociadas marcaActualizada) {
        return marcasAsociadasRepositorio.findById(id)
                .map(marca -> {
                    marca.setNombre(marcaActualizada.getNombre());
                    marca.setLogoUrl(marcaActualizada.getLogoUrl());
                    marca.setUrl(marcaActualizada.getUrl());
                    return marcasAsociadasRepositorio.save(marca);
                })
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + id));
    }

    public void eliminarMarca(Long id) {
        marcasAsociadasRepositorio.deleteById(id);
    }

    public boolean existeMarca(Long id) {
        return marcasAsociadasRepositorio.existsById(id);
    }
}
