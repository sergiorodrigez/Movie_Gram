package main.java.ui;

import main.java.controller.PlaylistController;
import main.java.domain.GestorApp;
import main.java.domain.Pelicula;
import main.java.domain.Playlist;
import main.java.domain.PlaylistPelicula;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PanelInternoPlaylist extends JPanel {

    private JPanel pnlPeliculas;
    private JPanel pnlBucasPeliculas;

    private Playlist playlist;
    private ArrayList<Integer> idPeliculas;
    private ArrayList<Pelicula> peliculas;

    private GridBagConstraints gbc = new GridBagConstraints();

    private JVentana ventana;

    private static final Color LBL_COLOR = new Color(183, 179, 179);
    private static final Color BCK_COLOR = new Color(31, 26, 26);

    public PanelInternoPlaylist(JVentana ventana, LayoutManager layoutManager, Playlist playlist, ArrayList<Pelicula> peliculas){ //hay que meter si o si las playlist aqui y como las pillas
        super(layoutManager);

        this.playlist=playlist;
        this.ventana = ventana;

        //Saco playlistpeliculas
        HashMap<String, Object> session = ventana.getCliente().sendMessage("/getPlaylistPeliculas", new HashMap<String, Object>());
        ArrayList<PlaylistPelicula> playlistPeliculas = (ArrayList<PlaylistPelicula>) session.get("PlaylistPeliculas");

        //saco idPeliculas de la playlist en cuestion
        PlaylistController playlistController = new PlaylistController();
        idPeliculas = playlistController.getIDPeliculasPlaylist(playlist, playlistPeliculas);

        this.peliculas = peliculas;

        ventana.getPnlCentralPerfil().add(this, "Interno");
        initPnlInternoPeliculas();
        initPnlPeliculas();
        initBuscarPeliculas();

    }

    public void initPnlInternoPeliculas(){
        JPanel pnlInfo = new JPanel(new BorderLayout());
        pnlInfo.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlInfo.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));

        JLabel lblIcon = new JLabel(new ImageIcon(playlist.getImagen().getImage().getScaledInstance(180,180, Image.SCALE_SMOOTH)));
        lblIcon.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        //pnlConfiguracion
        JPanel pnlConfiguracion = new JPanel(new GridBagLayout());
        pnlConfiguracion.setBackground(JVentana.R_BACKGROUND_COLOR);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setGbc(2,0,2,1);
        JLabel lblPlaylist = new JLabel(playlist.getNombre());
        //lblPlaylist.setBorder(BorderFactory.createEmptyBorder(0,55,0,0));
        lblPlaylist.setForeground(LBL_COLOR);
        lblPlaylist.setFont(new Font(JVentana.FONT_LBL, Font.BOLD, JVentana.LBL_SIZE));
        pnlConfiguracion.add(lblPlaylist, gbc);

        setGbc(0,1,4,1);
        Boton btnConfiguracion = new Boton("Configuracion", Color.WHITE, new ImageIcon("src/main/resources/addMovie.png"));
        btnConfiguracion.setBackground(new Color(51, 46, 46));
        btnConfiguracion.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnConfiguracion.setOpaque(true);
        btnConfiguracion.addHoverEffect();
        btnConfiguracion.setIconTextGap(20);
        btnConfiguracion.setFocusable(false);
        btnConfiguracion.setBorderPainted(false);

        btnConfiguracion.addActionListener(e -> {
            String options[] = {"Cambiar foto de perfil", "Cambiar nombre", "Borrar Movielist"};
            Integer opcionElegida = JOptionPane.showOptionDialog(ventana.getPnlCentralPerfil(), "¿Qué quieres hacer?", "Selecciona una opción", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (opcionElegida == JOptionPane.YES_OPTION) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Elige una foto");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setFileFilter(new FileNameExtensionFilter("*.png", "png"));

                int selecion = jfc.showOpenDialog(ventana);
                if (selecion == JFileChooser.APPROVE_OPTION){
                    //Guardamos imagen en base de datos
                    String absolutePath = jfc.getSelectedFile().getAbsolutePath();

                    Image image = null;
                    try {
                        image = ImageIO.read(new File(absolutePath));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    lblIcon.setIcon(new ImageIcon(image.getScaledInstance(180,180,Image.SCALE_SMOOTH)));
                    playlist.setImagen(new ImageIcon(image));

                    HashMap<String, Object> session = new HashMap<>();
                    session.put("Playlist", playlist);
                    session.put("Path", absolutePath);
                    ventana.getCliente().sendMessage("/modificarImagenPlaylist", session);

                    ventana.getGestorApp().getPnlPlaylist().removeAll();
                    ventana.getGestorApp().getPnlPlaylist().initPnls();
                }


            }else if(opcionElegida == JOptionPane.NO_OPTION){
                String nombreNuevo = JOptionPane.showInputDialog(ventana, "Introduce un nombre", "Nombre:", JOptionPane.QUESTION_MESSAGE);
                if (!(nombreNuevo == null)) {


                    HashMap<String, Object> session = new HashMap<>();
                    session.put("Playlist", playlist);
                    session.put("NuevoNombre", nombreNuevo);
                    ventana.getCliente().sendMessage("/modificarNombrePlaylist", session);

                    playlist.setNombre(nombreNuevo);
                    lblPlaylist.setText(nombreNuevo);

                    ventana.getGestorApp().getPnlPlaylist().removeAll();
                    ventana.getGestorApp().getPnlPlaylist().initPnls();
                }
            } else if (opcionElegida == JOptionPane.CANCEL_OPTION){
                HashMap<String, Object> session = new HashMap<>();
                session.put("Playlist", playlist);
                ventana.getCliente().sendMessage("/borrarPlaylist", session);

                ventana.getGestorApp().getPnlPlaylist().removeAll();
                ventana.getGestorApp().getPnlPlaylist().initPnls();
                ventana.getCardsPerfil().show(ventana.getPnlCentralPerfil(),"centralUsuario");
            }
        });

        if (playlist.getNombrePropietario().equals(GestorApp.USUARIO.getNombre())) {
            pnlConfiguracion.add(btnConfiguracion, gbc);
        }


        pnlInfo.add(lblIcon, BorderLayout.WEST);
        pnlInfo.add(pnlConfiguracion);

        add(pnlInfo, BorderLayout.NORTH);
    }


    public void initPnlPeliculas(){
        pnlPeliculas= new JPanel();
        pnlPeliculas.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlPeliculas.setLayout(new BoxLayout(pnlPeliculas, BoxLayout.Y_AXIS)); //Añado más margen por arriba
        //BorderFactory.createEmptyBorder(70, 0, 0, 0)
        showPeliculas();

        //add(pnlPeliculas,BorderLayout.CENTER);

        JScrollPane scrollMovies = new JScrollPane(pnlPeliculas, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollMovies.getVerticalScrollBar().setBackground(JVentana.R_BACKGROUND_COLOR);
        scrollMovies.getVerticalScrollBar().setUnitIncrement(20);
        scrollMovies.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollMovies.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));
        scrollMovies.setFocusable(false);
        scrollMovies.setBorder(null);

        add(scrollMovies,BorderLayout.CENTER);
    }

    public void showPeliculas(){
        idPeliculas.forEach(id ->{
            peliculas.forEach(p -> {
                if (id == p.getId_p()){
                    JPanel pnlPelicula = new JPanel(new BorderLayout());
                    JLabel lblNombre = new JLabel(p.getNombre());
                    lblNombre.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
                    lblNombre.setForeground(Color.WHITE);
                    try {
                        lblNombre.setIcon(new ImageIcon(new URL(p.getUrl())));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    pnlPelicula.setBackground(JVentana.R_BACKGROUND_COLOR);
                    pnlPelicula.add(lblNombre, BorderLayout.WEST);

                    pnlPeliculas.add(pnlPelicula);
                }
            });
        });
    }

    public void initBuscarPeliculas(){
        pnlBucasPeliculas = new JPanel();
        pnlBucasPeliculas.setBackground(JVentana.R_BACKGROUND_COLOR);


        add(pnlBucasPeliculas,BorderLayout.SOUTH);

    }


    private void setGbc(int gridX, int gridY, int gridWidth, int gridHeight){
        gbc.gridheight = gridHeight;
        gbc.gridwidth = gridWidth;
        gbc.gridx = gridX;
        gbc.gridy = gridY;
    }
}
