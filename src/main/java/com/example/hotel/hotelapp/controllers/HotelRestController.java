package com.example.hotel.hotelapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.hotelapp.entities.Hotel;
import com.example.hotel.hotelapp.repositories.HotelRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/hotel")
public class HotelRestController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }
    
    @PostMapping
    public Hotel registrarHotel(Hotel hotel){
        if(hotel.getNombre() == null || hotel.getDireccion() == null){
            throw new IllegalArgumentException("Nombre y dirección son obligatorios");
        }
        return hotelRepository.save(hotel);
    }

    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable int id, @RequestBody Hotel hotelDetails) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        hotel.setNombre(hotelDetails.getNombre());
        hotel.setDireccion(hotelDetails.getDireccion());
        hotel.setTelefono(hotelDetails.getTelefono());
        hotel.setEmail(hotelDetails.getEmail());
        hotel.setSitioWeb(hotelDetails.getSitioWeb());
        return hotelRepository.save(hotel);
    }

    @GetMapping("/filtros")
    public List<Hotel> buscarHotelesFiltro(@RequestParam Map<String, String> parameters) {
        return hotelRepository.findAll().stream()
                .filter(hotel -> parameters.get("nombre") == null || 
                                 hotel.getNombre().toLowerCase().contains(parameters.get("nombre").toLowerCase()))
                .filter(hotel -> parameters.get("direccion") == null || 
                                 hotel.getDireccion().toLowerCase().contains(parameters.get("direccion").toLowerCase()))
                .filter(hotel -> parameters.get("telefono") == null || 
                                 hotel.getTelefono().contains(parameters.get("telefono")))
                .filter(hotel -> parameters.get("email") == null || 
                                 hotel.getEmail().toLowerCase().contains(parameters.get("email").toLowerCase()))
                .filter(hotel -> parameters.get("sitioWeb") == null || 
                                 hotel.getSitioWeb().toLowerCase().contains(parameters.get("sitioWeb").toLowerCase()))
                .collect(Collectors.toList());
    }
    
}
