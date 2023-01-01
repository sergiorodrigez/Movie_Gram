package test;

import main.java.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.io.File;

import static org.junit.Assert.*;

public class CustomerTest {

    private Customer customer;
    private Customer customerVerdadero;
    private Customer customerPrueba;

    private ImageIcon imageIcon;

    @Before
    public void setUp() throws Exception {
        imageIcon = new ImageIcon("src/main/resources/logo.png");

        customer = new Customer("miguel", "Miguel", "miguel@gmail.com", imageIcon);
        customerVerdadero = new Customer("miguel", "Miguel", "miguel1@gmail.com", imageIcon);
        customerPrueba = new Customer("miguel", "miguel", "miguel@gmail.com", imageIcon);
    }

    @Test
    public void getImagen() {
        ImageIcon image = customerPrueba.getImagen();
        assertEquals(imageIcon, image);
    }

    @Test
    public void setImagen() {
        ImageIcon image = new ImageIcon("src/main/resources/music.png");
        customerPrueba.setImagen(image);

        assertEquals(image, customerPrueba.getImagen());
    }

    @Test
    public void setNombre() {
        String nombre = "Miguel";
        customerPrueba.setNombre(nombre);

        assertEquals(nombre, customerPrueba.getNombre());
    }

    @Test
    public void getNombre() {
        assertEquals("miguel", customer.getNombre());
    }

    @Test
    public void getContra() {
        assertEquals("Miguel", customer.getContra());
    }

    @Test
    public void getCorreo() {
        assertEquals("miguel@gmail.com", customer.getCorreo());
    }

    @Test
    public void testEquals() {
        boolean equals = true;
        assertEquals(equals, customer.equals(customerVerdadero));
    }
}