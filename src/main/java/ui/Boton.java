package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Boton extends JButton {

    private Color color;
    private Color colorHover;
    private ImageIcon icon;
    private ImageIcon iconHover;


    public Boton(String text, Color color){
        this(text, color, color);
    }

    public Boton(String text, Color color, Color colorHover){
        this(text, color, colorHover, null, null);
    }

    public Boton(String text, Color color, ImageIcon icon, ImageIcon iconHover){
        this(text, color, color, icon, iconHover);
    }

    public Boton(String text, Color color, ImageIcon icon){
        this(text, color, color, icon, icon);
    }

    public Boton(String text, Color color, Color colorHover, ImageIcon icon, ImageIcon iconHover){
        super(text);
        this.icon = icon;
        this.iconHover = iconHover;
        this.colorHover = colorHover;
        this.color = color;

        setForeground(color);
        setIcon(icon);
    }

    public void addHoverEffect(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setForeground(colorHover);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setIcon(iconHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setForeground(color);
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
                setIcon(icon);
            }
        });
    }

    public void setStyle(Font font){
        setIconTextGap(7);
        setFont(font);
    }

    public void delBackground(){
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
}
