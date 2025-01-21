package com.example.hotel.hotelapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.hotelapp.entities.Huesped;

public interface HuespedRepository extends JpaRepository<Huesped, Integer>{
    void deleteByIdHabitacion(int id);
}
