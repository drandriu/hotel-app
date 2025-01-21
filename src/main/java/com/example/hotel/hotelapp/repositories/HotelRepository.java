package com.example.hotel.hotelapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.hotelapp.entities.Hotel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("SELECT h FROM Hotel h WHERE " +
       "(:id IS NULL OR h.id = :id) AND " +
       "(:nombre IS NULL OR h.nombre LIKE %:nombre%) AND " +
       "(:direccion IS NULL OR h.direccion LIKE %:direccion%) AND " +
       "(:telefono IS NULL OR h.telefono LIKE %:telefono%) AND " +
       "(:email IS NULL OR h.email LIKE %:email%) AND " +
       "(:sitioWeb IS NULL OR h.sitioWeb LIKE %:sitioWeb%)")
    Page<Hotel> buscarConFiltros(@Param("id") Integer id,
                            @Param("nombre") String nombre,
                            @Param("direccion") String direccion,
                            @Param("telefono") String telefono,
                            @Param("email") String email,
                            @Param("sitioWeb") String sitioWeb,
                            Pageable pageable);
}
