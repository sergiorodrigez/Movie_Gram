package main.java.domain;

import javax.swing.*;
import java.io.Serializable;

public class Customer implements Serializable {

	private String nombre;
	private String correo;
	private String contra;
	private ImageIcon imagen;


	public Customer(String nombre, String contra, String correo, ImageIcon imagen){
		this.nombre = nombre;
		this.correo = correo;
		this.contra = contra;
		this.imagen = imagen;
	}

	//Unicamente para comparar
	public Customer(String nombre, String contra){
		this(nombre, contra, null, null);
	}

	//Para buscar o borrar
	public Customer(String nombre){
		this(nombre, null);
	}

	public ImageIcon getImagen(){
		return imagen;
	}


	public void setImagen(ImageIcon inputStream){
		imagen = inputStream;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	public String getNombre(){
		return nombre;
	}

	public String getContra(){
		return contra;
	}

	public String getCorreo(){
		return correo;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Customer){
			Customer c = (Customer) o;
			if (c.getNombre().equals(nombre) && c.getContra().equals(contra)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString(){
		return "\n---CUSTOMER---\nNombre: " + nombre +
				"\nContraseña: " + contra +
				"\nCorreo: " + correo + "\nImagen: " + imagen;
	}
}
