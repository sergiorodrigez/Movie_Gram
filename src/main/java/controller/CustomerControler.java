package main.java.controller;


import main.java.dao.CustomerDAO;
import main.java.domain.Customer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerControler {

    public void getCustomers(ArrayList<Customer> lista) {
        CustomerDAO.getClientes(lista);
    }

    public Customer getCustomer(Customer customer) {
        return(CustomerDAO.getCustomer(customer));
    }

    public String getContra(String correo){
        return CustomerDAO.getContra(correo);
    }

    public void searchCustomer(ArrayList<Customer> customersFound, String texto){
        ArrayList<Customer> customers = new ArrayList<>();
        getCustomers(customers);

        customers.forEach(c -> {
            if (c.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                customersFound.add(c);
            }
        });
    }

    public void addCustomer(Customer customer, File file, HashMap<String, Object> session) {
        ArrayList<Customer> customers = new ArrayList<>();
        getCustomers(customers);

        ArrayList<String> nombreCustomers = new ArrayList<>();
        customers.forEach(c -> nombreCustomers.add(c.getNombre()));
        if (nombreCustomers.contains(customer.getNombre())){
            session.put("UsuarioExistente", true);
        } else {
            session.put("UsuarioExistente", false);
            CustomerDAO.addCustomer(customer, file);
        }
    }

    public void modificarImagen(Customer customer, File file){
        CustomerDAO.modificarImagen(customer, file);
    }

    public void modificarNombre(Customer customer, String nuevoNombre){
        CustomerDAO.modificarNombre(customer, nuevoNombre);
    }
}
