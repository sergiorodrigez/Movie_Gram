package main.java.domain;

import java.io.Serializable;

public class Pelicula implements Serializable {

    private int id_p;
    private String nombre;
    private int year;
    private String url;

    public Pelicula(int id_p, String nombre, int year, String url){
        this.id_p = id_p;
        this.nombre = nombre;
        this.year = year;
        this.url = url;
    }

    public int getId_p(){return id_p;}

    public String getNombre() {
        return nombre;
    }

    public String getUrl(){
        return url;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString(){
        return "---PELICULA---\nId: " + id_p +
                "\nNombre: " + nombre +
                "\naño: " + year +
                "\nurl: " + url;
    }
}
