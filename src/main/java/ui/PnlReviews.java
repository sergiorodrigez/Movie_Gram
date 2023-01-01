package main.java.ui;

import main.java.domain.Pelicula;
import main.java.domain.Review;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PnlReviews extends JPanel {

    ArrayList<Review> reviews;
    ArrayList<Pelicula> peliculas;

    public PnlReviews(ArrayList<Review> reviews, ArrayList<Pelicula> peliculas){
        this.peliculas = peliculas;

        setBackground(JVentana.R_BACKGROUND_COLOR);
        initPnls(reviews);

    }

    public void initPnls(ArrayList<Review> reviews){
        this.reviews = reviews;
        if (!(reviews==null)) {
            if (reviews.size() == 1) {
                initPnlsPeliculas();
                initPnlExtra(388);
            } else if (reviews.size() == 2) {
                initPnlsPeliculas();
                initPnlExtra(168);
            } else {
                initPnlsPeliculas();
            }
        }
    }




    public void initPnlsPeliculas(){

        reviews.forEach(r ->{
            peliculas.forEach(p ->{
                if(p.getId_p() == r.getId_p()){
                    JPanel pnlReview = new JPanel(new BorderLayout());
                    //pnlReview.setPreferredSize(new Dimension(this.getWidth(), 220));
                    pnlReview.setBackground(JVentana.R_BACKGROUND_COLOR);
                    JLabel foto = new JLabel();
                    try {
                        ImageIcon image = new ImageIcon(new URL(p.getUrl()));
                        foto.setIcon(new ImageIcon(image.getImage().getScaledInstance(image.getIconWidth(), 220, Image.SCALE_SMOOTH)));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    pnlReview.add(foto, BorderLayout.WEST);

                    JPanel pnlCentral = new JPanel(new GridLayout(3,1));

                    pnlCentral.setBackground(JVentana.R_BACKGROUND_COLOR);

                    //Para que aparezca en el medio
                    JPanel pnlTopVacio = new JPanel();
                    pnlTopVacio.setBackground(JVentana.R_BACKGROUND_COLOR);
                    JPanel pnlBottomVacio = new JPanel();
                    pnlBottomVacio.setBackground(JVentana.R_BACKGROUND_COLOR);

                    JPanel pnlInfo = new JPanel();
                    pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
                    pnlInfo.setBackground(JVentana.R_BACKGROUND_COLOR);

                    JLabel name = new JLabel("    " + p.getNombre());
                    name.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
                    name.setForeground(Color.WHITE);

                    JLabel valoracion = new JLabel("    Valoracion: " + r.getVal());
                    valoracion.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
                    valoracion.setForeground(Color.WHITE);

                    JLabel comentario = new JLabel("    Comentario:\n " + r.getComent());
                    comentario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
                    comentario.setBackground(JVentana.R_BACKGROUND_COLOR);
                    comentario.setForeground(Color.WHITE);

                    pnlInfo.add(name);
                    pnlInfo.add(valoracion);
                    pnlInfo.add(comentario);

                    pnlCentral.add(pnlTopVacio);
                    pnlCentral.add(pnlInfo);
                    pnlCentral.add(pnlBottomVacio);

                    pnlReview.add(pnlCentral, BorderLayout.CENTER);
                    add(pnlReview);
                }
            });
        });
    }

    public void initPnlExtra(int height){
        JPanel pnlExtra = new JPanel();
        pnlExtra.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlExtra.setPreferredSize(new Dimension(this.getWidth(), height));
        add(pnlExtra);
    }
}
