package com.example.hotel.hotelapp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.hotelapp.dtos.DynamicSearchDTO;
import com.example.hotel.hotelapp.dtos.HabitacionDTO;
import com.example.hotel.hotelapp.services.HabitacionService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {
    @Autowired
    private HabitacionService habitacionService;
    // --------- Gestión de Habitaciones ---------

    @GetMapping
    @Operation(summary = "Obtener todas las habitaciones")
    public ResponseEntity<?> obtenerHabitaciones(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        //return habitacionService.findAll();
        try {
            
            Page<HabitacionDTO> habitaciones = habitacionService.findAll(page,size);

            if (habitaciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay habitaciones disponibles.");
            }
            return ResponseEntity.ok(habitaciones);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de habitaciones. Por favor, inténtelo más tarde.");
        }
    }

    @PostMapping
    @Operation(summary = "Registrar Habitacion")
    public ResponseEntity<?> registrarHabitacion(@RequestBody HabitacionDTO habitacionDTO) {
        //return habitacionService.registrarHabitacion(habitacion);
        try {

            HabitacionDTO nuevaHabitacion = habitacionService.registrarHabitacion(habitacionDTO);
    
            if (nuevaHabitacion == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos para el registro de habitación.");
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaHabitacion);
    
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al registrar la habitación.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Habitacion")
    public ResponseEntity<?> actualizarHabitacion(@PathVariable int id, @RequestBody HabitacionDTO habitacionDetails) {
        //return habitacionService.updateHabitacion(id, habitacionDetails);
        try {
            HabitacionDTO habitacionActualizada = habitacionService.updateHabitacion(id, habitacionDetails);
    
            if (habitacionActualizada == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Datos inválidos o la habitación no fue encontrada.");
            }
    
            return ResponseEntity.ok(habitacionActualizada);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al actualizar la habitación.");
        }
    }

    @GetMapping("/filtros")
    @Operation(summary = "Buscar Habitacion con filtros")
    public ResponseEntity<?> buscarHabitacionesFiltro(@RequestParam Map<String, String> parameters,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        //return habitacionService.buscarHabitacionesFiltro(parameters, page, size);
        try {
            Page<HabitacionDTO> habitaciones = habitacionService.buscarHabitacionesFiltro(parameters, page, size);
    
            if (habitaciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
    
            return ResponseEntity.ok(habitaciones);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al buscar habitaciones.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una habitacion(y sus huespedes)")
    public ResponseEntity<?> eliminarHabitacion(@PathVariable int id){
        //habitacionService.eliminarHabitacion(id);
        try {
            boolean eliminado = habitacionService.eliminarHabitacion(id);
    
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Habitacion con ID " + id + " no encontrado.");
            }
    
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 sin contenido
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al eliminar la habitación.");
        }
    }
    @PostMapping("/buscar")
    @Operation(summary = "Búsqueda dinámica de huéspedes")
    public ResponseEntity<?> buscarHabitaciones(@RequestBody DynamicSearchDTO searchRequest) {
        try {
            Page<HabitacionDTO> resultado = habitacionService.buscarHabitacionesDinamico(searchRequest);
            if (resultado.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar la búsqueda dinámica de huéspedes.");
        }
    }
}
