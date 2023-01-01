package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class PnlWest extends JPanel {

    private Boton btnInicio;
    private Boton btnExplorar;
    private Boton btnMensajes;
    private Boton btnCrearPlaylist;
    private Boton btnCerrarSesion;

    private JVentana ventana;

    public PnlWest(JVentana ventana){
        super();

        this.ventana = ventana;

        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25)); //Añado más márgen por la derecha
        setBackground(JVentana.BACKGROUND_COLOR);
    }

    public void initComponentes(Dimension separacion){
        btnInicio = new Boton("Inicio", JVentana.LBL_COLOR, new Color(239, 221, 221), new ImageIcon("src/main/resources/homeIcon.png"), new ImageIcon("src/main/resources/homeIconHover.png"));
        btnExplorar = new Boton("Explorar", JVentana.LBL_COLOR, new Color(239, 221, 221), new ImageIcon("src/main/resources/searchIcon.png"), new ImageIcon("src/main/resources/searchIconHover.png"));
        btnCrearPlaylist = new Boton("Añadir movielist", JVentana.LBL_COLOR, new Color(239, 221, 221), new ImageIcon("src/main/resources/createIcon.png"), new ImageIcon("src/main/resources/createIconHover.png"));
        btnCerrarSesion = new Boton("Cerrar sesion", JVentana.LBL_COLOR, new Color(239,221,221), new ImageIcon("src/main/resources/logOut.png"), new ImageIcon("src/main/resources/logOutHover.png"));

        btnInicio.delBackground();
        btnInicio.addHoverEffect();
        btnInicio.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnInicio.addActionListener(e -> {
            ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(), "centralUsuario");
        });
        add(btnInicio);

        add(Box.createRigidArea(separacion));
        btnExplorar.delBackground();
        btnExplorar.addHoverEffect();
        btnExplorar.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnExplorar.addActionListener(e -> {
            ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(), "centralBusqueda");
        });
        add(btnExplorar);

        add(Box.createRigidArea(separacion));
        btnCrearPlaylist.delBackground();
        btnCrearPlaylist.addHoverEffect();
        btnCrearPlaylist.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnCrearPlaylist.addActionListener(e -> {
            ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(), "centralPlaylist");
        });
        add(btnCrearPlaylist);

        add(Box.createRigidArea(separacion));
        btnCerrarSesion.delBackground();
        btnCerrarSesion.addHoverEffect();
        btnCerrarSesion.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnCerrarSesion.addActionListener(e -> {
            ventana.dispose();
            new JVentana();
        });
        add(btnCerrarSesion);
    }
}

