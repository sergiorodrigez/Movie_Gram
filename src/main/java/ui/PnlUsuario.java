package main.java.ui;

import main.java.domain.Customer;
import main.java.domain.GestorApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class PnlUsuario extends JPanel {

    private Customer customer;

    private JPanel pnlImagen;
    private JPanel pnlInfo;
    private JPanel pnlOpciones;

    private JPanel pnlPadre;
    private CardLayout cardLayout;

    private JLabel lblImagen;

    private JVentana ventana;

    private static final Color LBL_COLOR = new Color(183, 179, 179);
    private static final Color BCK_COLOR = new Color(31, 26, 26);

    public PnlUsuario(JVentana ventana, LayoutManager layoutManager, Customer customer, CardLayout cardLayout, JPanel pnlPadre){
        super(layoutManager);

        this.ventana = ventana;
        this.customer = customer;
        this.pnlPadre = pnlPadre;
        this.cardLayout = cardLayout;

        initPnlInfo();
        initPnlOpciones();
        initPnlImagen();
    }

    public void initPnlImagen(){
        pnlImagen = new JPanel();

        lblImagen = new JLabel();
        lblImagen.setIcon(new ImageIcon((customer.getImagen()).getImage().getScaledInstance(162, 162, Image.SCALE_SMOOTH)));

        pnlImagen.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlImagen.add(lblImagen);
        pnlImagen.setBorder(BorderFactory.createEmptyBorder(25, 25, 0, 0)); //Añado más márgen por la derecha e izquierda


        add(pnlImagen, BorderLayout.WEST);
    }

    public void initPnlInfo(){
        pnlInfo = new JPanel();

        pnlInfo.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0)); //Añado más margen por arriba

        JLabel lblUsuario = new JLabel("@" + customer.getNombre());
        lblUsuario.setForeground(LBL_COLOR);
        lblUsuario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, 15));

        Boton btnConfiguracion = new Boton("Configuracion", LBL_COLOR, new ImageIcon("src/main/resources/gearIcon.png"));
        btnConfiguracion.delBackground();
        btnConfiguracion.addHoverEffect();
        btnConfiguracion.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnConfiguracion.addActionListener(e -> {
            String options[] = {"Cambiar foto de perfil", "Cambiar nombre"};
            int opcionElegida = JOptionPane.showOptionDialog(ventana.getPnlCentralPerfil(), "¿Qué quieres hacer?", "Selecciona una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

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

                    customer.setImagen(new ImageIcon(image));
                    lblImagen.setIcon(new ImageIcon(image.getScaledInstance(162, 162, Image.SCALE_SMOOTH)));

                    HashMap<String, Object> session = new HashMap<>();
                    session.put("Customer", customer);
                    session.put("Path", absolutePath);
                    ventana.getCliente().sendMessage("/modificarImagenCustomer", session);
                }

            }else if(opcionElegida == JOptionPane.NO_OPTION){
                String nombreNuevo = JOptionPane.showInputDialog(ventana, "Introduce un nombre", "Nombre:", JOptionPane.QUESTION_MESSAGE);
                if (!(nombreNuevo == null)) {
                    lblUsuario.setText("@" + nombreNuevo);
                    HashMap<String, Object> session = new HashMap<>();
                    session.put("Customer", customer);
                    session.put("NuevoNombre", nombreNuevo);
                    ventana.getCliente().sendMessage("/modificarNombreCustomer", session);

                    customer.setNombre(nombreNuevo);
                }
            }

        });

        pnlInfo.add(lblUsuario);
        pnlInfo.add(Box.createRigidArea(new Dimension(50,0))); //Añado separación entre nombre usuario y configuración
        if (GestorApp.USUARIO.equals(customer)) {
            pnlInfo.add(btnConfiguracion);
        }

        add(pnlInfo);
    }

    public void initPnlOpciones(){
        pnlOpciones = new JPanel();
        pnlOpciones.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlOpciones.setBorder(BorderFactory.createMatteBorder(0,0,1,0,LBL_COLOR));

        Boton btnPlaylist = new Boton("Movielist", LBL_COLOR, new ImageIcon("src/main/resources/playlistIcon.png"));
        btnPlaylist.delBackground();
        btnPlaylist.addHoverEffect();
        btnPlaylist.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnPlaylist.addActionListener(e -> {
            cardLayout.show(pnlPadre, "playlist");
        });

        Boton btnReviews = new Boton("Reviews", LBL_COLOR, new ImageIcon("src/main/resources/pencilIcon.png"));
        btnReviews.delBackground();
        btnReviews.addHoverEffect();
        btnReviews.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnReviews.addActionListener(e -> {
            cardLayout.show(pnlPadre, "reviews");
        });

        pnlOpciones.add(btnPlaylist);
        pnlOpciones.add(btnReviews);

        add(pnlOpciones, BorderLayout.SOUTH);
    }
}
