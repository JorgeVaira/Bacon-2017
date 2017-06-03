package controller;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.collections.ObservableList;
import model.*;
import java.util.Date;

public class Parto{
	private int caravan;
	private String birth_date;
	private String pre_date;
	private int alive_piglet;
	private int dead_piglet;
	private String observation;
	private Connection connection;

	//********************************Constructor****************************//
	public Parto(int caravan,String birth_date,String pre_date,int alive_piglet,int dead_piglet,String observations){
		this.caravan=caravan;
		this.birth_date=birth_date;
		this.pre_date=pre_date;
		this.alive_piglet=alive_piglet;
		this.dead_piglet=dead_piglet;
		this.observation=observations;
	}
	//*********************************Guardar el Parto**************************//
	public void saveParto(){
		PreparedStatement stmt;
		try{
			DB_Connection db=DB_Connection.getInstance();
			connection=db.getConnection();
			stmt=connection.prepareStatement("INSERT INTO parto(id_cerda,fecha_parto,fecha_paso_lactancia,cantidad_lechones,cantidad_muertos,observaciones) VALUES(?,?,?,?,?,?)");
			stmt.setInt(1,caravan);
			stmt.setString(2,birth_date);

			SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha=formatoTexto.parse(birth_date);

			stmt.setString(3,formatoTexto.format(restarDias(fecha,-25)));
			stmt.setInt(4,alive_piglet);
			stmt.setInt(5,dead_piglet);
			stmt.setString(6,observation);

			DB_Insert.insert(connection,stmt);
		}
		catch(Exception sql_exce){
			sql_exce.printStackTrace();
		}
                
                
	}
        //*************************Actualizar El Parto******************************//
        public static void update_Parto(int old_id, String old_date, String new_date, String pre_date, int alive, int dead, String observations){
            DB_Connection db=DB_Connection.getInstance();
            Connection con=db.getConnection();
            String sql="UPDATE parto SET fecha_parto=?, fecha_paso_lactancia=?, cantidad_lechones=?, cantidad_muertos=?, observaciones=?"+ "WHERE id_cerda='"+old_id+"' AND fecha_parto='"+old_date+"'";
            try{
                PreparedStatement stmt=con.prepareStatement(sql);
                stmt.setString(1, new_date);
                stmt.setString(2,pre_date);
                stmt.setInt(3, alive);
                stmt.setInt(4,dead);
                stmt.setString(5, observations);
                DB_Insert.insert(con, stmt);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
	//*********************************Set y Get de los atributos**************************//
	public void setCaravan(String caravana){
		this.caravan=caravan;
	}
	public void setBirthDate(String birth_date){
		this.birth_date=birth_date;
	}
	public void setPreDate(String pre_date){
		this.pre_date=pre_date;
	}
	public void setAlivePiglet(String alive_piglett){
		this.alive_piglet=alive_piglet;
	}
	public void setDeadPiglet(String dead_piglett){
		this.dead_piglet=dead_piglet;
	}
	public void setObservations(String observations){
		this.observation=observations;
	}
	public int getCaravan(){
		return caravan;
	}
	public String getBirthDate(){
		return birth_date;
	}
	public String getPreDate(){
		return pre_date;
	}
	public int getAlivePiglet(){
		return alive_piglet;
	}
	public int getDeadPiglet(){
		return dead_piglet;
	}
	public String getObservations(){
		return observation;
	}

	public Date restarDias(Date fecha, int dias){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR,dias);//Aunque uso el mismo código que franco, le paso valor negativo
		return calendar.getTime();
	}

	public java.util.Date deStringToDate(String fecha){
		SimpleDateFormat formatoTexto=new SimpleDateFormat("yyyy-MM-dd");
		Date fechaEnviar=null;
		try{
			fechaEnviar=formatoTexto.parse(fecha);
			return fechaEnviar;
		}
		catch(ParseException ex){
			ex.printStackTrace();
			return null;
		}
	}
}