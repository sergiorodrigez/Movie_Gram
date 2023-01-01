package main.java.domain;

import javax.swing.*;
import java.io.Serializable;

public class Playlist implements Serializable {

    private String nombre;
    private String nombrePropietario;
    private int id_playlist;
    private ImageIcon imagen;

    public Playlist(int id_playlist, String nombre, String nombrePropietario, ImageIcon imagen){
        this.nombre= nombre;
        this.id_playlist=id_playlist;
        this.nombrePropietario = nombrePropietario;
        this.imagen = imagen;
    }

    public ImageIcon getImagen(){
        return imagen;
    }

    public void setImagen(ImageIcon imagen){
        this.imagen = imagen;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }


    public String getNombre(){
        return nombre;}

    public int getId_playlist(){
        return id_playlist;}

    public String getNombrePropietario(){
        return nombrePropietario;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Playlist){
            Playlist c = (Playlist) o;
            if (c.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "\n---PLAYLIST---\n" +
                "Nombre: " + nombre +
                "\nId: " + id_playlist +
                "\nNombre propietario: " + nombrePropietario;
    }
}
