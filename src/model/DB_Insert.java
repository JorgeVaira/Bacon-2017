package model;
import java. sql.*;

public class DB_Insert{
	
    public static void insert(Connection connection,PreparedStatement stmt){ 
        try{
            stmt.executeUpdate(); //Actualiza la base de datos con el sql cargado
        }
        catch(java.sql.SQLException e){
            System.out.println(e+"Hola, soy JOrge");
        }
    }
}

