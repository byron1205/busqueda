/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.busqueda;

import ec.edu.espe.distribuidas.busqueda.model.Persona;
import ec.edu.espe.distribuidas.busqueda.model.PersonaDAO;
import ec.edu.espe.distribuidas.busqueda.view.frmBusqueda;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edward
 */
public class Principal {

    private static final Logger LOG = Logger.getLogger(Principal.class.getName());

    public static void main(String[] args) {

        frmBusqueda frm = new frmBusqueda();
        frm.setVisible(true);
        
//        PersonaDAO dao = new PersonaDAO();
//        dao.conectarMongo();
//        try {
//            dao.leerArchivo("final.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

}
