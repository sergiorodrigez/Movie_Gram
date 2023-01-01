package main.java.ui;

import main.java.client.Cliente;
import main.java.domain.Customer;
import main.java.domain.GestorApp;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class PnlInicio extends JPanel {

    private CardLayout cards;
    private GridBagConstraints gbc = new GridBagConstraints();
    private GestorApp gestorApp;

    private JVentana ventana;

    private Cliente cliente;
    private HashMap<String, Object> session;
    private ArrayList<Customer> customers;



    public PnlInicio(CardLayout cards, JVentana ventana, Cliente cliente){
        super(cards);

        this.cards = cards;
        this.ventana = ventana;
        this.cliente = cliente;

        session = new HashMap<>();
        customers = ventana.getGestorApp().getCustomers();

        gestorApp = ventana.getGestorApp();

        initPnlInicioSesion();
        initPnlRegistro();
        initPnlRecuperacion();
    }

    public void initPnlInicioSesion(){
        JPanel pnlInicioSesion = new JPanel(new GridBagLayout());
        pnlInicioSesion.setBackground(JVentana.BACKGROUND_COLOR);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        setGbc(0,0,2,1);
        JLabel lblUsuario = new JLabel("Usuario ");
        lblUsuario.setForeground(JVentana.LBL_COLOR);
        lblUsuario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlInicioSesion.add(lblUsuario, gbc);

        setGbc(2,0,2,1);
        Texto txtUsuario = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtUsuario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlInicioSesion.add(txtUsuario, gbc);

        setGbc(0,1,2,1);
        JLabel lblContra = new JLabel("Contraseña ");
        lblContra.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblContra.setForeground(JVentana.LBL_COLOR);
        pnlInicioSesion.add(lblContra, gbc);

        setGbc(2,1,2,1);
        Contra pswContra = new Contra(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        pswContra.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlInicioSesion.add(pswContra, gbc);

        setGbc(0, 2, 4, 1);
        Boton btnNuevoUser = new Boton("Registrarse", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnNuevoUser.delBackground();
        btnNuevoUser.addHoverEffect();
        btnNuevoUser.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnNuevoUser.setForeground(JVentana.BTN_COLOR);
        pnlInicioSesion.add(btnNuevoUser, gbc);
        btnNuevoUser.addActionListener(e -> cards.show(this, "registro"));

        setGbc(0,3,4,1);
        Boton btnContraOlvidada = new Boton("Recuperar contraseña", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnContraOlvidada.delBackground();
        btnContraOlvidada.addHoverEffect();
        btnContraOlvidada.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnContraOlvidada.setForeground(JVentana.BTN_COLOR);
        pnlInicioSesion.add(btnContraOlvidada, gbc);
        btnContraOlvidada.addActionListener(e -> cards.show(this, "recuperar"));

        setGbc(0,4,4,1);
        Boton btnLogin = new Boton("Iniciar sesión", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnLogin.delBackground();
        btnLogin.addHoverEffect();
        btnLogin.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnLogin.setForeground(JVentana.BTN_COLOR);
        pnlInicioSesion.add(btnLogin, gbc);
        btnLogin.addActionListener(e -> {
            if (customers.contains(new Customer(txtUsuario.getText(), pswContra.getText()))){
                session.put("Customer", new Customer(txtUsuario.getText()));
                Customer cu = (Customer) cliente.sendMessage("/getCustomer", session).get("Customer");

                gestorApp.initPnlPerfil(cu);
                ventana.getCards().show(ventana.getPnlTodo(), "perfil");

            }else {
                JOptionPane.showMessageDialog(ventana, "Usuario no encontrado");
            }
            txtUsuario.setText("");
            pswContra.setText("");
        });

        add(pnlInicioSesion, "inicioSesion");
        cards.show(this, "inicioSesion");
    }

    public void initPnlRegistro(){
        JPanel pnlRegistro = new JPanel(new GridBagLayout());
        pnlRegistro.setBackground(JVentana.BACKGROUND_COLOR);

        setGbc(0,0,2,1);
        JLabel lblNuevoUsuario = new JLabel("Usuario ");
        lblNuevoUsuario.setForeground(JVentana.LBL_COLOR);
        lblNuevoUsuario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRegistro.add(lblNuevoUsuario, gbc);

        setGbc(2,0,2,1);
        Texto txtNuevoUsuario = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtNuevoUsuario.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRegistro.add(txtNuevoUsuario, gbc);

        setGbc(0,1,2,1);
        JLabel lblNuevoCorreo = new JLabel("Correo ");
        lblNuevoCorreo.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblNuevoCorreo.setForeground(JVentana.LBL_COLOR);
        pnlRegistro.add(lblNuevoCorreo, gbc);

        setGbc(2,1,2,1);
        Texto txtCorreoNuevo = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtCorreoNuevo.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRegistro.add(txtCorreoNuevo, gbc);

        setGbc(0,2,2,1);
        JLabel lblNuevaContra = new JLabel("Contraseña ");
        lblNuevaContra.setForeground(JVentana.LBL_COLOR);
        lblNuevaContra.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRegistro.add(lblNuevaContra, gbc);

        setGbc(2,2,2,1);
        Texto pswContraNueva = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        pswContraNueva.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRegistro.add(pswContraNueva, gbc);

        setGbc(0, 3, 4, 1);
        Boton btnRegistro = new Boton("Iniciar sesion", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnRegistro.delBackground();
        btnRegistro.addHoverEffect();
        btnRegistro.setForeground(JVentana.BTN_COLOR);
        pnlRegistro.add(btnRegistro, gbc);
        btnRegistro.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnRegistro.addActionListener(e -> {
            try {
                String path = "src/main/resources/newUser.png";
                Image image = ImageIO.read(new File(path));

                Customer customer = new Customer(txtNuevoUsuario.getText(), pswContraNueva.getText(), txtCorreoNuevo.getText(), new ImageIcon(image));
                session.put("Customer", customer);
                session.put("Path", path);

                HashMap<String, Object> result = cliente.sendMessage("/addCustomer", session);

                if (!(Boolean) result.get("UsuarioExistente")) {
                    gestorApp.initPnlPerfil(customer);
                    ventana.getCards().show(ventana.getPnlTodo(), "perfil");
                }else{
                    JOptionPane.showMessageDialog(ventana, "Usuario ya existente. Pruebe otro", "Usuario ya existente", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            txtCorreoNuevo.setText("");
            txtNuevoUsuario.setText("");
            pswContraNueva.setText("");

        });

        setGbc(0,4,4,1);
        Boton btnVolver = new Boton("Volver", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnVolver.delBackground();
        btnVolver.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnVolver.addHoverEffect();
        btnVolver.setForeground(JVentana.BTN_COLOR);
        pnlRegistro.add(btnVolver, gbc);
        btnVolver.addActionListener(e -> cards.show(this, "inicioSesion"));

        add(pnlRegistro, "registro");
    }

    public void initPnlRecuperacion(){
        JPanel pnlRecuperacion = new JPanel(new GridBagLayout());
        pnlRecuperacion.setBackground(JVentana.BACKGROUND_COLOR);

        setGbc(0,0,2,1);
        JLabel lblCorreoParaRecuperarContra = new JLabel("Correo ");
        lblCorreoParaRecuperarContra.setForeground(JVentana.LBL_COLOR);
        lblCorreoParaRecuperarContra.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRecuperacion.add(lblCorreoParaRecuperarContra, gbc);

        setGbc(2,0,2,1);
        Texto txtCorreoParaRecuperarContra = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtCorreoParaRecuperarContra.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlRecuperacion.add(txtCorreoParaRecuperarContra, gbc);

        setGbc(0,1,4,1);
        Boton btnRecuperarContra = new Boton("Recuperar", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnRecuperarContra.delBackground();
        btnRecuperarContra.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnRecuperarContra.addHoverEffect();
        btnRecuperarContra.setForeground(JVentana.BTN_COLOR);
        btnRecuperarContra.addActionListener(e->{
            if (txtCorreoParaRecuperarContra.getText() == null){
                JOptionPane.showMessageDialog(ventana, "Introduzca un correo valido", "Correo no valido", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(pnlRecuperacion, "Ahora le enviaremos un correo con su correspondiente contraseña.");

                Properties props = System.getProperties();
                props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
                props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
                props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
                props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP

                Session session = Session.getDefaultInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("moviegramcompany@gmail.com", "rjghxytmfxmegatw");
                    }
                });
                MimeMessage message = new MimeMessage(session);

                HashMap<String, Object> sendSession = new HashMap<>();
                sendSession.put("Correo", txtCorreoParaRecuperarContra.getText());
                HashMap<String, Object> result = ventana.getCliente().sendMessage("/getContra", sendSession);
                String contra = (String) result.get("Contra");

                try {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(txtCorreoParaRecuperarContra.getText(), true));
                    message.setSubject("RECUPERACIÓN DE SU CONTRASEÑA.");
                    message.setText("Buenas tardes, " + "\n" + "Desde MovieGram le adjuntamos su correspondiente contraseña: " +
                            "\n" + contra + "\n"
                            + "Esperemos que disfrute de la aplicación MovieGram!"); //cambiar
                    Transport.send(message);
                } catch (MessagingException me) {
                    System.out.println("Excepton: " + me);
                }
            }
        });
        pnlRecuperacion.add(btnRecuperarContra, gbc);

        setGbc(0,2,4,1);
        Boton btnVolver = new Boton("Volver", JVentana.BTN_COLOR, new Color(239, 221, 221));
        btnVolver.delBackground();
        btnVolver.setStyle(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnVolver.addHoverEffect();
        btnVolver.setForeground(JVentana.BTN_COLOR);
        btnVolver.addActionListener(e -> cards.show(this, "inicioSesion"));
        pnlRecuperacion.add(btnVolver, gbc);


        add(pnlRecuperacion, "recuperar");
    }


    private void setGbc(int gridX, int gridY, int gridWidth, int gridHeight){
        gbc.gridheight = gridHeight;
        gbc.gridwidth = gridWidth;
        gbc.gridx = gridX;
        gbc.gridy = gridY;
    }
}
