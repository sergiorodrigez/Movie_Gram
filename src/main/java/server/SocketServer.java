package main.java.server;

import main.java.configuration.PropertiesISW;
import main.java.controller.CustomerControler;
import main.java.controller.PeliculaController;
import main.java.controller.PlaylistController;
import main.java.controller.ReviewController;
import main.java.domain.*;
import main.java.message.Message;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


/*
    El cliente manda peticiones al sockerServer a través de mensajes que tienen un contexto y una sesion.
    Si le pide que le de todos los usuarios, en la sesion el String sera "Customers" y el objeto un array con todos
    los usuarios.

    El socketServer analiza las peticiones y las direcciona a la base de datos. Una vez saca toda la info
    manda un mensaje de vuelta al cliente indicandole que todo ha ido bien.
 */
public class SocketServer extends Thread{

    //Puerto que abre el servidor para conexiones externas. El puerto al que accederá el cliente.
    public static int port = 8081; //Valor por defecto

    protected Socket socket;

    private SocketServer(Socket socket){
        this.socket = socket;

        port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
        start(); //Empieza el thread
    }

    public void run() {
        //Bytes que entran y salen del socket
        InputStream in = null;
        OutputStream out = null;

        try{
            in = socket.getInputStream();
            out = socket.getOutputStream();

            //Primero leemos el objeto que entra. Como lo que entra es de la clase mensaje (lo envía el cliente,
            //hay que hacer downcast a mensaje).
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Message messageIn = (Message) objectInputStream.readObject();

            //Creamos mensaje para devolver
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            Message messageOut = new Message();

            //Session del mensaje que llega al servidor
            HashMap<String, Object> session = messageIn.getSession();

            //Inicializamos
            CustomerControler customerControler;
            PlaylistController playlistController;
            PeliculaController peliculaController;
            ReviewController reviewController;
            String nuevoNombre;

            String path;
            Customer customer;
            Review review;
            PlaylistPelicula playlistPelicula;

            ArrayList<Customer> customers;
            ArrayList<Pelicula> peliculas;

            switch (messageIn.getContext()){
                case "/getCustomers":
                    customerControler = new CustomerControler();
                    customers = new ArrayList<>();
                    customerControler.getCustomers(customers);

                    //Preparamos mensaje de salida. Lo que le llega a cliente
                    messageOut.setContext("/getCustomersResponse");
                    session.put("Customers", customers);
                    messageOut.setSession(session);

                    //Mandamos mensaje de salida a cliente
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getCustomer":
                    customerControler = new CustomerControler();
                    //El customer a buscar estara dentro de session
                    Customer cu = (Customer) session.get("Customer");
                    customer = customerControler.getCustomer(cu);

                    //Preparo mensaje de salida
                    messageOut.setContext("/getCustomerResponse");
                    session.put("Customer", customer);
                    messageOut.setSession(session);

                    //Mando mensaje a cliente
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/searchCustomers":
                    customerControler = new CustomerControler();
                    ArrayList<Customer> customersFound = new ArrayList<>();

                    customerControler.searchCustomer(customersFound, (String) session.get("TextoCustomer"));

                    messageOut.setContext("/searchCustomersResponse");
                    session.put("CustomersFound", customersFound);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/searchPeliculas":
                    peliculaController = new PeliculaController();
                    ArrayList<Pelicula> peliculasFound = new ArrayList<>();

                    peliculaController.searchPelicula(peliculasFound, (String) session.get("TextoPelicula"));

                    messageOut.setContext("/searchPeliculasResponse");
                    session.put("PeliculasFound", peliculasFound);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/searchPlaylist":
                    playlistController = new PlaylistController();
                    ArrayList<Playlist> playlistFound = new ArrayList<>();

                    playlistController.searchPlaylist(playlistFound,  (String) session.get("TextoPlaylist"));

                    messageOut.setContext("/searchPlaylistResponse");
                    session.put("PlaylistFound", playlistFound);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getPlaylist":
                    playlistController = new PlaylistController();
                    customer = (Customer) session.get("Customer");
                    ArrayList<Playlist> playlists = new ArrayList<>();
                    playlistController.getPlaylists(playlists, customer);

                    messageOut.setContext("/getPlaylistResponse");
                    session.put("Playlist", playlists);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getPeliculas":
                    peliculaController = new PeliculaController();
                    peliculas = new ArrayList<>();
                    peliculaController.getPeliculas(peliculas);

                    messageOut.setContext("/getPeliculasResponse");
                    session.put("Peliculas", peliculas);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getReviews":
                    reviewController = new ReviewController();
                    ArrayList<Review> reviews = new ArrayList<>();
                    customer = (Customer) session.get("Customer");
                    reviewController.getReviews(reviews, customer);

                    messageOut.setContext("/getReviewsResponse");
                    session.put("Reviews", reviews);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/addCustomer":
                    customerControler = new CustomerControler();
                    customer = (Customer) session.get("Customer");
                    path = (String) session.get("Path");

                    customerControler.addCustomer(customer, new File(path), session);

                    messageOut.setContext("/addCustomerResponse");
                    session.put("Customer", customer);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/modificarImagenCustomer":
                    customerControler = new CustomerControler();
                    customer = (Customer) session.get("Customer");
                    path = (String) session.get("Path");

                    customerControler.modificarImagen(customer, new File(path));

                    messageOut.setContext("/modificarImagenCustomerResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/modificarNombreCustomer":
                    customerControler = new CustomerControler();
                    customer = (Customer) session.get("Customer");
                    nuevoNombre = (String) session.get("NuevoNombre");

                    customerControler.modificarNombre(customer, nuevoNombre);

                    messageOut.setContext("/modificarNombreCustomerResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/modificarImagenPlaylist":
                    playlistController = new PlaylistController();
                    Playlist playlist = (Playlist) session.get("Playlist");
                    path = (String) session.get("Path");

                    playlistController.modificarImagen(playlist, new File(path));

                    messageOut.setContext("/modificarImagenPlaylistResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/modificarNombrePlaylist":
                    playlistController = new PlaylistController();
                    playlist = (Playlist) session.get("Playlist");
                    nuevoNombre = (String) session.get("NuevoNombre");

                    playlistController.modificarNombre(playlist, nuevoNombre);

                    messageOut.setContext("/modificarNombrePlaylistResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getPlaylistPeliculas":
                    playlistController = new PlaylistController();
                    ArrayList<PlaylistPelicula> playlistPeliculas = new ArrayList<>();

                    playlistController.getPlaylistPeliculas(playlistPeliculas);

                    messageOut.setContext("/getPlaylistPeliculasResponse");
                    session.put("PlaylistPeliculas", playlistPeliculas);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/addReview":
                    reviewController = new ReviewController();
                    review = (Review) session.get("Review");

                    reviewController.addReview(review);
                    messageOut.setContext("/addReviewResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getNewPlaylistId":
                    playlistController = new PlaylistController();

                    int newId = playlistController.getNewPlaylistId();

                    messageOut.setContext("/getNewPlaylistIdResponse");
                    session.put("NewId", newId);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/addPlaylist":
                    playlistController = new PlaylistController();
                    playlist = (Playlist) session.get("Playlist");
                    path = (String) session.get("Path");
                    customer = (Customer) session.get("Customer");

                    playlistController.addPlaylist(customer, playlist, session, new File(path));

                    messageOut.setContext("/addPlaylistResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/addPlaylistPelicula":
                    playlistController = new PlaylistController();
                    playlistPelicula = (PlaylistPelicula) session.get("PlaylistPelicula");

                    playlistController.addPlaylistPelicula(playlistPelicula, session);

                    messageOut.setContext("/addPlaylistPeliculaResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/borrarPlaylist":
                    playlistController = new PlaylistController();
                    playlist = (Playlist) session.get("Playlist");

                    playlistController.borrarPlaylist(playlist);

                    messageOut.setContext("/borrarPlaylistResponse");
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                case "/getContra":
                    customerControler = new CustomerControler();
                    String correo = (String) session.get("Correo");

                    String contra = customerControler.getContra(correo);

                    messageOut.setContext("/getContraResponse");
                    session.put("Contra", contra);
                    messageOut.setSession(session);
                    objectOutputStream.writeObject(messageOut);
                    break;
                default:
                    System.out.println("Parametro no encontrado");
                    break;
            }

        } catch (IOException ex) {
            System.out.println("Unable to get streams from client");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting server");
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            while (true) {
                /**
                 * create a new {@link SocketServer} object for each connection
                 * this will allow multiple client connections
                 */
                new SocketServer(server.accept());
            }
        } catch (IOException ex) {
            System.out.println("Unable to start server.");
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
