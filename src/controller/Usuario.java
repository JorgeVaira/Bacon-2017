/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.DB_Connection;
import model.DB_Insert;

/**
 *
 * @author Vallejin94
 */
public class Usuario {
    private int id_usuario;
    private String apellido;
    private String nombre;
    private String mail;
    private String contrasenia;
    private String telefono;
    private String documento;
    private String cargo;
    
    public Usuario(String apellido,String nombre,String mail,String constrasenia,String telefono,String documento,String cargo){
        this.apellido=apellido;
        this.nombre=nombre;
        this.mail=mail;
        this.contrasenia=constrasenia;
        this.telefono=telefono;
        this.documento=documento;
        this.cargo=cargo;
    }
    
    public void setApellido(String apellido){
        this.apellido=apellido;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setMail(String mail){
        this.mail=mail;
    }
    public void setContrasenia(String contrasenia){
        this.contrasenia=contrasenia;
    }
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }
    public void setDocumento(String documento){
        this.documento=documento;
    }
    public void setCargo(String cargo){
        this.cargo=cargo;
    }
    public String getApellido(){
        return this.apellido;
    }
    public String getNombre(){
       return this.nombre;
    }
    public String getMail(){
       return this.mail;
    }
    public String getContrasenia(){
       return this.contrasenia;
    }
    public String getTelefono(){
       return this.telefono;
    }
    public String getDocumento(){
       return this.documento;
    }
    public String getCargo(){
       return this.cargo;
    }
    
    public void save_Usuario(){
        Connection connection = null;
        PreparedStatement stmt;
        try{
            DB_Connection bd = DB_Connection.getInstance();
            connection = bd.getConnection();
             stmt = connection.prepareStatement("INSERT INTO usuario (apellido,nombre,mail,constrasenia,telefono,documento,cargo) VALUES ('"+this.apellido+"','"+this.nombre+"','"+this.mail+"','"+this.contrasenia+"','"+this.telefono+"','"+this.documento+"','"+this.cargo+"');");    
           System.out.println(">>>>>"+stmt); 
           DB_Insert.insert(connection,stmt);
	} catch(Exception sql_exce){
            sql_exce.printStackTrace();
	}
    }
    
    
}
