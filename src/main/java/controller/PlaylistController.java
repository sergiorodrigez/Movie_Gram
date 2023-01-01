package main.java.controller;

import main.java.dao.PlaylistDAO;
import main.java.domain.Customer;
import main.java.domain.Playlist;
import main.java.domain.PlaylistPelicula;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistController {

    public void getPlaylists(ArrayList<Playlist> playlists, Customer customer){
        PlaylistDAO.getPlaylists(playlists, customer);
    }

    public void getAllPlaylists(ArrayList<Playlist> playlists){
        PlaylistDAO.getAllPlaylists(playlists);
    }

    public void getIDPlaylists(ArrayList<Integer> idPlaylists){
        PlaylistDAO.getIDPlaylists(idPlaylists);
    }

    public void modificarImagen(Playlist playlist, File file){
        PlaylistDAO.modificarImagen(playlist, file);
    }

    public void modificarNombre(Playlist playlist, String nuevoNombre){
        PlaylistDAO.modificarNombre(playlist, nuevoNombre);
    }

    public void getPlaylistPeliculas(ArrayList<PlaylistPelicula> playlistPeliculas){
        PlaylistDAO.getPlaylistPeliculas(playlistPeliculas);
    }

    public ArrayList<Integer> getIDPeliculasPlaylist(Playlist playlist, ArrayList<PlaylistPelicula> playlistPeliculas){
        ArrayList<Integer> idPeliculasPlaylist = new ArrayList<>();
        playlistPeliculas.forEach(p -> {
            if(playlist.getId_playlist() == p.getId_playlist()){
                idPeliculasPlaylist.add(p.getId_pelicula());
            }
        });
        return idPeliculasPlaylist;
    }

    public void searchPlaylist(ArrayList<Playlist> playlistsFound, String texto) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        getAllPlaylists(playlists);

        playlists.forEach(p -> {
            if (p.getNombre().equalsIgnoreCase(texto)) {
                playlistsFound.add(p);
            }
        });
    }

    public void addPlaylist(Customer customer, Playlist playlist, HashMap<String, Object> session, File file){
        ArrayList<Playlist> playlistsCustomer = new ArrayList<>();
        getPlaylists(playlistsCustomer, customer);

        ArrayList<String> nombrePlaylist = new ArrayList<>();
        playlistsCustomer.forEach(pc -> nombrePlaylist.add(pc.getNombre()));
        if (nombrePlaylist.contains(playlist.getNombre())){
            session.put("PlaylistExistente", true);
        } else {
            session.put("PlaylistExistente", false);
            PlaylistDAO.addPlaylist(playlist, file);
        }
    }

    public void addPlaylistPelicula(PlaylistPelicula playlistPelicula, HashMap<String, Object> session){
        ArrayList<PlaylistPelicula> playlistPeliculas = new ArrayList<>();
        getPlaylistPeliculas(playlistPeliculas);
        PlaylistDAO.addPlaylistPelicula(playlistPelicula);
    }

    public int getNewPlaylistId(){
        ArrayList<Integer> idPlaylists = new ArrayList<>();
        getIDPlaylists(idPlaylists);

        int newId = 1;
        while (idPlaylists.contains(newId)){
            newId ++;
        }
        return newId;
    }

    public void borrarPlaylist(Playlist playlist){
        PlaylistDAO.borrarPlaylist(playlist);
    }
}
