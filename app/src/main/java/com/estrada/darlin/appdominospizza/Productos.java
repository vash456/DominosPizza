package com.estrada.darlin.appdominospizza;

/**
 * Created by DARLIN on 23/10/2016.
 */
public class Productos extends Promociones {

    private String Precio;
    private int idProducto;

    public Productos(int idImagen, String nombre, String descripcion, String precio, int idproducto) {
        super(idImagen, nombre, descripcion);
        this.Precio = precio;
        this.idProducto = idproducto;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
