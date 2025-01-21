package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.entities.Huesped;
import com.example.hotel.hotelapp.repositories.HuespedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    public List<Huesped> findAll() {
        return huespedRepository.findAll();
    }

    public Huesped registrarHuesped(Huesped huesped) {
        if (huesped.getNombre() == null || huesped.getDniPasaporte() == null) {
            throw new IllegalArgumentException("Nombre y DNI/Pasaporte son obligatorios");
        }
        return huespedRepository.save(huesped);
    }

    public Huesped updateHuesped(int id, Huesped huespedDetails) {
        Huesped huesped = huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));
        huesped.setNombre(huespedDetails.getNombre());
        huesped.setApellido(huespedDetails.getApellido());
        huesped.setDniPasaporte(huespedDetails.getDniPasaporte());
        huesped.setFechaCheckIn(huespedDetails.getFechaCheckIn());
        huesped.setFechaCheckOut(huespedDetails.getFechaCheckOut());
        return huespedRepository.save(huesped);
    }

    public List<Huesped> buscarHuespedFiltro(Map<String, String> parameters) {
        return huespedRepository.findAll().stream()
                .filter(huesped -> parameters.get("nombre") == null || 
                        huesped.getNombre().toLowerCase().contains(parameters.get("nombre").toLowerCase()))
                .filter(huesped -> parameters.get("apellido") == null || 
                        huesped.getApellido().toLowerCase().contains(parameters.get("apellido").toLowerCase()))
                .filter(huesped -> parameters.get("dniPasaporte") == null || 
                        huesped.getDniPasaporte().toLowerCase().contains(parameters.get("dniPasaporte").toLowerCase()))
                .collect(Collectors.toList());
    }

    public void eliminarHuesped(int id) {
        Huesped huesped = huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));
        huespedRepository.delete(huesped);
    }
}
