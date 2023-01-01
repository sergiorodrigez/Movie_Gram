package main.java.ui;

import main.java.domain.Customer;
import main.java.domain.Pelicula;
import main.java.domain.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PnlPlayList extends JPanel {

    private JVentana ventana;
    private JScrollPane scrollMovieList;

    private Customer customer;

    private ArrayList<Pelicula> peliculas;


    public PnlPlayList(JVentana ventana, Customer customer, ArrayList<Pelicula> peliculas){

        this.ventana = ventana;
        this.customer = customer;
        this.peliculas = peliculas;

        setLayout(new BorderLayout());
        setBackground(JVentana.R_BACKGROUND_COLOR);

        initPnls();
    }

    public void initPnls(){

        JPanel panelMovieList= new JPanel(new GridLayout(0,3));
        panelMovieList.setBackground(JVentana.R_BACKGROUND_COLOR);
        panelMovieList.setBorder(BorderFactory.createEmptyBorder(20,5,0,5));

        ventana.getGestorApp().getPlaylists(customer).forEach(p->{
            JPanel pnlPelis = initPnlPlaylist(p);

            panelMovieList.add(pnlPelis);
        });
        scrollMovieList = new JScrollPane(panelMovieList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollMovieList.getVerticalScrollBar().setBackground(JVentana.R_BACKGROUND_COLOR);
        scrollMovieList.getVerticalScrollBar().setUnitIncrement(20);
        scrollMovieList.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollMovieList.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));

        scrollMovieList.setFocusable(false);
        scrollMovieList.setBorder(null);


        add(scrollMovieList,BorderLayout.CENTER);
    }

    public JPanel initPnlPlaylist(Playlist playlist){
        JPanel pnlPlaylist = new JPanel();
        pnlPlaylist.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlPlaylist.setLayout(new BoxLayout(pnlPlaylist, BoxLayout.Y_AXIS));

        Boton btnPlaylist = new Boton("", Color.WHITE, new ImageIcon(playlist.getImagen().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

        btnPlaylist.delBackground();
        btnPlaylist.addHoverEffect();
        btnPlaylist.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        btnPlaylist.addActionListener(e -> {
            PanelInternoPlaylist pnlInternoPlaylist = new PanelInternoPlaylist(ventana, new BorderLayout(), playlist, peliculas);
            ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(),"Interno");
        });

        JLabel lblNombrePlaylist = new JLabel(playlist.getNombre());
        lblNombrePlaylist.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, 13));
        lblNombrePlaylist.setForeground(Color.WHITE);
        lblNombrePlaylist.setBorder(BorderFactory.createEmptyBorder(10, 100-10, 0, 25));


        pnlPlaylist.add(btnPlaylist);
        pnlPlaylist.add(lblNombrePlaylist);

        return pnlPlaylist;
    }
}

