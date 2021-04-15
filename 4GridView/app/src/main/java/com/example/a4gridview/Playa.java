package com.example.a4gridview;

public class Playa {
    private String nombre;
    private int idImagen;

    public Playa(String nombre, int idImagen) {
        this.nombre = nombre;
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public int getId() {
        return nombre.hashCode();
    }

    public static Playa getItem(int id) {
        for (Playa item : MainActivity.ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
