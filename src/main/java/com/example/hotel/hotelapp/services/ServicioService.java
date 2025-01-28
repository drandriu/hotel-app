package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.dtos.DynamicSearchDTO;
import com.example.hotel.hotelapp.dtos.ServicioDTO;
import com.example.hotel.hotelapp.entities.Hotel;
import com.example.hotel.hotelapp.entities.Servicio;
import com.example.hotel.hotelapp.repositories.HotelRepository;
import com.example.hotel.hotelapp.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;


@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Page<ServicioDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);  // Página 0, tamaño 5
        return servicioRepository.findAll(pageable).map(this::convertirA_DTO);
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

    public Page<ServicioDTO> buscarServiciosDinamico(DynamicSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(
            searchDTO.getPage().getPageIndex(),
            searchDTO.getPage().getPageSize()
        );

        Specification<Servicio> spec = buildSpecification(searchDTO.getListSearchCriteria());

        if (searchDTO.getListOrderCriteria() != null && !searchDTO.getListOrderCriteria().isEmpty()) {
            Sort sort = buildSort(searchDTO.getListOrderCriteria());
            pageable = PageRequest.of(searchDTO.getPage().getPageIndex(), searchDTO.getPage().getPageSize(), sort);
        }

        Page<Servicio> servicios = servicioRepository.findAll(spec, pageable);
        return servicios.map(this::convertirA_DTO);
        }

    private Specification<Servicio> buildSpecification(List<DynamicSearchDTO.SearchCriteria> criteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
                
        for (DynamicSearchDTO.SearchCriteria criteria : criteriaList) {
            switch (criteria.getOperation().toLowerCase()) {
                case "equals":
                    predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case "lower":
                    predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case "higher":
                    predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case "like":
                    predicates.add(criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
                    }
                }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Sort buildSort(List<DynamicSearchDTO.OrderCriteria> orderCriteria) {
        List<Sort.Order> orders = orderCriteria.stream()
            .map(criteria -> new Sort.Order(
                criteria.getValuesortOrder().equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                criteria.getSortBy()
            ))
            .collect(Collectors.toList());
        return Sort.by(orders);
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
