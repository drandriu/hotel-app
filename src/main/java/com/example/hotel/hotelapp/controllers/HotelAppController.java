package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.hotelapp.dtos.HabitacionDTO;
import com.example.hotel.hotelapp.dtos.HotelDTO;
import com.example.hotel.hotelapp.dtos.HuespedDTO;
import com.example.hotel.hotelapp.dtos.ServicioDTO;
import com.example.hotel.hotelapp.entities.*;
import com.example.hotel.hotelapp.services.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class HotelAppController {

    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private HabitacionService habitacionService;
    
    @Autowired
    private HuespedService huespedService;
    
    @Autowired
    private ServicioService servicioService;

    // --------- Gestión de Hoteles ---------

    @GetMapping("/hoteles")
    @Operation(summary = "Obtener todos los hoteles")
    public ResponseEntity<?> obtenerHoteles() {
        try {
            List<HotelDTO> hoteles = hotelService.findAll();

            if (hoteles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay hoteles disponibles.");
            }
            return ResponseEntity.ok(hoteles);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de hoteles. Por favor, inténtelo más tarde.");
        }
    }
    
    @PostMapping("/hoteles")
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


    @PutMapping("/hoteles/{id}")
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

    @GetMapping("/hoteles/filtros")
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

    @DeleteMapping("/hoteles/{id}")
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

    // --------- Gestión de Habitaciones ---------

    @GetMapping("/habitaciones")
    @Operation(summary = "Obtener todas las habitaciones")
    public ResponseEntity<?> obtenerHabitaciones() {
        //return habitacionService.findAll();
        try {
            List<HabitacionDTO> habitaciones = habitacionService.findAll();

            if (habitaciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay habitaciones disponibles.");
            }
            return ResponseEntity.ok(habitaciones);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de habitaciones. Por favor, inténtelo más tarde.");
        }
    }

    @PostMapping("/habitaciones")
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

    @PutMapping("/habitaciones/{id}")
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

    @GetMapping("/habitaciones/filtros")
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

    @DeleteMapping("/habitaciones/{id}")
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

    // --------- Gestión de Huéspedes ---------
    @GetMapping("/huespedes")
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

    @PostMapping("/huespedes")
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

    @PutMapping("/huespedes/{id}")
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

    @GetMapping("/huespedes/filtros")
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

    @DeleteMapping("/huespedes/{id}")
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

    // --------- Gestión de Servicios ---------

    @GetMapping("/servicios")
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

    @PostMapping("/servicios")
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

    @PutMapping("/servicios/{id}")
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

    @GetMapping("/servicios/filtros")
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

    @DeleteMapping("/servicios/{id}")
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
