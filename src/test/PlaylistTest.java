package test;

import junit.framework.TestCase;
import main.java.domain.Playlist;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.*;

public class PlaylistTest {
    private ImageIcon imageIcon;
    private Playlist playlist;

    private Playlist playlistVerdadera;
    private Playlist playlistPrueba;

    @Before
    public void setUp() throws Exception {
        imageIcon= new ImageIcon("src/main/resources/playlistIcon.png");


        playlist = new Playlist(1, "Favoritas", "sergio", imageIcon);
        playlistVerdadera= new Playlist(1, "Favoritas", "sergio", imageIcon);
        playlistPrueba = new Playlist(1, "Favoritas", "sergio", imageIcon);
    }

    @Test
    public void getImagen() {
        ImageIcon image= playlistPrueba.getImagen();
        assertEquals(imageIcon,image);
    }

    @Test
    public void setImagen() {
        ImageIcon image = new ImageIcon("src/main/resources/logo.png");
        playlistPrueba.setImagen(image);

        assertEquals(image,playlistPrueba.getImagen());
    }

    @Test
    public void setNombre() {
        String nombre= "Favoritas";
        playlistPrueba.setNombre(nombre);

        assertEquals(nombre,playlistPrueba.getNombre());
    }

    @Test
    public void getNombre() {
        assertEquals("Favoritas",playlist.getNombre());
    }

    @Test
    public void getId_playlist() {
        assertEquals(1, playlist.getId_playlist());
    }

    @Test
    public void getNombrePropietario() {
        assertEquals("sergio",playlist.getNombrePropietario());
    }

    @Test
    public void testEquals() {
        boolean equals= true;
        assertEquals(equals, playlist.equals(playlistVerdadera));
    }

}