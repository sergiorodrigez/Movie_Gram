package main.java.dao;

import main.java.domain.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeliculaDAO {

    private static final Connection con = ConnectionDAO.getInstance().getConnection();

    public static void getPeliculas(ArrayList<Pelicula> peliculas){
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM peliculas");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                peliculas.add(new Pelicula(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(5)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
