package model;

import java. sql.*;

public class DB_Connection{
	
    private static Connection connection;
    private static DB_Connection instance;
    private static String host = "jdbc:mysql://127.0.0.1/mydb";
    private static String username = "root";
    private static String password = "";
    
    private DB_Connection(){}
    
    public static DB_Connection getInstance(){
        
        if(instance == null){
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            }
            catch(Exception e){
                System.err.println("No se pudo realizar la Conexión");
            }
            try{
                instance = new DB_Connection();
                connection = DriverManager.getConnection(host,username,password);
            }
            catch(java.sql.SQLException e)
            {
                System.out.println("No se puede conectar con la base de Datos");
            }
        }
        return instance;
    }
    
    public void close_connection(){
        try
        {
        connection.close();
        }
        catch(java.sql.SQLException e)
        {
            System.out.println("No se pudo cerrar correctamente la base de datos");
        }
    }
    
    public Connection getConnection(){
       Connection con=null;
        if(instance != null){
           con = this.connection;
           
       }
       else{
           System.out.println("No existe instancia!");
       }
        DB_Create_Table.create(con); //nuevo
       return con;
    }
}

