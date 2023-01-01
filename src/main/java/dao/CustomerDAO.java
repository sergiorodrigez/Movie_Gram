package main.java.dao;

import main.java.domain.Customer;

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

public class CustomerDAO {

	private static final Connection con = ConnectionDAO.getInstance().getConnection();


	public static void getClientes(ArrayList<Customer> lista) {
		try (PreparedStatement pst = con.prepareStatement("SELECT * FROM usuarios");
			 ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				InputStream inputStream = rs.getBinaryStream(4);
				Image image = ImageIO.read(inputStream);

				lista.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), new ImageIcon(image)));
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Customer getCustomer(Customer customer) {
		Customer cu = null;
		try (PreparedStatement pst = con.prepareStatement("SELECT * FROM usuarios WHERE nombre='"+customer.getNombre()+"'");
			 ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				InputStream inputStream = rs.getBinaryStream(4);
				Image image = ImageIO.read(inputStream);

				cu =  new Customer(rs.getString(1), rs.getString(2), rs.getString(3), new ImageIcon(image));
			}

		} catch (SQLException ex) {

			System.out.println(ex.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cu;
	}

	public static String getContra(String correo){
		String contra = "";
		try (PreparedStatement pst = con.prepareStatement("SELECT contra FROM usuarios WHERE correo='" + correo +"'");
			 ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				contra = rs.getString(1);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return contra;
	}

	public static void addCustomer(Customer customer, File file){
		String query = "INSERT INTO usuarios (nombre, contra, correo, image) VALUES(?, ?, ?, ?)";
		try (PreparedStatement pst = con.prepareStatement(query);
			 FileInputStream fileInputStream = new FileInputStream(file)){

			pst.setString(1, customer.getNombre());
			pst.setString(2, customer.getContra());
			pst.setString(3, customer.getCorreo());
			pst.setBinaryStream(4, fileInputStream, (int) file.length());

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void modificarImagen(Customer customer, File file){
		String query = "UPDATE usuarios SET image = ? WHERE nombre = ?";
		try (PreparedStatement pst = con.prepareStatement(query);
			 FileInputStream fileInputStream = new FileInputStream(file)){

			pst.setBinaryStream(1, fileInputStream, (int) file.length());
			pst.setString(2, customer.getNombre());

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void modificarNombre(Customer customer, String nuevoNombre){
		String query = "UPDATE usuarios SET nombre = ? WHERE nombre = ?";
		try (PreparedStatement pst = con.prepareStatement(query)){

			pst.setString(1, nuevoNombre);
			pst.setString(2, customer.getNombre());

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
