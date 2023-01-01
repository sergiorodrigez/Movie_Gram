package main.java.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Texto extends JTextField {

    private Color textColor;

    public Texto(int columns){
        super(columns);
    }

    public Texto(int columns, Color textColor, Color backgroundColor){
        this(columns);
        this.textColor = textColor;


        setBackground(backgroundColor);
        setBorder(new EmptyBorder(7, 3, 7, 3));
        setForeground(textColor);
        setCaretColor(textColor);
    }


    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);

        g2.setColor(textColor);
        g2.fillRect(0, getHeight() - 7, getWidth(), 1);
        g2.dispose();

    }
}