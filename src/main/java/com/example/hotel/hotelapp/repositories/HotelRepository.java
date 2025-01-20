package com.example.hotel.hotelapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.hotelapp.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}
