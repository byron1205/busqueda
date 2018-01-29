/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.busqueda;

import ec.edu.espe.distribuidas.busqueda.model.Persona;
import ec.edu.espe.distribuidas.busqueda.model.PersonaDAO;
import ec.edu.espe.distribuidas.busqueda.view.frmBusqueda;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
//        dao.conectarMysql();
//        dao.conectarRedis();
//        dao.conectarMongo();
//
//        dao.insertarBases();
//        
//        dao.cerrarMysql();
//        dao.cerrarRedis();

    }

}
