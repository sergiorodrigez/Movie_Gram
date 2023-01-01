package main.java.domain;

import java.io.Serializable;

public class PlaylistPelicula implements Serializable {

    public int id_playlist;
    public int id_pelicula;

    public PlaylistPelicula(int id_playlist, int id_pelicula){
        this.id_playlist = id_playlist;
        this.id_pelicula = id_pelicula;
    }

    public int getId_playlist(){return id_playlist;}

    public int getId_pelicula() {
        return id_pelicula;
    }

    @Override
    public String toString(){
        return "---PLAYLIST-PELICULA---" +
                "\nId playilst: " + id_playlist +
                "\nId pelicula:" + id_pelicula;
    }
}
