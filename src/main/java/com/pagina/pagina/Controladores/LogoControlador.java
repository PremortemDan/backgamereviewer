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

import com.pagina.pagina.Modelos.Logo;
import com.pagina.pagina.Servicios.LogoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/logos")
@Tag(name = "Logos", description = "API para la gestión de logos del sitio")
public class LogoControlador {

    @Autowired
    private LogoServicio logoServicio;

    @Operation(summary = "Obtener todos los logos", description = "Retorna una lista con todos los logos registrados")
    @GetMapping
    public ResponseEntity<List<Logo>> obtenerTodosLosLogos() {
        List<Logo> logos = logoServicio.obtenerTodosLosLogos();
        return ResponseEntity.ok(logos);
    }

    @Operation(summary = "Obtener logo por ID", description = "Retorna un logo específico basado en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Logo> obtenerLogoPorId(
            @Parameter(description = "ID del logo a buscar", required = true) @PathVariable Long id) {
        return logoServicio.obtenerLogoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo logo", description = "Crea y registra un nuevo logo en el sistema")
    @PostMapping
    public ResponseEntity<Logo> crearLogo(
            @Parameter(description = "Datos del logo a crear", required = true) @RequestBody Logo logo) {
        Logo nuevoLogo = logoServicio.crearLogo(logo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLogo);
    }

    @Operation(summary = "Actualizar un logo", description = "Actualiza los datos de un logo existente")
    @PutMapping("/{id}")
    public ResponseEntity<Logo> actualizarLogo(
            @Parameter(description = "ID del logo a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del logo", required = true) @RequestBody Logo logo) {
        try {
            Logo logoActualizado = logoServicio.actualizarLogo(id, logo);
            return ResponseEntity.ok(logoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un logo", description = "Elimina un logo del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogo(
            @Parameter(description = "ID del logo a eliminar", required = true) @PathVariable Long id) {
        if (!logoServicio.existeLogo(id)) {
            return ResponseEntity.notFound().build();
        }
        logoServicio.eliminarLogo(id);
        return ResponseEntity.noContent().build();
    }
}
