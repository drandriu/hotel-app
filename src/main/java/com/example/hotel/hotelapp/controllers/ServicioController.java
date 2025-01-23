package com.example.hotel.hotelapp.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.hotelapp.dtos.ServicioDTO;
import com.example.hotel.hotelapp.services.ServicioService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    // --------- Gestión de Servicios ---------

    @GetMapping
    @Operation(summary = "Obtener todos los servicios")
    public ResponseEntity<?> obtenerServicios() {
        try {
            List<ServicioDTO> servicios = servicioService.findAll();

            if (servicios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay servicios disponibles.");
            }
            return ResponseEntity.ok(servicios);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de servicios.");
        }
    }

    @PostMapping
    @Operation(summary = "Registrar servicio")
    public ResponseEntity<?> registrarServicio(@Valid @RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO nuevoServicio = servicioService.registrarServicio(servicioDTO);

            if (nuevoServicio == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos para el registro de servicio.");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoServicio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al registrar el servicio.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio")
    public ResponseEntity<?> actualizarServicio(@PathVariable int id, @Valid @RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO servicioActualizado = servicioService.updateServicio(id, servicioDTO);

            if (servicioActualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Servicio no encontrado o error de datos.");
            }

            return ResponseEntity.ok(servicioActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al actualizar el servicio.");
        }
    }

    @GetMapping("/filtros")
    @Operation(summary = "Obtener todos los huespedes")
    public ResponseEntity<?> buscarServiciosFiltro(@RequestParam Map<String, String> parameters,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ServicioDTO> servicios = servicioService.buscarServiciosFiltro(parameters, page, size);

            if (servicios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al buscar servicios.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar servicio")
    public ResponseEntity<?> eliminarServicio(@PathVariable int id) {
        try {
            boolean eliminado = servicioService.eliminarServicio(id);

            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Servicio con ID " + id + " no encontrado.");
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al eliminar el servicio.");
        }
    }
}
