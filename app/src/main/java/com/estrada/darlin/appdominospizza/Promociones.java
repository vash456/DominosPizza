package com.estrada.darlin.appdominospizza;

/**
 * Created by DARLIN on 02/10/2016.
 */
public class Promociones {

    private int idImagen;
    private String nombre, descripcion;

    public Promociones(int idImagen, String nombre, String descripcion) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
