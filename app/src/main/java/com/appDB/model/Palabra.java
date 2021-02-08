package com.appDB.model;

public class Palabra {
    private long id;
    private String palabra;
    private String definicion;

    public Palabra(long id, String palabra, String definicion) {
        this.id = id;
        this.palabra = palabra;
        this.definicion = definicion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }
}
