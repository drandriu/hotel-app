package com.example.hotel.hotelapp.dtos;



public class ServicioDTO {
    private int id;  // Se usará en las respuestas, opcional en las peticiones


    private String nombre;

  
    private String descripcion;

    public ServicioDTO() {
    }

    public ServicioDTO(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

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
