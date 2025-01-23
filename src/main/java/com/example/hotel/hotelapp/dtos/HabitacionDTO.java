package com.example.hotel.hotelapp.dtos;

public class HabitacionDTO {
    private int id;
    private int idHotel;
    private String numeroHabitacion;
    private String tipo;
    private float precioNoche;

    public HabitacionDTO() {
    }

    public HabitacionDTO(int id, int idHotel, String numeroHabitacion, String tipo, float precioNoche) {
        this.id = id;
        this.idHotel = idHotel;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(String numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(float precioNoche) {
        this.precioNoche = precioNoche;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", idHotel=" + idHotel +
                ", numeroHabitacion='" + numeroHabitacion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precioNoche=" + precioNoche +
                '}';
    }
}
