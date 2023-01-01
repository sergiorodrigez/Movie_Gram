package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PnlLogo extends JPanel {

    private ArrayList<Imagen> imagenes;

    public PnlLogo(){
        imagenes = new ArrayList<>();
        setBackground(JVentana.BACKGROUND_COLOR);
    }

    public void addImagen(Imagen imagen){
        imagenes.add(imagen);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        imagenes.forEach(imagen -> imagen.pintar(g));
        g.dispose();
    }
}
