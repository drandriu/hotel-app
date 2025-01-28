package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.hotelapp.dtos.DynamicSearchDTO;
import com.example.hotel.hotelapp.dtos.HotelDTO;
import com.example.hotel.hotelapp.services.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelService;
// --------- Gestión de Hoteles ---------

@GetMapping
@Operation(summary = "Obtener todos los hoteles")
public ResponseEntity<?> obtenerHoteles(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size) {
    try {
        Page<HotelDTO> hoteles = hotelService.findAll(page, size);

        if (hoteles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay hoteles disponibles.");
        }
        return ResponseEntity.ok(hoteles);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener la lista de hoteles. Por favor, inténtelo más tarde.");
    }
}

@PostMapping
@Operation(summary = "Registrar Hotel")
public ResponseEntity<?> registrarHotel(@RequestBody HotelDTO hotel) {
    try {
        HotelDTO nuevoHotel = hotelService.registrarHotel(hotel);

        if (nuevoHotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos para el registro de hotel");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHotel);

    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al registrar el hotel.");
    }
}


@PutMapping("/{id}")
@Operation(summary = "Actualizar Hotel")
public ResponseEntity<?> actualizarHotel(@PathVariable int id, @RequestBody HotelDTO hotelDetails) {
    try {
        HotelDTO hotelActualizado = hotelService.updateHotel(id, hotelDetails);

        if (hotelActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Datos inválidos o el hotel no fue encontrado.");
        }

        return ResponseEntity.ok(hotelActualizado);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error interno al actualizar el hotel.");
    }
}

@GetMapping("/filtros")
@Operation(summary = "Buscar Hotel con filtros")
public ResponseEntity<?> buscarHotelesFiltro(
    @RequestParam Map<String, String> parameters,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            Page<HotelDTO> hoteles = hotelService.buscarHotelesFiltro(parameters, page, size);
            if (hoteles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
    
            return ResponseEntity.ok(hoteles);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno al buscar hoteles.");
        }
}

@DeleteMapping("/{id}")
@Operation(summary = "Eliminar un hotel y sus habitaciones")
public ResponseEntity<?> eliminarHotel(@PathVariable int id) {
    try {
        boolean eliminado = hotelService.eliminarHotel(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Hotel con ID " + id + " no encontrado.");
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 sin contenido

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error interno al eliminar el hotel.");
    }
}
    @PostMapping("/buscar")
    @Operation(summary = "Búsqueda dinámica de huéspedes")
    public ResponseEntity<?> buscarHoteles(@RequestBody DynamicSearchDTO searchRequest) {
        try {
            Page<HotelDTO> resultado = hotelService.buscarHotelDinamico(searchRequest);
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
