package com.example.hotel.hotelapp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Servicio {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String nombre;
    private String descripcion;

    // Constructor vacío
    public Servicio() {
    }

    public Servicio(int id, String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }




    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
