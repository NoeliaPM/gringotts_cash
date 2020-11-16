package com.example.gringottscash;

public class Movimientos {
    String producto, tienda, precio,fecha,hora;
    public Movimientos(String producto, String tienda, String precio, String fecha, String hora){
        this.precio=precio;
        this.producto=producto;
        this.tienda=tienda;
        this.fecha=fecha;
        this.hora=hora;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public String getProducto() {
        return producto;
    }

    public String getTienda() {
        return tienda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
