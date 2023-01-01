package main.java.domain;

import java.io.Serializable;

public class Review implements Serializable {

    private String user;
    private int id_p;
    private String coment;
    private int val;

    public Review(String user, int id_p, String coment, int val){
        this.id_p = id_p;
        this.user = user;
        this.coment = coment;
        this.val = val;
    }

    public String getUser(){return user;}

    public int getId_p(){return id_p;}

    public int getVal(){
        return val;
    }

    public String getComent() {
        return coment;
    }

    @Override
    public String toString(){
        return "---REVIEW---\nUsuario: " + user +
                "\nID: " + id_p +
                "\nComentario: " + coment +
                "\nValoracion: " + val;
    }
}
