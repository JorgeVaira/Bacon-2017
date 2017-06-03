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

public class Cerda {
    
    private String caravan_num;
    private int group_num;
    private int food_type;
    private String start_date;
    private String end_date;
    private String observation;
    private Connection connection;
    
    public static void llenar_informacion( ObservableList<Cerda> lista){
     /*   DB_Connection DB_con=DB_Connection.getInstance();
        Connection con=DB_con.getConnection();
        String sql="SELECT * FROM cerda";
        DB_Query q=new DB_Query();
        ResultSet rs=q.query(con, sql);
        try{
            try {
             if(rs!=null){   
            ResultSetMetaData rsMd = rs.getMetaData();
        //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
             

        //Creando las filas para el JTable
           rs.first();
          
           while(!rs.isAfterLast()){
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i]=rs.getObject(i+1);
                    System.out.println(fila[i]);
                   lista.add(new Cerda(Integer.parseInt(rs.getString("caravana")),Integer.parseInt(rs.getString("nro_grupo")),rs.getString("fecha_inicio"),rs.getString("alimento_tipo"),rs.getString("observaciones")));
            }
                lista.add(new Cerda(Integer.parseInt((String) fila[0]),Integer.parseInt((String) fila[1]),(String) fila[3],(String) fila[4],(String) fila[5]));
           }
                
                 
                rs.next();
            
             }
            } catch (Exception ex) {
    
            System.out.println("Nose pudo crear la tabla en utilidad: "+ex.getMessage());
            
            }
        
        rs.first();
        rs.first();
            while(rs.next()){*/
               // lista.add(new Cerda(Integer.parseInt(rs.getString("caravana")),Integer.parseInt(rs.getString("nro_grupo")),rs.getString("fecha_inicio"),rs.getString("alimento_tipo"),rs.getString("observaciones")));
     /*       }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }*/
    }
    
    public void setCaravan_num(String num_caravan) {
        this.caravan_num = caravan_num;
    }

    public void setGroup_num(String group) {
        this.group_num = group_num;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setFood_type(int food) {
        this.food_type = food;
    }

    public void setObservations(String observations) {
        this.observation = observations;
    }
    /*public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }*/

    /*public Date getEnd_date() {
        return end_date;
    }*/

    public String getCaravan_num() {
        return caravan_num;
    }

    public int getGroup_num() {
        return group_num;
    }

    public String getStart_date() {
        return start_date;
    }
    
    public String getEnd_date() {
        return end_date;
    }

    public int getFood_type() {
        return food_type;
    }

    public String getObservations() {
        return observation;
    }
    
    public Cerda(String caravan_num, int group_num, String start_date, int food, String observations) {
        this.caravan_num = caravan_num;
        this.group_num = group_num;
        this.start_date = start_date;
        this.food_type = food;
        this.observation = observations;   
    }
    
    public static int getID(int caravana){
        int result = -1;
        
        Connection connection = DB_Connection.getInstance().getConnection();
        try{
            String auxSql = "SELECT id_cerda FROM cerda WHERE caravana = " + caravana + ";";
        
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(auxSql);
            
            if(res.first()) result = res.getInt("id_cerda");
        }catch(Exception sql_exce){
            sql_exce.printStackTrace();
	} 
        
        return result;
    }
    
    public void save_Cerda(){
        Connection connection = null;
        PreparedStatement stmt;
        try{
            DB_Connection bd = DB_Connection.getInstance();
            connection = bd.getConnection();
            //nuevos cambios!! para poder cargar y funciona!!! pd: ivan te rompi todo el codigo :)
            stmt = connection.prepareStatement("INSERT INTO cerda (caravana,fecha_inicio,fecha_fin,nro_grupo,observaciones,alimento_tipo) VALUES (?,?,?,?,?,?)");
            stmt.setString(1,caravan_num);
            stmt.setString(2,start_date);
           //lo paso a date y le sumo los dias localdatetime
            //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
            //Date fecha= formatoDelTexto.parse(start_date);
            //String fecha2= formatoDeFecha.parse(fecha);
            //LocalDateTime date = LocalDateTime.parse(start_date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            stmt.setString(3,null);
            stmt.setInt(4,group_num);
            stmt.setString(5,observation);
            stmt.setInt(6,food_type);
            DB_Insert.insert(connection,stmt);
        }catch(Exception sql_exce){
            sql_exce.printStackTrace();
        }
    }
     public Date sumarRestarDiasFecha(Date fecha, int dias){	
      Calendar calendar = Calendar.getInstance();	
      calendar.setTime(fecha); // Configuramos la fecha que se recibe	
      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días	
      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadido 
    }
    public java.util.Date deStringToDate(String fecha){ 
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd"); 
        Date fechaEnviar=null; 
        try { 
        fechaEnviar = formatoDelTexto.parse(fecha); 
        return fechaEnviar; 
        } 
        catch (ParseException ex) { 
        ex.printStackTrace(); 
        return null; 
        } 
    }
    
    public static void update_Cerda(Connection con,String oldcaravan,String newcaravan,int group_num, String start_date,String end_date,int food,String observations) throws SQLException{
        String sql="UPDATE cerda SET caravana=?, fecha_inicio=?, fecha_fin=?, nro_grupo=?, observaciones=?, alimento_tipo=? WHERE caravana='"+oldcaravan+"'";
        try{
            PreparedStatement stmt;
            stmt=con.prepareStatement(sql);
            stmt.setString(1,newcaravan);
            stmt.setString(2,start_date);
            stmt.setString(3,end_date);
            stmt.setInt(4, group_num);
            stmt.setString(5,observations);
            stmt.setInt(6, food);
            DB_Insert.insert(con, stmt);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
