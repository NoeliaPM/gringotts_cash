package com.example.gringottscash;

public class Categoria {
    String producto, tienda, precio,fecha,hora,tipo_moneda;
    public Categoria(String producto, String tienda, String precio, String tipo_moneda, String fecha, String hora){
        this.precio=precio;
        this.producto=producto;
        this.tienda=tienda;
        this.fecha=fecha;
        this.hora=hora;
        this.tipo_moneda=tipo_moneda;
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

    public String getTipo_moneda() {
        return tipo_moneda;
    }

    public void setTipo_moneda(String tipo_moneda) {
        this.tipo_moneda = tipo_moneda;
    }
}
