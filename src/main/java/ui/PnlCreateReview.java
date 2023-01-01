package main.java.ui;

import main.java.domain.*;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class PnlCreateReview extends JFrame {

    private Pelicula p;
    private JVentana ventana;

    public static final int ANCHURA = 700;
    public static final int ALTURA = 300;

    private static final Color LBL_COLOR = new Color(183, 179, 179);

    HashMap<String, Object> session = new HashMap<>();

    public PnlCreateReview(Pelicula pelicula, JVentana ventana){
        this.ventana = ventana;
        this.p = pelicula;

        setSize(ANCHURA,ALTURA);
        setLocation(ventana.getLocation());
        setVisible(true);

        initPnls();
    }

    public void initPnls(){

        JPanel pnlReview = new JPanel(new BorderLayout());
        pnlReview.setBackground(JVentana.R_BACKGROUND_COLOR);


        JLabel foto = new JLabel();
        try {
            foto.setIcon(new ImageIcon(new URL(p.getUrl())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        pnlReview.add(foto, BorderLayout.WEST);

        JPanel pnlCentral = new JPanel(new GridLayout(4,1));
        pnlCentral.setBackground(JVentana.R_BACKGROUND_COLOR);

        JPanel pnlName = new JPanel(new FlowLayout());
        pnlName.setBackground(JVentana.R_BACKGROUND_COLOR);
        JPanel pnlVal = new JPanel(new FlowLayout());
        pnlVal.setBackground(JVentana.R_BACKGROUND_COLOR);
        JPanel pnlComent = new JPanel(new FlowLayout());
        pnlComent.setBackground(JVentana.R_BACKGROUND_COLOR);
        JPanel pnlClose = new JPanel(new BorderLayout());
        pnlClose.setBackground(JVentana.R_BACKGROUND_COLOR);

        JLabel name = new JLabel("    " + p.getNombre());
        name.setForeground(Color.WHITE);
        name.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlName.add(name);

        JLabel valoracion = new JLabel("Valoracion(1/5): ");
        Texto txtValoracion = new Texto(1, JVentana.LBL_COLOR, JVentana.R_BACKGROUND_COLOR);
        valoracion.setForeground(Color.WHITE);
        valoracion.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        txtValoracion.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlVal.add(valoracion);
        pnlVal.add(txtValoracion);

        JLabel comentario = new JLabel("Comentario: " );
        Texto txtComentario = new Texto(30, JVentana.LBL_COLOR, JVentana.R_BACKGROUND_COLOR);
        txtComentario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        comentario.setForeground(Color.WHITE);
        comentario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlComent.add(comentario);
        pnlComent.add(txtComentario);

        Boton btnClose = new Boton("Cerrar y Guardar", LBL_COLOR);
        btnClose.delBackground();
        btnClose.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnClose.addHoverEffect();
        btnClose.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnClose.addActionListener(e -> {
            if (Integer.parseInt(txtValoracion.getText()) > 0 && Integer.parseInt(txtValoracion.getText()) < 6 && txtComentario.getText().length() < 50){
                session.put("Review", new Review(GestorApp.USUARIO.getNombre(), p.getId_p(), txtComentario.getText(), Integer.parseInt(txtValoracion.getText())));
                ventana.getCliente().sendMessage("/addReview", session);

                this.dispose();
                ventana.getGestorApp().getPnlReviews().removeAll();
                ventana.getGestorApp().getPnlReviews().initPnls(ventana.getGestorApp().getReviews(GestorApp.USUARIO));
            }
            else
                JOptionPane.showMessageDialog(ventana, "Review no valida. La valoracion debe ser entre 1 y 5 y debe tener menos de 50 caracteres", "Review no valida", JOptionPane.ERROR_MESSAGE);

        });
        pnlClose.add(btnClose, BorderLayout.CENTER);

        pnlCentral.add(pnlName);
        pnlCentral.add(pnlVal);
        pnlCentral.add(pnlComent);
        pnlCentral.add(pnlClose);
        pnlReview.add(pnlCentral, BorderLayout.CENTER);

        add(pnlReview);
    }

}
