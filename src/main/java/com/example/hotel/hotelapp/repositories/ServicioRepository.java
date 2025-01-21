package com.example.hotel.hotelapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.hotelapp.entities.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    @Query("SELECT s FROM Servicio s " +
           "WHERE (:id IS NULL OR s.id = :id) " +
           "AND (:nombre IS NULL OR s.nombre LIKE %:nombre%) " +
           "AND (:descripcion IS NULL OR s.descripcion LIKE %:descripcion%)")
    Page<Servicio> buscarConFiltros(@Param("id") Integer id,
                                    @Param("nombre") String nombre, 
                                    @Param("descripcion") String descripcion, 
                                    Pageable pageable);
}
