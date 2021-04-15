package com.example.a5recyclerview;

public class  Carro {
    private int idImagen;
    private String nombre;
    private int likes;

    public Carro() {
    }

    public Carro(int idImagen, String nombre, int likes) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.likes = likes;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLikes() {
        return likes;
    }
}
