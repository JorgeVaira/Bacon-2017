/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import model.DB_Connection;
import model.DB_Insert;

/**
 *
 * @author Vallejin94
 */
public class Sesion {
    private Date fecha;
    private int id_usuario;
    private int tiempo_uso;
    
    public Sesion(Date fecha, int id_usuario,int tiempo_uso){
        this.fecha=fecha;
        this.id_usuario=id_usuario;
        this.tiempo_uso=tiempo_uso;
    }
    
    public void setFecha(Date fecha){
        this.fecha=fecha;
    }
    public void setId_usuario(int id_usuario){
        this.id_usuario=id_usuario;
    }
    public void setTiempo_uso(int tiempo_uso){
        this.tiempo_uso=tiempo_uso;
    }
    public int getId_usuario(){
        return this.id_usuario;
    }
    public int getTiempo_uso(){
        return this.tiempo_uso;
    }
    public Date getFecha(){
       return this.fecha;
    }
    
    public void save_Sesion(){
        Connection connection = null;
        PreparedStatement stmt;
        try{
            DB_Connection bd = DB_Connection.getInstance();
            connection = bd.getConnection();
             stmt = connection.prepareStatement("INSERT INTO Sesion (fecha,id_usuario,tiempo_uso) VALUES ('"+this.fecha+"','"+this.id_usuario+"','"+this.tiempo_uso+"');");    
           System.out.println(">>>>>"+stmt); 
           DB_Insert.insert(connection,stmt);
	} catch(Exception sql_exce){
            sql_exce.printStackTrace();
	}
    }
}
