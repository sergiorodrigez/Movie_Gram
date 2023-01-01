package main.java.ui;

import main.java.client.Cliente;
import main.java.domain.GestorApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class JVentana extends JFrame {

    private GestorApp gestorApp;
    private Cliente cliente;
    public static final Color BACKGROUND_COLOR = new Color(51, 46, 46);
    public static final Color R_BACKGROUND_COLOR = new Color(72, 64, 64);

    public static final Color LBL_COLOR = new Color(190, 176, 176);
    public static final Color BTN_COLOR = new Color(43, 131, 120);
    public static final String FONT_LBL = "Lato";
    public static final int LBL_SIZE = 15;

    private CardLayout cardsPerfil;
    private CardLayout cards;
    private CardLayout cardsMedia;
    private CardLayout cardsInicioSesion;

    private JPanel pnlTodo;
    private JPanel pnlCentralPerfil;
    private JPanel pnlMedia;

    public static final int ANCHURA = 900;
    public static final int ALTURA = 1000;

    public JVentana(){
        super("MovieGram");


        try {
            setIconImage(ImageIO.read(new File("src/main/resources/logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cliente = new Cliente();
        gestorApp = new GestorApp(this, cliente);

        //Inicializamos atributos
        cards = new CardLayout();
        cardsInicioSesion = new CardLayout();
        cardsPerfil = new CardLayout();
        cardsMedia = new CardLayout();

        pnlCentralPerfil = new JPanel();
        pnlMedia = new JPanel();

        pnlTodo = new JPanel(cards);

        gestorApp.initPnlInicioSesion();

        cards.show(pnlTodo, "inicioSesion");

        //JFrame
        add(pnlTodo);

        setSize(ANCHURA,ALTURA);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Cliente getCliente(){
        return cliente;
    }

    public GestorApp getGestorApp(){
        return gestorApp;
    }

    public JPanel getPnlTodo(){
        return pnlTodo;
    }

    public JPanel getPnlMedia(){
        return pnlMedia;
    }

    public CardLayout getCardsInicioSesion(){
        return cardsInicioSesion;
    }

    public CardLayout getCards(){
        return cards;
    }

    public CardLayout getCardsPerfil(){
        return cardsPerfil;
    }

    public CardLayout getCardsMedia(){
        return cardsMedia;
    }

    public JPanel getPnlCentralPerfil(){
        return pnlCentralPerfil;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new JVentana();

    }
}
