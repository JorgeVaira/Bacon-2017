package controller;

import java.util.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;

public class Padrillo {

    private String caravan_num;
    private int food_type;
    private String start_date;
    private String end_date;
    private String observation;
    private Connection connection;
    
    public void setCaravan_num(int num_caravan) {
        this.caravan_num = caravan_num;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setFood_tipe(int food_type) {
        this.food_type = food_type;
    }

    public void setObservations(String observations) {
        this.observation = observations;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getCaravan_num() {
        return caravan_num;
    }

    public String getStart_date() {
        return start_date;
    }

    public int getFood_tipe() {
        return food_type;
    }

    public String getObservations() {
        return observation;
    }
    
    public Padrillo(String caravan_num, String start_date, int food, String observations) {
        
        this.caravan_num = caravan_num;
        this.start_date = start_date;
        this.food_type = food;
        this.observation = observations;       
    }
    
    public void save_Padrillo(){
        
        Connection connection = null;
        PreparedStatement stmt;
        try{
            DB_Connection bd = DB_Connection.getInstance();
            connection = bd.getConnection();
            //nuevos cambios!! para poder cargar y funciona!!! pd: ivan te rompi todo el codigo :)
            
            stmt = connection.prepareStatement("INSERT INTO padrillo (caravana,fecha_inicio,fecha_fin,observaciones,alimento_tipo) VALUES (?,?,?,?,?);");
            stmt.setString(1,caravan_num);
            stmt.setString(2,start_date);
            stmt.setString(3,null);
            stmt.setString(4,observation);
            stmt.setInt(5,food_type);
            DB_Insert.insert(connection,stmt);
            
	} catch(Exception sql_exce){
            sql_exce.printStackTrace();
	}
    }

    public static void update_Padrillo(Connection con,String oldcaravan,String newcaravan,String start_date,String end_date,String observations){
        String sql="UPDATE padrillo SET caravana=?, fecha_inicio=?, fecha_fin=?, observaciones=? WHERE caravana='"+oldcaravan+"'";
        try{
            PreparedStatement stmt;
            stmt=con.prepareStatement(sql);
            stmt.setString(1,newcaravan);
            stmt.setString(2,start_date);
            stmt.setString(3,end_date);
            stmt.setString(4,observations);
            DB_Insert.insert(con,stmt);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
