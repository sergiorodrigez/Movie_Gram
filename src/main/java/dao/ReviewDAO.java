package main.java.dao;

import main.java.domain.Customer;
import main.java.domain.Playlist;
import main.java.domain.Review;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO {

    private static final Connection con = ConnectionDAO.getInstance().getConnection();

    public static void getReviews(ArrayList<Review> reviews, Customer customer){
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM reviews WHERE id_user LIKE '" + customer.getNombre() + "'");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                reviews.add(new Review(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addReview(Review review){
        String query = "INSERT INTO reviews (id_user, id_pelicula, contenido, valoracion) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)){

            pst.setString(1, review.getUser());
            pst.setInt(2, review.getId_p());
            pst.setString(3, review.getComent());
            pst.setInt(4, review.getVal());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
