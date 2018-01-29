/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.busqueda.model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Edward
 */
public class ConexionMysql {
    private static ConexionMysql instancia;
    Connection con = null;
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busqueda"
                    ,"root","espe");
            System.out.println("Conexion exitosa");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Conexion fallida");
        }
        return con;
    }
    public static ConexionMysql getInstancia()
    {
        if(instancia==null)
        {
            instancia= new ConexionMysql();
        }        
        return instancia;
    }
    
}
