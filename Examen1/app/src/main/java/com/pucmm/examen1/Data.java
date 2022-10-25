package com.pucmm.examen1;

public class Data {
    private String articulo;
    private String descripcion;
    private int price;

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Data(String articulo, String descripcion, int price)
    {
        this.articulo = articulo;
        this.descripcion = descripcion;
        this.price = price;
    }
}
