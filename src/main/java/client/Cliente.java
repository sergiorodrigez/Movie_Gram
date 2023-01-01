package main.java.client;

import main.java.domain.*;
import main.java.message.Message;
import main.java.configuration.PropertiesISW;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class Cliente {

    //Cliente es el que se conecta a la aplicación
    private String host;
    private int port;

    final static Logger logger = Logger.getLogger(Cliente.class);

    public Cliente(String host, int port){
        this.host = host;
        this.port = port;
    }

    //El por defecto que aparece en properties
    public Cliente(){
        host = PropertiesISW.getInstance().getProperty("host");
        port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
    }

    public HashMap<String, Object> sendMessage(String context, HashMap<String, Object> session){

        /*
        Object message = "Host: " + host + " port" + port;
        Logger.getRootLogger().info(message);
        System.out.println(message);

         */

        //La clase mensaje es lo que viaja por la red. Tiene que implementar el interfaz
        //Serializable para poder convertirlo en bytes.
        Message messageSent = new Message();
        Message messageRecieved = new Message();

        //Mensaje enviado
        messageSent.setContext(context); //Por ejemeplo: /getCustomers
        messageSent.setSession(session);

        sent(messageSent, messageRecieved);

        //Inicializamos
        Customer customer;
        Playlist playlist;
        Review review;

        //En cada contexto del mensaje que ha recibido el servidor realizo una cosa u otra
        switch(messageRecieved.getContext()){
            case "/addCustomerResponse":
                boolean usuarioExistente = (boolean) messageRecieved.getSession().get("UsuarioExistente");
                customer = (Customer) messageRecieved.getSession().get("Customer");
                if (!usuarioExistente){
                    System.out.println("Se ha añadido el usuario " + customer.getNombre() + " correctamente");
                }
                break;
            case "/addPlaylistResponse":
                boolean playlistExistente = (boolean) messageRecieved.getSession().get("PlaylistExistente");
                playlist = (Playlist) messageRecieved.getSession().get("Playlist");
                if (!playlistExistente){
                    System.out.println("Se ha añadido la Movielist " + playlist.getNombre() + " perteneciente a " + playlist.getNombrePropietario() + " correctamente");
                }
                break;
            case "/modificarNombreCustomerResponse":
                customer = (Customer) messageRecieved.getSession().get("Customer");
                System.out.println("Se ha cambiado el nombre a " + customer.getNombre());
                break;
            case "/borrarPlaylistResponse":
                playlist = (Playlist) messageRecieved.getSession().get("Playlist");
                System.out.println("Se ha borrado la Movielist " + playlist.getNombre() + " perteneciente a " + playlist.getNombrePropietario() + " correctamente");
                break;
            case "/modificarImagenCustomerResponse":
                customer = (Customer) messageRecieved.getSession().get("Customer");
                System.out.println("Se ha cambiado la imagen a " + customer.getNombre());
                break;
            case "/modificarImagenPlaylistResponse":
                playlist = (Playlist) messageRecieved.getSession().get("Playlist");
                System.out.println("Se ha cambiado la imagen a la Movielist " + playlist.getNombre() +" perteneciente al usuario " + playlist.getNombrePropietario());
                break;
            case "/modificarNombrePlaylistResponse":
                playlist = (Playlist) messageRecieved.getSession().get("Playlist");
                System.out.println("Se ha cambiado el nombre a la Movielist " + playlist.getNombre() +" perteneciente al usuario " + playlist.getNombrePropietario());
                break;
            case "/addReviewResponse":
                review = (Review) messageRecieved.getSession().get("Review");
                System.out.println("Review a la pelicula " + review.getId_p() + " añadida");
                break;
            case "/getCustomersResponse":
            case "/getCustomerResponse":
            case "/getPlaylistResponse":
            case "/getPeliculasResponse":
            case "/getReviewsResponse":
            case "/getPlaylistPeliculasResponse":
            case "/searchPeliculasResponse":
            case "/searchCustomersResponse":
            case "/getPlaylistPeliculas":
            case "/getNewPlaylistIdResponse":
            case "/searchPlaylistResponse":
            case "/addPlaylistPeliculaResponse":
            case "/getContraResponse":
                break;
            default:
                Logger.getRootLogger().info("Option not found");
                System.out.println("Error a la vuelta: " + messageRecieved.getContext());
                break;
        }
        session = messageRecieved.getSession();

        return session;
    }

    //Envio de mensajes por la red.
    public void sent(Message messageOut, Message messageIn){
        try{
            //System.out.println("Connecting to host " + host + " on port " + port);

            Socket echoSocket = null;
            OutputStream out = null;
            InputStream in = null;

            try{
                //Lanzo servidor en la direccion y el puerto del cliente para lanzar mensajes
                echoSocket = new Socket(host, port);
                in = echoSocket.getInputStream();
                out = echoSocket.getOutputStream();

                //Mensaje que mando
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

                //Mando el mensaje
                objectOutputStream.writeObject(messageOut);

                //Me preparo para poder leer el mensaje
                ObjectInputStream objectInputStream = new ObjectInputStream(in);

                //Leo el mensaje.
                Message msg = (Message) objectInputStream.readObject();

                //Actualizo el mensaje recibido con lo que he leido
                messageIn.setContext(msg.getContext());
                messageIn.setSession(msg.getSession());
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + host);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }/** Closing all the resources */
            out.close();
            in.close();
            echoSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
