package main.java.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Imagen {

    private Image image;
    private int x;
    private int y;


    public Imagen(String ruta, int x, int y, int anchuraEspecifica, int alturaEspecifica){
        try {
            Image imageAux = ImageIO.read(new File(ruta));
            image = imageAux.getScaledInstance(anchuraEspecifica, alturaEspecifica, Image.SCALE_SMOOTH);
        }catch (IOException e){
            e.printStackTrace();
        }

        this.x = x;
        this.y = y;
    }

    public void pintar(Graphics g){
        g.drawImage(image, x, y, null);
    }
}
