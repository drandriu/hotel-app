package com.example.hotel.hotelapp.services;

import com.example.hotel.hotelapp.dtos.DynamicSearchDTO;
import com.example.hotel.hotelapp.dtos.HuespedDTO;
import com.example.hotel.hotelapp.entities.Habitacion;
import com.example.hotel.hotelapp.entities.Huesped;
import com.example.hotel.hotelapp.repositories.HabitacionRepository;
import com.example.hotel.hotelapp.repositories.HuespedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;


@Service
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired HabitacionRepository habitacionRepository;

    public Page<HuespedDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);  // Página 0, tamaño 5
        return huespedRepository.findAll(pageable).map(this::convertirA_DTO);
    }

    public HuespedDTO registrarHuesped(HuespedDTO huespedDTO) {
        /*if (huesped.getNombre() == null || huesped.getDniPasaporte() == null
        || huesped.getNombre() == "" || huesped.getDniPasaporte() == "") {
            return null;
        }
        return huespedRepository.save(huesped);
        */
        Huesped huesped = convertirA_Entidad(huespedDTO);
        if (huesped.getNombre() == null || huesped.getDniPasaporte() == null
        || huesped.getNombre() == "" || huesped.getDniPasaporte() == "") {
            return null;
        }
        huespedRepository.save(huesped);
        return convertirA_DTO(huesped);
    }

    public HuespedDTO updateHuesped(int id, HuespedDTO huespedDTO) {
        Optional<Huesped> huespedOptional = huespedRepository.findById(id);
        if(!huespedOptional.isPresent()){
            return null;
        }
        Huesped huesped = huespedOptional.get();
        huesped.setNombre(huespedDTO.getNombre());
        huesped.setApellido(huespedDTO.getApellido());
        huesped.setDniPasaporte(huespedDTO.getDniPasaporte());
        huesped.setFechaCheckIn(huespedDTO.getFechaCheckIn());
        huesped.setFechaCheckOut(huespedDTO.getFechaCheckOut());
        if (huesped.getNombre() == null || huesped.getDniPasaporte() == null
        || huesped.getNombre() == "" || huesped.getDniPasaporte() == "") {
            return null;
        }
        Huesped huespedActualizado = huespedRepository.save(huesped);
        return convertirA_DTO(huespedActualizado);
    }

    public Page<HuespedDTO> buscarHuespedFiltro(Map<String, String> parameters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Integer idHuesped = parameters.get("idHuesped") != null ? Integer.parseInt(parameters.get("idHuesped")) : null;
        Integer idHabitacion = parameters.get("idHabitacion") != null ? Integer.parseInt(parameters.get("idHabitacion")) : null;
        String nombre = parameters.getOrDefault("nombre", null);
        String apellido = parameters.getOrDefault("apellido", null);
        String dniPasaporte = parameters.getOrDefault("dniPasaporte", null);
        Date fechaCheckIn = parameters.get("fechaCheckIn") != null ? Date.valueOf(parameters.get("fechaCheckIn")) : null;
        Date fechaCheckOut = parameters.get("fechaCheckOut") != null ? Date.valueOf(parameters.get("fechaCheckOut")) : null;
    
        Page <Huesped> huespedPage = huespedRepository.buscarConFiltros(idHuesped, idHabitacion, nombre, apellido, dniPasaporte, fechaCheckIn, fechaCheckOut, pageable);
        return huespedPage.map(this::convertirA_DTO);
    }

    public boolean eliminarHuesped(int id) {
        if (!huespedRepository.existsById(id)) {
            return false;
        }
    
        huespedRepository.deleteById(id);
        return true;
    }

    private HuespedDTO convertirA_DTO(Huesped huesped) {
    return new HuespedDTO(
        huesped.getIdHuesped(),
        huesped.getHabitacion().getId(),
        huesped.getNombre(),
        huesped.getApellido(),
        huesped.getDniPasaporte(),
        huesped.getFechaCheckIn(),
        huesped.getFechaCheckOut()
    );
    }

    private Huesped convertirA_Entidad(HuespedDTO huespedDTO) {
        Huesped huesped = new Huesped();
        huesped.setIdHuesped(huespedDTO.getIdHuesped());
        Optional<Habitacion> habitacionOptional = habitacionRepository.findById(huespedDTO.getIdHabitacion());
        if(!habitacionOptional.isPresent()){
            return null;
        }
        huesped.setHabitacion(habitacionOptional.get());
        huesped.setNombre(huespedDTO.getNombre());
        huesped.setApellido(huespedDTO.getApellido());
        huesped.setDniPasaporte(huespedDTO.getDniPasaporte());
        huesped.setFechaCheckIn(huespedDTO.getFechaCheckIn());
        huesped.setFechaCheckOut(huespedDTO.getFechaCheckOut());
        return huesped;
    }
    
    public Page<HuespedDTO> buscarHuespedDinamico(DynamicSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(
            searchDTO.getPage().getPageIndex(),
            searchDTO.getPage().getPageSize()
        );

        Specification<Huesped> spec = buildSpecification(searchDTO.getListSearchCriteria());

        if (searchDTO.getListOrderCriteria() != null && !searchDTO.getListOrderCriteria().isEmpty()) {
            Sort sort = buildSort(searchDTO.getListOrderCriteria());
            pageable = PageRequest.of(searchDTO.getPage().getPageIndex(), searchDTO.getPage().getPageSize(), sort);
        }

        Page<Huesped> huespedes = huespedRepository.findAll(spec, pageable);
        return huespedes.map(this::convertirA_DTO);
        }

    private Specification<Huesped> buildSpecification(List<DynamicSearchDTO.SearchCriteria> criteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
                
            for (DynamicSearchDTO.SearchCriteria criteria : criteriaList) {
                switch (criteria.getOperation().toLowerCase()) {
                    case "equals":
                        if (criteria.getKey().equals("habitacion")) {
                            // Aquí, buscamos por el id de la habitación (relación)
                            predicates.add(criteriaBuilder.equal(root.get("habitacion").get("id"), criteria.getValue()));
                        } else {
                            // Búsqueda normal para otros campos
                            predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
                        }
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

}
