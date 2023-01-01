package main.java.dao;

import main.java.domain.Customer;
import main.java.domain.Playlist;
import main.java.domain.PlaylistPelicula;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {

    private static final Connection con = ConnectionDAO.getInstance().getConnection();

    public static void getPlaylists(ArrayList<Playlist> playlists, Customer customer){
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM playlist WHERE id_user = '" + customer.getNombre() + "'");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                InputStream inputStream = rs.getBinaryStream(4);
                Image image = ImageIO.read(inputStream);

                playlists.add(new Playlist(rs.getInt(1), rs.getString(2), rs.getString(3), new ImageIcon(image)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAllPlaylists(ArrayList<Playlist> playlists){
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM playlist");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                InputStream inputStream = rs.getBinaryStream(4);
                Image image = ImageIO.read(inputStream);

                playlists.add(new Playlist(rs.getInt(1), rs.getString(2), rs.getString(3), new ImageIcon(image)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void borrarPlaylist(Playlist playlist){
        try (PreparedStatement pst = con.prepareStatement("DELETE FROM playlist WHERE id_playlist = " + playlist.getId_playlist());
             ResultSet rs = pst.executeQuery()) {

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //Borramos peliculas asociadas (si las hay).
        try (PreparedStatement pst = con.prepareStatement("DELETE FROM playlist_peliculas WHERE id_playlist = " + playlist.getId_playlist());
             ResultSet rs = pst.executeQuery()) {

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addPlaylist(Playlist playlist, File file){
        String query = "INSERT INTO playlist (id_playlist, nombre, id_user, image) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query);
             FileInputStream fileInputStream = new FileInputStream(file)){

            pst.setInt(1, playlist.getId_playlist());
            pst.setString(2, playlist.getNombre());
            pst.setString(3, playlist.getNombrePropietario());
            pst.setBinaryStream(4, fileInputStream, (int) file.length());

            pst.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPlaylistPelicula(PlaylistPelicula playlistPelicula){
        String query = "INSERT INTO playlist_peliculas (id_playlist, id_pelicula) VALUES(?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)){

            pst.setInt(1, playlistPelicula.getId_playlist());
            pst.setInt(2, playlistPelicula.getId_pelicula());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getIDPlaylists(ArrayList<Integer> idPlaylist){
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM playlist");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                idPlaylist.add(rs.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void modificarImagen(Playlist playlist, File file){
        String query = "UPDATE playlist SET image = ? WHERE nombre = ? AND id_user = ?";
        try (PreparedStatement pst = con.prepareStatement(query);
             FileInputStream fileInputStream = new FileInputStream(file)){

            pst.setBinaryStream(1, fileInputStream, (int) file.length());
            pst.setString(2, playlist.getNombre());
            pst.setString(3, playlist.getNombrePropietario());

            pst.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void modificarNombre(Playlist playlist, String nuevoNombre){
        String query = "UPDATE playlist SET nombre = ? WHERE nombre = ?";
        try (PreparedStatement pst = con.prepareStatement(query)){

            pst.setString(1, nuevoNombre);
            pst.setString(2, playlist.getNombre());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getPlaylistPeliculas(ArrayList<PlaylistPelicula> playlistPeliculas){
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM playlist_peliculas");   //WHERE id_user = '" + JVentana.user + "'
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                playlistPeliculas.add(new PlaylistPelicula(rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
