package main.java.ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TxtAnio extends Texto {
    public TxtAnio(int columns, Color textColor, Color backgroundColor) {
        super(columns, textColor, backgroundColor);

        this.addKeyListener(new KeyAdapter() {


            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();

                boolean numero = key >= 48 && key <= 57;
                if (!numero) {
                    e.consume();
                }
                if(TxtAnio.this.getText().length()>=2){
                    e.consume();
                }



            }
        });
    }
}