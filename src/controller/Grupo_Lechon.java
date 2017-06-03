package controller;

import java.sql.*;
import java.text.SimpleDateFormat;
import javafx.collections.ObservableList;
import model.*;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Grupo_Lechon {
    
    private String weaning_date;
    private String mother_caravan_num;
    private int number;
    private int initial_weight;
    private String food_type;
    private String observation;
    private static Connection connection = DB_Connection.getInstance().getConnection();
    

    //Builder
    public Grupo_Lechon(){}
    
    public Grupo_Lechon(String mother_caravan_num, String weaning_date) {
        this.mother_caravan_num = mother_caravan_num;
        this.weaning_date = weaning_date;
    }
    
    public Grupo_Lechon(String mother_caravan_num, String weaning_date, int number, int initial_weight, String food_type, String observation) {
        this.mother_caravan_num = mother_caravan_num;
        this.weaning_date = weaning_date;
        this.number = number;
        this.initial_weight = initial_weight;
        this.food_type = food_type;
        this.observation = observation;
    }
    
    public static ObservableList<String> getMotherCaravans(){
        String auxSql = "SELECT DISTINCT id_caravana_madre FROM grupo_lechon WHERE peso_inicial IS NULL;";
        String auxSql1 = "SELECT caravana FROM cerda WHERE id_cerda = ";
        ResultSet caravan, caravansId;
        ObservableList<String> lista_caravana = javafx.collections.FXCollections.observableArrayList();;
        
        try{
            Statement stmt = connection.createStatement(),stmt1 = connection.createStatement();
            caravansId = stmt.executeQuery(auxSql);
            
            caravansId.beforeFirst();
            while(caravansId.next()){
                caravan = stmt1.executeQuery(auxSql1 + caravansId.getInt("id_caravana_madre"));
                caravan.first();
                lista_caravana.add(String.valueOf(caravan.getInt("caravana")));
            }
            
        } catch(Exception sql_exce){
            sql_exce.printStackTrace();
	}
        
        return(lista_caravana);
    }
    
    public static ObservableList<String> getWeaningDates(String caravan){
        String auxSql = "SELECT DISTINCT fecha_destete FROM grupo_lechon WHERE peso_inicial IS NULL AND " +
        "id_caravana_madre = " + Cerda.getID(Integer.parseInt(caravan)) + ";";
        ResultSet weaningDates;
        ObservableList<String> weaningDatesList = javafx.collections.FXCollections.observableArrayList();;
        
        try{
            Statement stmt = connection.createStatement();
            weaningDates = stmt.executeQuery(auxSql);
            
            weaningDates.beforeFirst();
            while(weaningDates.next()) weaningDatesList.add(weaningDates.getString("fecha_destete"));
            
        } catch(Exception sql_exce){
            sql_exce.printStackTrace();
	}
        
        return(weaningDatesList);
    }
    
    public static boolean isGroup(String mother_caravan_num, String weaning_date){
        boolean result = false;
        
        String auxSql = "SELECT id_cerda FROM cerda WHERE fecha_fin IS NULL AND caravana = " + mother_caravan_num;
        String auxSql1 = "SELECT * FROM grupo_lechon WHERE fecha_destete = '" + weaning_date + "'";
        
        try{
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(auxSql);
            
            res.first();
            auxSql1 += "AND id_caravana_madre = " + res.getInt("id_cerda");
            res = stmt.executeQuery(auxSql1);
            
            if(res.first()) result = true;
        }catch(Exception sql_exce){
            sql_exce.printStackTrace();
	} 
        
        return result;
    }
    
    //Setters
    public void setMotherCaravanNum(String mother_caravan_num) {
        this.mother_caravan_num = mother_caravan_num;
    }

    public void setWeaningDate(String weaning_date) {
        this.weaning_date = weaning_date;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public void setInitialWeight(int initial_weight) {
        this.initial_weight = initial_weight;
    }
    
    public void setFoodType(String food_type) {
        this.food_type = food_type;
    }

    public void setObservations(String observations) {
        this.observation = observations;
    }
    
    //Getters
    public String getMotherCaravanNum() {
        return(this.mother_caravan_num);
    }

    public String getWeaningDate() {
        return(this.weaning_date);
    }
    
    public int getNumber() {
        return(this.number);
    }
    
    public int getInitialWeight() {
        return(this.initial_weight);
    }
    
    public String getFoodType() {
        return(this.food_type);
    }
    
    public String getObservations() {
        return(this.observation);
    }
    
    public boolean discharge(){
        boolean cargado = true;
        int id_food = Alimento.getID(food_type);
        int id_mother = Cerda.getID(Integer.parseInt(mother_caravan_num));
        
        if(isGroup(this.mother_caravan_num, this.weaning_date) &&
        Alimento.isFood(food_type)){
            String sql="UPDATE grupo_lechon SET cantidad = ?, peso_inicial = ?, id_alimento = ?, observaciones = ?" +
            " WHERE id_caravana_madre = " + id_mother + " AND fecha_destete = '" + weaning_date + "';";
            try{
                PreparedStatement stmt;
                stmt=connection.prepareStatement(sql);
                stmt.setInt(1,number);
                stmt.setDouble(2,initial_weight);
                stmt.setInt(3,id_food);
                stmt.setString(4,observation);
                DB_Insert.insert(connection,stmt);
            }catch(SQLException e){
                e.printStackTrace();
                cargado = false;
            }
        }
        
        return cargado;
    }
    //This is for saving the information about a group object into the database
    public void saveGroup(){
        
        PreparedStatement stmt;
        try{
            DB_Connection bd = DB_Connection.getInstance();
            connection = bd.getConnection();
           
            stmt = connection.prepareStatement("INSERT INTO grupo_lechon (caravana_madre,fecha_destete) VALUES (?,?)");
            stmt.setInt(1,Integer.parseInt(mother_caravan_num));
            stmt.setString(2,weaning_date);

            DB_Insert.insert(connection,stmt);
            
	} catch(Exception sql_exce){
            sql_exce.printStackTrace();
	}   
    }
}
