/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.busqueda.model;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.clients.jedis.Jedis;
import org.bson.Document;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author Edward
 */
public class PersonaDAO {

    ConexionMysql obj;
    Connection con;

    ConexionRedis objRedis;
    Jedis conRedis;

    private MongoClient mongoClient;
    private MongoDatabase mongodb;

    public void conectarMysql() {
        this.obj = ConexionMysql.getInstancia();
        this.con = obj.conectar();
    }

    public void conectarRedis() {
        this.objRedis = new ConexionRedis();
        this.conRedis = this.objRedis.getDirectConnection();
        System.out.println("Server is running: " + conRedis.ping());
    }

    public void conectarMongo() {
        this.mongoClient = new MongoClient();
        this.mongodb = mongoClient.getDatabase("personas");
        
    }

    public void cerrarRedis() {
        this.conRedis.close();
    }

    public void cerrarMysql() {
        try {
            this.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leerArchivo(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        String[] persona;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        Persona objPersona = new Persona();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        while ((cadena = b.readLine()) != null) {
            persona = cadena.split("\\|");
            objPersona.setCedula(persona[0]);
            objPersona.setNombre(persona[1]);
            objPersona.setApellido(persona[2]);
            try {
                objPersona.setFechaNacimiento(format.parse(persona[3]));
            } catch (ParseException ex) {
                Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(objPersona.toString());
            //insertarPersonaMysql(objPersona);
            //insertarPersonaRedis(objPersona);
            insertarPersonaMongo(objPersona);
        }
        b.close();
    }

    public void insertarPersonaMysql(Persona objPer) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO PERSONA(CEDULA,NOMBRE,APELLIDO,FECHA) VALUES (?,?,?,?);");

            ps.setString(1, String.valueOf(objPer.getCedula()));
            ps.setString(2, String.valueOf(objPer.getNombre()));
            ps.setString(3, String.valueOf(objPer.getApellido()));
            ps.setString(4, String.valueOf(objPer.getFechaNacimiento()));
            ps.executeUpdate();
            ps.close();
            //System.out.println("Conexion Cerrada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("No registrados");
        }
    }

    public void insertarPersonaRedis(Persona objPers) {

        this.conRedis.set(objPers.getCedula(), objPers.getNombre() + "|" + objPers.getApellido() + "|" + objPers.getFechaNacimiento());
    }

    public void insertarPersonaMongo(Persona objPer) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
       
        this.mongodb.getCollection("persona").insertOne(
                    new Document("cedula", objPer.getCedula())
                            .append("nombre", objPer.getNombre())
                            .append("apellido", objPer.getApellido())
                            .append("fecha", objPer.getFechaNacimiento()));
    }

    public void buscarPersonaMySql(String cedula) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Persona estPer = null;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM persona WHERE CEDULA='" + cedula + "';");
            estPer = new Persona();
            while (rs.next()) {
                estPer.setCedula(rs.getString(1));
                estPer.setNombre(rs.getString(2));
                estPer.setApellido(rs.getString(3));
                estPer.setFechaNacimiento(format.parse(rs.getString(4)));
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(estPer.toString());
    }

    public void buscarPersonaRedis(String cedula) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String cadena = this.conRedis.get(cedula);
        String[] persona = cadena.split("\\|");
        Persona objPersona = new Persona();
        objPersona.setCedula(cedula);
        objPersona.setNombre(persona[0]);
        objPersona.setApellido(persona[1]);
        try {
            objPersona.setFechaNacimiento(format.parse(persona[2]));
        } catch (ParseException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(objPersona.toString());
    }
    
    public void buscarPersonaMongo(String cedula){
        FindIterable<Document> iterable = this.mongodb.getCollection("persona").find(new Document("cedula", cedula));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        this.mongoClient.close();
    }
}
