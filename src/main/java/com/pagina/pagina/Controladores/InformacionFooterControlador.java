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

import com.pagina.pagina.Modelos.InformacionFooter;
import com.pagina.pagina.Servicios.InformacionFooterServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/informacion-footer")
@Tag(name = "Información Footer", description = "API para la gestión de información del footer")
public class InformacionFooterControlador {

    @Autowired
    private InformacionFooterServicio informacionFooterServicio;

    @Operation(summary = "Obtener toda la información del footer", description = "Retorna una lista con toda la información del footer registrada")
    @GetMapping
    public ResponseEntity<List<InformacionFooter>> obtenerTodaLaInformacion() {
        List<InformacionFooter> informacion = informacionFooterServicio.obtenerTodaLaInformacion();
        return ResponseEntity.ok(informacion);
    }

    @Operation(summary = "Obtener información por ID", description = "Retorna información específica del footer basada en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<InformacionFooter> obtenerInformacionPorId(
            @Parameter(description = "ID de la información a buscar", required = true) @PathVariable Long id) {
        return informacionFooterServicio.obtenerInformacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva información del footer", description = "Crea y registra nueva información del footer en el sistema")
    @PostMapping
    public ResponseEntity<InformacionFooter> crearInformacion(
            @Parameter(description = "Datos de la información a crear", required = true) @RequestBody InformacionFooter informacion) {
        InformacionFooter nuevaInformacion = informacionFooterServicio.crearInformacion(informacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInformacion);
    }

    @Operation(summary = "Actualizar información del footer", description = "Actualiza los datos de información del footer existente")
    @PutMapping("/{id}")
    public ResponseEntity<InformacionFooter> actualizarInformacion(
            @Parameter(description = "ID de la información a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la información", required = true) @RequestBody InformacionFooter informacion) {
        try {
            InformacionFooter informacionActualizada = informacionFooterServicio.actualizarInformacion(id, informacion);
            return ResponseEntity.ok(informacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar información del footer", description = "Elimina información del footer del sistema basado en su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInformacion(
            @Parameter(description = "ID de la información a eliminar", required = true) @PathVariable Long id) {
        if (!informacionFooterServicio.existeInformacion(id)) {
            return ResponseEntity.notFound().build();
        }
        informacionFooterServicio.eliminarInformacion(id);
        return ResponseEntity.noContent().build();
    }
}
