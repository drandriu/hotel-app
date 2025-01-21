package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.hotelapp.entities.*;
import com.example.hotel.hotelapp.services.*;

import io.swagger.v3.oas.annotations.Operation;

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
    public List<Hotel> obtenerHoteles() {
        return hotelService.findAll();
    }
    
    @PostMapping("/hoteles")
    @Operation(summary = "Registrar Hotel")
    public Hotel registrarHotel(@RequestBody Hotel hotel) {
        return hotelService.registrarHotel(hotel);
    }

    @PutMapping("/hoteles/{id}")
    @Operation(summary = "Actualizar Hotel")
    public Hotel actualizarHotel(@PathVariable int id, @RequestBody Hotel hotelDetails) {
        return hotelService.updateHotel(id, hotelDetails);
    }

    @GetMapping("/hoteles/filtros")
    @Operation(summary = "Buscar Hotel con filtros")
    public List<Hotel> buscarHotelesFiltro(@RequestParam Map<String, String> parameters) {
        return hotelService.buscarHotelesFiltro(parameters);
    }

    @DeleteMapping("/hoteles/{id}")
    @Operation(summary = "Eliminar un hotel y sus habitaciones")
    public ResponseEntity<String> eliminarHotel(@PathVariable int id) {
        hotelService.eliminarHotel(id);
        return ResponseEntity.ok("Hotel eliminado correctamente");
    }

    // --------- Gestión de Habitaciones ---------

    @GetMapping("/habitaciones")
    @Operation(summary = "Obtener todas las habitaciones")
    public List<Habitacion> obtenerHabitaciones() {
        return habitacionService.findAll();
    }

    @PostMapping("/habitaciones")
    @Operation(summary = "Registrar Habitacion")
    public Habitacion registrarHabitacion(@RequestBody Habitacion habitacion) {
        return habitacionService.registrarHabitacion(habitacion);
    }

    @PutMapping("/habitaciones/{id}")
    @Operation(summary = "Actualizar Habitacion")
    public Habitacion actualizarHabitacion(@PathVariable int id, @RequestBody Habitacion habitacionDetails) {
        return habitacionService.updateHabitacion(id, habitacionDetails);
    }

    @GetMapping("/habitaciones/filtros")
    @Operation(summary = "Buscar Habitacion con filtros")
    public List<Habitacion> buscarHabitacionesFiltro(@RequestParam Map<String, String> parameters) {
        return habitacionService.buscarHabitacionesFiltro(parameters);
    }

    @DeleteMapping("/habitaciones/{id}")
    @Operation(summary = "Eliminar una habitacion(y sus huespedes)")
    public void eliminarHabitacion(@PathVariable int id){
        habitacionService.eliminarHabitacion(id);
    }

    // --------- Gestión de Huéspedes ---------

    @GetMapping("/huespedes")
    @Operation(summary = "Obtener todos los huespedes")
    public List<Huesped> obtenerHuespedes() {
        return huespedService.findAll();
    }

    @PostMapping("/huespedes")
    @Operation(summary = "Registrar huesped")
    public Huesped registrarHuesped(@RequestBody Huesped huesped) {
        return huespedService.registrarHuesped(huesped);
    }

    @PutMapping("/huespedes/{id}")
    @Operation(summary = "Actualizar huespedes")
    public Huesped actualizarHuesped(@PathVariable int id, @RequestBody Huesped huespedDetails) {
        return huespedService.updateHuesped(id, huespedDetails);
    }

    @GetMapping("/huespedes/filtros")
    @Operation(summary = "Buscar huesped con filtros")
    public List<Huesped> buscarHuespedFiltro(@RequestParam Map<String, String> parameters) {
        return huespedService.buscarHuespedFiltro(parameters);
    }

    @DeleteMapping("/huespedes/{id}")
    @Operation(summary = "Eliminar huesped")
    public void eliminarHuesped(@PathVariable int id) {
        huespedService.eliminarHuesped(id);
    }

    // --------- Gestión de Servicios ---------

    @GetMapping("/servicios")
    @Operation(summary = "Obtener todos los servicios")
    public List<Servicio> obtenerServicios() {
        return servicioService.findAll();
    }

    @PostMapping("/servicios")
    @Operation(summary = "Registrar servicio")
    public Servicio registrarServicio(@RequestBody Servicio servicio) {
        return servicioService.registrarServicio(servicio);
    }

    @PutMapping("/servicios/{id}")
    @Operation(summary = "Actualizar servicio")
    public Servicio actualizarServicio(@PathVariable int id, @RequestBody Servicio servicioDetails) {
        return servicioService.updateServicio(id, servicioDetails);
    }

    @GetMapping("/servicios/filtros")
    @Operation(summary = "Obtener todos los huespedes")
    public List<Servicio> buscarServiciosFiltro(@RequestParam Map<String, String> parameters) {
        return servicioService.buscarServiciosFiltro(parameters);
    }

    @DeleteMapping("/servicios/{id}")
    @Operation(summary = "Eliminar servicio")
    public void eliminarServicio(@PathVariable int id) {
        servicioService.eliminarServicio(id);
    }
}
