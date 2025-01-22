package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.hotel.hotelapp.services.HuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.hotelapp.dtos.HabitacionDTO;
import com.example.hotel.hotelapp.dtos.HotelDTO;
import com.example.hotel.hotelapp.dtos.HuespedDTO;
import com.example.hotel.hotelapp.dtos.ServicioDTO;
import com.example.hotel.hotelapp.services.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/huespedes")
public class HuespedController {

    @Autowired
    private HuespedService huespedService;


    // --------- Gestión de Huéspedes ---------
    @GetMapping
    @Operation(summary = "Obtener todos los huespedes")
    public ResponseEntity<?> obtenerHuespedes() {
        //return huespedService.findAll();
        try {
            List<HuespedDTO> huespedes = huespedService.findAll();
            if (huespedes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay huéspedes disponibles.");
            }
            return ResponseEntity.ok(huespedes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de huéspedes. Por favor, inténtelo más tarde.");
        }
    }

    @PostMapping
    @Operation(summary = "Registrar huesped")
    public ResponseEntity<?> registrarHuesped(@RequestBody HuespedDTO huespedDTO) {
        //return huespedService.registrarHuesped(huesped);
        try {
            HuespedDTO nuevoHuesped = huespedService.registrarHuesped(huespedDTO);
    
            if (nuevoHuesped == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos para el registro de huesped.");
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHuesped);
    
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al registrar el huesped.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar huespedes")
    public ResponseEntity<?> actualizarHuesped(@PathVariable int id, @RequestBody HuespedDTO huespedDTO) {
        try {
            HuespedDTO huespedActualizado = huespedService.updateHuesped(id, huespedDTO);
    
            if (huespedActualizado == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Datos inválidos o el huésped no fue encontrado.");
            }
    
            return ResponseEntity.ok(huespedActualizado);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al actualizar el huésped.");
        }
    }

    @GetMapping("/filtros")
    @Operation(summary = "Buscar huesped con filtros")
    public ResponseEntity<?> buscarHuespedFiltro(@RequestParam Map<String, String> parameters,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        //return huespedService.buscarHuespedFiltro(parameters, page, size);
        try {
            Page<HuespedDTO> huespedes = huespedService.buscarHuespedFiltro(parameters, page, size);
    
            if (huespedes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
    
            return ResponseEntity.ok(huespedes);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al buscar huespedes.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar huesped")
    public ResponseEntity<?> eliminarHuesped(@PathVariable int id) {
        //huespedService.eliminarHuesped(id);
        try {
            boolean eliminado = huespedService.eliminarHuesped(id);
    
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Huesped con ID " + id + " no encontrado.");
            }
    
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 sin contenido
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al eliminar el huesped.");
        }
    }

}
