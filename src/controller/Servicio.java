package controller;
import java.sql.*;
import javafx.collections.ObservableList;
import model.*;

public class Servicio{
	private int caravan_mother;
	private int caravan_father;
	private String service_date;
	private String observation;
	private Connection connection;

	//******************************Constructor*****************************//
	public Servicio(int caravan_mother, int caravan_father, String service_date, String observations){
		this.caravan_mother=caravan_mother;
		this.caravan_father=caravan_father;
		this.service_date=service_date;
		this.observation=observations;
	}
	//*****************************Guardar el Servicio*************************************//
	public void saveService(){
		Connection con=null;
		PreparedStatement stmt;
		try{
			DB_Connection bd = DB_Connection.getInstance();
			con = bd.getConnection();
			stmt = con.prepareStatement("INSERT INTO servicio (id_cerda,id_padrillo,fecha_servicio,observaciones) VALUES (?,?,?,?)");
			stmt.setInt(1,caravan_mother);
			stmt.setInt(2,caravan_father);
			stmt.setString(3,service_date);
			stmt.setString(4,observation);
                        DB_Insert.insert(con,stmt);
		}
		catch(Exception sql_exce){
			sql_exce.printStackTrace();
		}
	}
        
        public static void update_Service(int old_mother, int old_father, String old_date, int new_mother, int new_father, String new_date, String observations) throws SQLException{
            String sql="UPDATE servicio SET id_cerda=?, id_padrillo=?, fecha_servicio=?, observaciones=? "+ "WHERE id_cerda='"+old_mother+"' AND id_padrillo='"+old_father+"' AND fecha_servicio='"+old_date+"'";
            PreparedStatement stmt;
            DB_Connection db=DB_Connection.getInstance();
            Connection con=db.getConnection();
            try{
                stmt=con.prepareStatement(sql);
                stmt.setInt(1,new_mother);
                stmt.setInt(2,new_father);
                stmt.setString(3,new_date);
                stmt.setString(4,observations);
                DB_Insert.insert(con,stmt);   
            }catch(Exception e){
                e.printStackTrace();
            }
        }
	//*********************************Set y Get de los atributos*********************************//
	public void setCaravan_mother(String caravana_mother){
		this.caravan_mother=caravan_mother;
	}
	public void setCaravan_father(String caravana_father){
		this.caravan_father=caravan_father;
	}
	public void setServiceDate(String service_date){
		this.service_date=service_date;
	}
	public void setObservations(String observations){
		this.observation=observations;
	}

	public int getCaravan_mother(){
		return caravan_mother;
	}
	public int getCaravan_father(){
		return caravan_father;
	}
	public String getServiceDate(){
		return service_date;
	}
	public String getObservations(){
		return observation;
	}
}