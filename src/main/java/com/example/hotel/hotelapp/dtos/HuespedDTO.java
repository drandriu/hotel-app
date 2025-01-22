package com.example.hotel.hotelapp.dtos;

import java.sql.Date;

public class HuespedDTO {
    private int idHuesped;
    private int idHabitacion;
    private String nombre;
    private String apellido;
    private String dniPasaporte;
    private Date fechaCheckIn;
    private Date fechaCheckOut;

    // Constructor vacío
    public HuespedDTO() {}

    // Constructor con parámetros
    public HuespedDTO(int idHuesped, int idHabitacion, String nombre, String apellido, 
                      String dniPasaporte, Date fechaCheckIn, Date fechaCheckOut) {
        this.idHuesped = idHuesped;
        this.idHabitacion = idHabitacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dniPasaporte = dniPasaporte;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
    }

    // Getters y Setters
    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDniPasaporte() {
        return dniPasaporte;
    }

    public void setDniPasaporte(String dniPasaporte) {
        this.dniPasaporte = dniPasaporte;
    }

    public Date getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(Date fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public Date getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setFechaCheckOut(Date fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }
}
