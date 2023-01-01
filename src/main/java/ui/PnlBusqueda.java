package main.java.ui;

import main.java.domain.Customer;
import main.java.domain.Pelicula;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PnlBusqueda extends JPanel {

    private JVentana ventana;

    private PnlFeed pnlFeed;

    private JPanel pnlCentralFeed;
    private CardLayout cardsFeed;

    public PnlBusqueda(JVentana ventana, LayoutManager layoutManager){
        super(layoutManager);

        this.ventana = ventana;
        initPnlNorte();
        initPnlCentral();
    }

    public void initPnlNorte(){
        JPanel pnlNorte = new JPanel();
        pnlNorte.setLayout(new BoxLayout(pnlNorte, BoxLayout.Y_AXIS));

        pnlNorte.setBackground(JVentana.R_BACKGROUND_COLOR);

        Texto txtBuscar = new Texto(30, JVentana.LBL_COLOR, JVentana.R_BACKGROUND_COLOR);
        txtBuscar.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlNorte.add(txtBuscar);


        Boton btnBusqueda = new Boton("Buscar", Color.WHITE);
        btnBusqueda.delBackground();
        btnBusqueda.addHoverEffect();
        btnBusqueda.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));

        btnBusqueda.addActionListener(e -> {
            String txtBuscado = txtBuscar.getText();

            HashMap<String, Object> session = new HashMap<>();
            session.put("TextoCustomer", txtBuscado);
            session.put("TextoPelicula", txtBuscado);

            ArrayList<Customer> customers = (ArrayList<Customer>) ventana.getCliente().sendMessage("/searchCustomers", session).get("CustomersFound");
            ArrayList<Pelicula> peliculas = (ArrayList<Pelicula>) ventana.getCliente().sendMessage("/searchPeliculas", session).get("PeliculasFound");

            if (customers.size() > 0 || peliculas.size() > 0){
                pnlFeed = new PnlFeed(ventana, this, customers, peliculas);
                pnlFeed.setLayout(new BoxLayout(pnlFeed, BoxLayout.Y_AXIS));


                JScrollPane pnlScrollFeed = new JScrollPane(pnlFeed, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


                pnlScrollFeed.getVerticalScrollBar().setBackground(JVentana.R_BACKGROUND_COLOR);
                pnlScrollFeed.getVerticalScrollBar().setUnitIncrement(20);
                pnlScrollFeed.getVerticalScrollBar().setUI(new CustomScrollBarUI());
                pnlScrollFeed.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));


                pnlScrollFeed.setFocusable(false);
                pnlScrollFeed.setBorder(null);

                pnlCentralFeed.add(pnlScrollFeed, "feed");

                cardsFeed.show(pnlCentralFeed, "feed");
            } else{
                JOptionPane.showMessageDialog(ventana, "No se han encontrado resultados para la busqueda");

                cardsFeed.show(pnlCentralFeed, "feedVacio");

            }
        });

        pnlNorte.add(btnBusqueda);

        add(pnlNorte, BorderLayout.NORTH);
    }

    private void initPnlCentral(){
        cardsFeed = new CardLayout();
        pnlCentralFeed = new JPanel(cardsFeed);

        JPanel pnlFeedVacio = new JPanel();
        pnlFeedVacio.setBackground(JVentana.R_BACKGROUND_COLOR);

        pnlCentralFeed.add(pnlFeedVacio, "feedVacio");

        cardsFeed.show(pnlCentralFeed, "feedVacio");

        add(pnlCentralFeed);

    }
}
