package main.java.domain;

import main.java.client.Cliente;
import main.java.dao.ConnectionDAO;
import main.java.dao.CustomerDAO;
import main.java.ui.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GestorApp {

    public static Customer USUARIO;

    private JVentana ventana;

    private Cliente cliente;

    private ArrayList<Pelicula> peliculas;

    private PnlReviews pnlReviews;
    private PnlInicio pnlInicio;
    private PnlPlayList pnlPlaylist;


    public GestorApp(JVentana ventana, Cliente cliente){
        this.ventana = ventana;
        this.cliente = cliente;
    }

    public void initPnlInicioSesion(){
        JPanel pnlCentral = new JPanel(new GridLayout(2,1));

        //pnlInicio
        pnlInicio = new PnlInicio(ventana.getCardsInicioSesion(), ventana, cliente);

        //pnlLogo
        PnlLogo pnlLogo = new PnlLogo();
        int anchoImagen = 250;
        int altoImagen = 250;
        pnlLogo.addImagen(new Imagen("src/main/resources/logo.png", JVentana.ANCHURA/2 - (anchoImagen/2),150, anchoImagen, altoImagen));

        pnlCentral.add(pnlLogo);
        pnlCentral.add(pnlInicio);

        ventana.getPnlTodo().add(pnlCentral, "inicioSesion");
    }


    public void initPnlPerfil(Customer customer){
        USUARIO = customer;

        peliculas = getPeliculas();

        //Perfil
        JPanel pnlPerfil = new JPanel(new BorderLayout());

        //pnlCentral
        JPanel pnlCentralPerfil = ventana.getPnlCentralPerfil();
        pnlCentralPerfil.setLayout(ventana.getCardsPerfil());

        //pnlCentralUsuario
        JPanel pnlCentralUsuario = new JPanel(new BorderLayout());
        pnlCentralPerfil.add(pnlCentralUsuario, "centralUsuario");

        //pnlUsuario
        PnlUsuario pnlUsuario = new PnlUsuario(ventana, new BorderLayout(), customer, ventana.getCardsMedia(), ventana.getPnlMedia());
        pnlUsuario.setBackground(JVentana.R_BACKGROUND_COLOR);

        pnlCentralUsuario.add(pnlUsuario, BorderLayout.NORTH);


        //pnlMedia
        JPanel pnlMedia = ventana.getPnlMedia();
        pnlMedia.setLayout(ventana.getCardsMedia());
        pnlCentralUsuario.add(pnlMedia);

        //pnlPlayList
        pnlPlaylist = new PnlPlayList(ventana, customer, peliculas);
        pnlPlaylist.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlMedia.add(pnlPlaylist, "playlist");

        //pnlReviews
        pnlReviews = new PnlReviews(getReviews(customer), peliculas);
        pnlReviews.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlReviews.setLayout(new BoxLayout(pnlReviews, BoxLayout.Y_AXIS));

        JScrollPane pnlScrollFeed = new JScrollPane(pnlReviews, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlScrollFeed.getVerticalScrollBar().setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlScrollFeed.getVerticalScrollBar().setUnitIncrement(20);
        pnlScrollFeed.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        pnlScrollFeed.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));
        pnlScrollFeed.setFocusable(false);
        pnlScrollFeed.setBorder(null);
        pnlMedia.add(pnlScrollFeed, "reviews");

        ventana.getCardsMedia().show(pnlMedia, "playlist");

        //pnlBusqueda
        PnlBusqueda pnlBusqueda = new PnlBusqueda(ventana, new BorderLayout());
        pnlCentralPerfil.add(pnlBusqueda, "centralBusqueda");

        //pnlNewPlaylist
        PnlnewPlaylist pnlnewPlaylist= new PnlnewPlaylist(ventana);
        pnlCentralPerfil.add(pnlnewPlaylist,"centralPlaylist");

        //pnlWest
        PnlWest pnlWest = new PnlWest(ventana);
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));

        pnlWest.add(Box.createRigidArea(new Dimension(20, 300)));
        pnlWest.initComponentes(new Dimension(20,15));


        ventana.getCardsPerfil().show(pnlCentralPerfil, "centralUsuario");

        pnlPerfil.add(pnlCentralPerfil);
        pnlPerfil.add(pnlWest, BorderLayout.WEST);


        ventana.getPnlTodo().add(pnlPerfil, "perfil");
    }

    public ArrayList<Customer> getCustomers(){
        HashMap<String, Object> session = new HashMap<>();
         return (ArrayList<Customer>) cliente.sendMessage("/getCustomers", session).get("Customers");
    }

    public ArrayList<Pelicula> getPeliculas(){
        if (peliculas==null){
            peliculas = (ArrayList<Pelicula>) cliente.sendMessage("/getPeliculas", new HashMap<String, Object>()).get("Peliculas");
        }
        return peliculas;
    }

    public ArrayList<Review> getReviews(Customer customer){
        HashMap<String, Object> session = new HashMap<>();
        session.put("Customer", customer);

        ArrayList<Review> reviews = (ArrayList<Review>) cliente.sendMessage("/getReviews", session).get("Reviews");
        return reviews;
    }

    public ArrayList<Playlist> getPlaylists(Customer customer){
        HashMap<String, Object> session = new HashMap<>();
        session.put("Customer", customer);

        ArrayList<Playlist> playlists = (ArrayList<Playlist>) cliente.sendMessage("/getPlaylist", session).get("Playlist");
        return playlists;
    }

    public PnlInicio getPnlInicio(){
        return pnlInicio;
    }

    public PnlReviews getPnlReviews(){
        return pnlReviews;
    }

    public PnlPlayList getPnlPlaylist(){
        return pnlPlaylist;
    }
}
