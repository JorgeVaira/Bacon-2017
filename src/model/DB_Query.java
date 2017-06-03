package model;

import java. sql.*;

public class DB_Query {

    Statement stmt = null;
	
    public ResultSet query(Connection connection,String sql){
        ResultSet answer = null;
        try{
            stmt = connection.createStatement();
	    answer = stmt.executeQuery(sql);
        }
	catch(java.sql.SQLException e){
            System.out.println("No se pudo realizar la consulta");
            System.out.println(e);
        }
        return answer;
    }
}

