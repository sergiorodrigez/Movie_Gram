package main.java.ui;

import main.java.domain.GestorApp;
import main.java.domain.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PnlnewPlaylist  extends JPanel {

    private JVentana ventana;

    private GridBagConstraints gbc = new GridBagConstraints();

    public PnlnewPlaylist(JVentana ventana) {
        this.ventana=ventana;

        initComponentes();
    }

    public void initComponentes(){
        setLayout(new GridBagLayout());
        setBackground(JVentana.R_BACKGROUND_COLOR);

        setGbc(0,0,2,1);
        JLabel lblNuevaPlaylist = new JLabel("Movielist  ");
        lblNuevaPlaylist.setForeground(JVentana.LBL_COLOR);
        lblNuevaPlaylist.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        add(lblNuevaPlaylist, gbc);

        setGbc(2,0,2,1);
        Texto txtNuevaPlaylist = new Texto(15, JVentana.LBL_COLOR, JVentana.R_BACKGROUND_COLOR);
        txtNuevaPlaylist.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        add(txtNuevaPlaylist, gbc);

        setGbc(0, 1, 4, 1);
        Boton btnCrear = new Boton("Crear Movielist", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnCrear.delBackground();
        btnCrear.addHoverEffect();
        btnCrear.setForeground(JVentana.BTN_COLOR);
        btnCrear.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        add(btnCrear, gbc);
        btnCrear.addActionListener(e -> {
            HashMap<String, Object> session = new HashMap<>();

            HashMap<String, Object> result = ventana.getCliente().sendMessage("/getNewPlaylistId", session);
            int newId = (int) result.get("NewId");


            String path = "src/main/resources/movieList.png";
            Playlist playlist = new Playlist(newId, txtNuevaPlaylist.getText(), GestorApp.USUARIO.getNombre(), new ImageIcon(path));
            session.put("Playlist", playlist);
            session.put("Path", path);
            session.put("Customer", GestorApp.USUARIO);
            result = ventana.getCliente().sendMessage("/addPlaylist", session);

            if ((Boolean) result.get("PlaylistExistente")){
                JOptionPane.showMessageDialog(ventana, "Movielist ya existente. Pruebe otro nombre", "Movielist ya existente", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ventana, "Movielist creada correctamente", "Movielist creada", JOptionPane.INFORMATION_MESSAGE);
                ventana.getGestorApp().getPnlPlaylist().removeAll();
                ventana.getGestorApp().getPnlPlaylist().initPnls();
            }
            txtNuevaPlaylist.setText("");
        });
    }

    private void setGbc(int gridX, int gridY, int gridWidth, int gridHeight){
        gbc.gridheight = gridHeight;
        gbc.gridwidth = gridWidth;
        gbc.gridx = gridX;
        gbc.gridy = gridY;
    }
}
