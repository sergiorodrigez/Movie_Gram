package main.java.ui;

import main.java.domain.GestorApp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Properties;

public class PnlComprar extends JPanel {

    private JVentana ventana;
    private GridBagConstraints gbc = new GridBagConstraints();
    //private KeyEvent evt= new KeyEvent();
    public PnlComprar(JVentana ventana){
        this.ventana= ventana;

        ventana.getPnlCentralPerfil().add(this, "CompraEntradas");

        initCompra();
    }
    public void initCompra(){

        setLayout(new BorderLayout());
        JPanel pnlTitulo= new JPanel(new GridBagLayout());
        pnlTitulo.setBackground(JVentana.BACKGROUND_COLOR);

        JLabel lblLogo= new JLabel();
        lblLogo.setIcon(new ImageIcon(new ImageIcon("src/main/resources/logo.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        pnlTitulo.add(lblLogo,gbc);
        pnlTitulo.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));


        JPanel pnlTarjeta = new JPanel(new GridBagLayout());
        pnlTarjeta.setBackground(JVentana.R_BACKGROUND_COLOR);
        pnlTarjeta.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.WHITE));
        pnlTarjeta.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setGbc(0,0,2,1);
        JLabel lblNumTarjeta = new JLabel("Número de la tarjeta");
        lblNumTarjeta.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblNumTarjeta.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblNumTarjeta,gbc);
        lblNumTarjeta.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        setGbc(2,0,2,1);
        TxtTarjetas txtNumTarjeta = new TxtTarjetas(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtNumTarjeta.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlTarjeta.add(txtNumTarjeta, gbc);

        setGbc(0,2,2,1);
        JLabel lblFecha = new JLabel("Fecha de Caducidad");
        lblFecha.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblFecha.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblFecha,gbc);
        lblFecha.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        setGbc(3,2,1,1);
        TxtMes txtMes= new TxtMes(2, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtMes.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlTarjeta.add(txtMes, gbc);

        setGbc(4,2,2,1);
        JLabel lblSeparador = new JLabel("/");
        lblSeparador.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblSeparador.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblSeparador,gbc);

        setGbc(6,2,1,1);
        TxtAnio txtAnio=new TxtAnio(2, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtAnio.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlTarjeta.add(txtAnio, gbc);


        //titular
        setGbc(1,4,2,1);
        JLabel lblTitular = new JLabel("Titular");
        lblTitular.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblTitular.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblTitular,gbc);

        setGbc(0,5,2,1);
        JLabel lblSr = new JLabel("Sr./Sra. ");
        lblSr.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblSr.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblSr,gbc);
        lblSr.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        setGbc(2,5,2,1);
        Texto txtTitular = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtTitular.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlTarjeta.add(txtTitular, gbc);

        //emisor
        setGbc(0,7,2,1);
        JLabel lblEmisor = new JLabel("Emisor de la Tarjeta");
        lblEmisor.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblEmisor.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblEmisor,gbc);
        lblEmisor.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        setGbc(2,7,2,1);
        Texto txtEmisor = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtEmisor.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlTarjeta.add(txtEmisor, gbc);

        //cvv
        setGbc(0,9,2,1);
        JLabel lblCVV = new JLabel("CVV");
        lblCVV.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblCVV.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblCVV,gbc);
        lblCVV.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        setGbc(2,9,2,1);
        Texto txtCVV = new Texto(15, JVentana.LBL_COLOR, JVentana.BACKGROUND_COLOR);
        txtCVV.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        pnlTarjeta.add(txtCVV, gbc);

        //El total del coste
        //poner un icono de un carrito
        setGbc(0, 12,4 , 1);
        JLabel lblCoste = new JLabel(new ImageIcon("src/main/resources/buy.png"));
        lblCoste.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));

        pnlTarjeta.add(lblCoste,gbc);
        lblCoste.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));

        setGbc(0, 12,4 , 1);
        JLabel lblTotCompra = new JLabel("Total de la compra: 10€");
        lblTotCompra.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        lblTotCompra.setForeground(JVentana.LBL_COLOR);
        pnlTarjeta.add(lblTotCompra,gbc);


        //comprar
        setGbc(5, 12,4 , 1);
        Boton btnComprar = new Boton(" Confirmar compra", JVentana.LBL_COLOR);
        btnComprar.setFont(new Font(JVentana.FONT_LBL, Font.PLAIN, JVentana.LBL_SIZE));
        btnComprar.delBackground();
        btnComprar.addHoverEffect();
        btnComprar.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));
        pnlTarjeta.add(btnComprar, gbc);

        btnComprar.addActionListener(e-> {
            JOptionPane.showMessageDialog(this, "Gracias " + GestorApp.USUARIO.getNombre() + " por comprar la entrada."
                    + "\n Ahora le enviaremos un correo con el código QR de su entrada.");

            //enviamos correo
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
            try {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(GestorApp.USUARIO.getCorreo(), true));
                message.setSubject("Compra realizada correctamente");

                Multipart emailContent = new MimeMultipart();

                //text body part
                MimeBodyPart textBodyPart= new MimeBodyPart();
                textBodyPart.setText("Entrada.");

                //attachment body part
                MimeBodyPart pdfAttachment= new MimeBodyPart();
                pdfAttachment.attachFile("src/main/resources/EntradaMovieGram.pdf");

                //Attach body parts
                emailContent.addBodyPart(textBodyPart);
                emailContent.addBodyPart(pdfAttachment);

                //attach multipart to message
                message.setContent(emailContent);

                Transport.send(message);
            } catch (MessagingException me) {
                System.out.println("Excepton: " + me);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(pnlTarjeta,BorderLayout.CENTER);
        add(pnlTitulo,BorderLayout.NORTH);
        setBackground(JVentana.R_BACKGROUND_COLOR);
    }



    private void setGbc(int gridX, int gridY, int gridWidth, int gridHeight){
        gbc.gridheight = gridHeight;
        gbc.gridwidth = gridWidth;
        gbc.gridx = gridX;
        gbc.gridy = gridY;
    }

    private void txtNumTarjetaCondiciones(KeyEvent evt,JTextField txt){
        int key= evt.getKeyChar();
        boolean numeros = key>= 48 && key<=57;
        if(!numeros){
            evt.consume();
        }
        if(txt.getText().length()>16){
            evt.consume();
        }

    }

}
