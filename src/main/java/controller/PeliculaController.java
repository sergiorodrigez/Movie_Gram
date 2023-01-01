package main.java.controller;

import main.java.dao.CustomerDAO;
import main.java.dao.PeliculaDAO;
import main.java.domain.Customer;
import main.java.domain.Pelicula;

import java.util.ArrayList;

public class PeliculaController {

    public void getPeliculas(ArrayList<Pelicula> peliculas){
        PeliculaDAO.getPeliculas(peliculas);
    }

    public void searchPelicula(ArrayList<Pelicula> peliculasFound, String texto) {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        getPeliculas(peliculas);

        peliculas.forEach(p -> {
            if (p.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                peliculasFound.add(p);
            }
        });
    }
}
