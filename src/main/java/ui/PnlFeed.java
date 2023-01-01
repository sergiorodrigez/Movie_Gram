package main.java.ui;

import main.java.domain.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PnlFeed extends JPanel {

    private ArrayList<Customer> customers;
    private ArrayList<Pelicula> peliculas;

    private JPanel pnlPadre;
    private JVentana ventana;

    public PnlFeed(JVentana ventana, JPanel pnlPadre, ArrayList<Customer> customers, ArrayList<Pelicula> peliculas){
        super();

        this.pnlPadre = pnlPadre;
        this.customers = customers;
        this.peliculas = peliculas;
        this.ventana = ventana;

        setBackground(JVentana.R_BACKGROUND_COLOR);
        showFeed();
    }


    public void showFeed(){
        customers.forEach(c -> {
            JPanel pnlCliente = new JPanel(new BorderLayout());
            pnlCliente.setBackground(JVentana.R_BACKGROUND_COLOR);

            Boton btnNombre = new Boton(c.getNombre(), Color.WHITE, new ImageIcon(c.getImagen().getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH)));
            btnNombre.addHoverEffect();
            btnNombre.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
            btnNombre.delBackground();
            btnNombre.addActionListener(e -> {
                JPanel pnlNuevoUser = new JPanel(new BorderLayout());

                CardLayout cardsMediaPerfilBuscado = new CardLayout();
                JPanel pnlMedia = new JPanel(cardsMediaPerfilBuscado);


                //Usuario
                PnlUsuario pnlUsuario = new PnlUsuario(ventana, new BorderLayout(), c, cardsMediaPerfilBuscado, pnlMedia);
                pnlNuevoUser.add(pnlUsuario, BorderLayout.NORTH);

                pnlMedia.setBackground(JVentana.R_BACKGROUND_COLOR);
                pnlNuevoUser.add(pnlMedia);

                //pnlReviews
                JPanel pnlReviews = new PnlReviews(ventana.getGestorApp().getReviews(c), ventana.getGestorApp().getPeliculas());
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

                //pnlPlaylist
                PnlPlayList pnlPlaylist = new PnlPlayList(ventana, c, ventana.getGestorApp().getPeliculas());
                pnlPlaylist.setBackground(JVentana.R_BACKGROUND_COLOR);
                pnlMedia.add(pnlPlaylist, "playlist");

                cardsMediaPerfilBuscado.show(pnlMedia, "playlist");


                ventana.getPnlCentralPerfil().add(pnlNuevoUser, "perfilBuscado");

                ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(), "perfilBuscado");
            });

            pnlCliente.add(btnNombre, BorderLayout.WEST);

            Boton btnOpciones = new Boton("", Color.WHITE, new ImageIcon("src/main/resources/addMovie.png"));
            btnOpciones.delBackground();
            btnOpciones.addHoverEffect();

            pnlCliente.add(btnOpciones, BorderLayout.EAST);

            add(pnlCliente);
        });
        peliculas.forEach(p -> {
            JPanel pnlPelicula = new JPanel(new BorderLayout());
            pnlPelicula.setBackground(JVentana.R_BACKGROUND_COLOR);

            Boton btnOpciones = new Boton("", Color.WHITE, new ImageIcon("src/main/resources/addMovie.png"));
            btnOpciones.delBackground();
            btnOpciones.addHoverEffect();
            btnOpciones.addActionListener(e -> {
                Object[] options = {"Agregar a Movielist", "Valorar", "Comprar entrada"};
                Integer opcionElegida = JOptionPane.showOptionDialog(pnlPadre, "Selecciona una opción", "OPCIONES", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (opcionElegida != null) {
                    if (opcionElegida == JOptionPane.YES_OPTION) {
                        ArrayList<String> nombresArray = new ArrayList<>();
                        ventana.getGestorApp().getPlaylists(GestorApp.USUARIO).forEach(playlist -> nombresArray.add(playlist.getNombre()));

                        String[] nombres = nombresArray.toArray(new String[0]);
                        Object playlistElegida = JOptionPane.showInputDialog(pnlPadre, "Selecciona una opción", "OPCIONES", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

                        for (int i = 0; i < nombres.length; i++){
                            if(nombres[i] == playlistElegida){

                                HashMap<String, Object> session = new HashMap<>();
                                session.put("TextoPlaylist", playlistElegida);

                                ArrayList<Playlist> encontrada = (ArrayList<Playlist>) ventana.getCliente().sendMessage("/searchPlaylist", session).get("PlaylistFound");
                                PlaylistPelicula playlistPelicula = new PlaylistPelicula(encontrada.get(0).getId_playlist(), p.getId_p());

                                session.put("PlaylistPelicula", playlistPelicula);
                                ventana.getCliente().sendMessage("/addPlaylistPelicula", session);
                            }
                        }

                    } else if(opcionElegida == JOptionPane.NO_OPTION) {
                        PnlCreateReview create = new PnlCreateReview(p, ventana);
                    } else if(opcionElegida == JOptionPane.CANCEL_OPTION){
                        PnlComprar pnlComprarEntrada = new PnlComprar(ventana);
                        ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(),"CompraEntradas");
                    }
                }
            });

            pnlPelicula.add(btnOpciones, BorderLayout.EAST);

            JLabel lblNombre = new JLabel(p.getNombre());
            lblNombre.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
            lblNombre.setForeground(Color.WHITE);
            try {
                lblNombre.setIcon(new ImageIcon(new URL(p.getUrl())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            pnlPelicula.add(lblNombre);

            add(pnlPelicula);
        });
    }
}
