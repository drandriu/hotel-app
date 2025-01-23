package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.dtos.ServicioDTO;
import com.example.hotel.hotelapp.entities.Hotel;
import com.example.hotel.hotelapp.entities.Servicio;
import com.example.hotel.hotelapp.repositories.HotelRepository;
import com.example.hotel.hotelapp.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;


@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<ServicioDTO> findAll() {
        return servicioRepository.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    public ServicioDTO registrarServicio(ServicioDTO servicioDTO) {
        Servicio servicio = convertirA_Entidad(servicioDTO);
        if (servicio.getNombre() == null || servicio.getDescripcion() == null
        || servicio.getNombre() == "" || servicio.getDescripcion() == "") {
            return null;
        }
        Servicio servicioGuardado = servicioRepository.save(servicio);
        return convertirA_DTO(servicioGuardado);
    }

    public ServicioDTO updateServicio(int id, ServicioDTO servicioDTO) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        if (!servicioOptional.isPresent()) {
            return null;
        }
        Servicio servicio = servicioOptional.get();
        if (servicio.getNombre() == null || servicio.getDescripcion() == null
        || servicio.getNombre() == "" || servicio.getDescripcion() == "") {
            return null;
        }
        servicio.setNombre(servicioDTO.getNombre());
        servicio.setDescripcion(servicioDTO.getDescripcion());

        Servicio servicioActualizado = servicioRepository.save(servicio);
        return convertirA_DTO(servicioActualizado);
    }

    public Page<ServicioDTO> buscarServiciosFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String idStr = parameters.getOrDefault("id", null);
        Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;
        String nombre = parameters.getOrDefault("nombre", null);
        String descripcion = parameters.getOrDefault("descripcion", null);

        Page<Servicio> servicios = servicioRepository.buscarConFiltros(id, nombre, descripcion, pageable);
        return servicios.map(this::convertirA_DTO);
    }

    @Transactional
    public boolean eliminarServicio(int id) {
        if (!servicioRepository.existsById(id)) {
            return false;
        }
        servicioRepository.deleteById(id);
        return true;
    }

    public boolean asignarServicioAHotel(int servicioId, int hotelId) {
        Optional<Servicio> servicio = servicioRepository.findById(servicioId);
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);

        if (servicio.isPresent() && hotel.isPresent()) {
            Hotel h = hotel.get();
            Servicio s = servicio.get();
            h.getServicios().add(s);
            hotelRepository.save(h);

            return true;
        }

        return false;
    }



    private ServicioDTO convertirA_DTO(Servicio servicio) {
        return new ServicioDTO(servicio.getId(), servicio.getNombre(), servicio.getDescripcion());
    }

    private Servicio convertirA_Entidad(ServicioDTO servicioDTO) {
        Servicio servicio = new Servicio();
        servicio.setNombre(servicioDTO.getNombre());
        servicio.setDescripcion(servicioDTO.getDescripcion());
        return servicio;
    }
}
