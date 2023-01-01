package main.java.ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TxtTitular extends Texto{
    public TxtTitular(int columns, Color textColor, Color backgroundColor) {
        super(columns, textColor, backgroundColor);

        this.addKeyListener(new KeyAdapter() {


            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();

                boolean mayusculas = key >= 65 && key <= 90;
                boolean minusculas = key >=97 && key<=122;
                boolean espacio = key ==32;
                if (!(minusculas || mayusculas || espacio)) {
                    e.consume();
                }
            }
        });

    }
    }
