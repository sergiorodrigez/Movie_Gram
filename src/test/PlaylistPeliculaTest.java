package test;

import main.java.domain.PlaylistPelicula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaylistPeliculaTest {

    private PlaylistPelicula playlistPelicula;

    @Before
    public void setUp() throws Exception {
        playlistPelicula = new PlaylistPelicula(1, 180);
    }

    @Test
    public void getId_playlist() {
        int id_play = playlistPelicula.getId_playlist();
        assertEquals(1, id_play);
    }

    @Test
    public void getId_pelicula() {
        int id_peli = playlistPelicula.getId_pelicula();
        assertEquals(180, id_peli);
    }
}