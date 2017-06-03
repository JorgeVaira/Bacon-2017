package view;

import controller.Cerda;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.DB_Connection;
import model.DB_Query;


public class Ver_Estadisticas_Controller implements Initializable{
    private DB_Connection db=null;
    private Connection con=null;
    private DB_Query dbq=null;
    private String sql="SELECT caravana FROM cerda";
    private ResultSet rs=null;
    private ObservableList<Integer> lista;
    @FXML
    private ComboBox cerda;
    @FXML
    private Button cancelar;
    @FXML
    private Button ver;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis Anio;
    @FXML
    private NumberAxis Cantidad;
    
    private ObservableList<String> ol;
    
    private void loadComboBox()throws SQLException{
        lista=javafx.collections.FXCollections.observableArrayList();
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        while(rs.next()){
            lista.add(rs.getInt("caravana"));
        }
        cerda.setItems(lista);
        
        ol = FXCollections.observableArrayList();
        Anio.setLabel("Año");
        Cantidad.setLabel("Cantidad");
   
        Calendar c = Calendar.getInstance();
        int anioActual = c.get(Calendar.YEAR);
        for(int i = 0 ; i < 13 ; i++){
            ol.add(String.valueOf(anioActual - 12 + i));
        }
        Anio.setCategories(ol);
    }
  
   @FXML
   private void Visualizar_Estadisticas()throws SQLException{
       String nro_caravana=cerda.getValue().toString();
       int id_caravana = Cerda.getID(Integer.parseInt(nro_caravana));
       String sql2 = "Select year(fecha_destete) as anio,id_caravana_madre, sum(cantidad) as cantidad from grupo_lechon group by anio, id_caravana_madre having id_caravana_madre = " + id_caravana;
       
       db=DB_Connection.getInstance();
       con=db.getConnection();
       dbq=new DB_Query();
       rs=dbq.query(con,sql2);
         
       XYChart.Series<String,Integer> series = new XYChart.Series<String,Integer>();

       rs.beforeFirst();
       while(rs.next()){
            XYChart.Data<String, Integer> dato = new XYChart.Data<String,Integer>(Integer.toString(rs.getInt("anio")),rs.getInt("cantidad"));
            series.getData().add(dato);
       }
       
       barChart.getData().add(series);
   }
    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) cancelar.getScene().getWindow();
            stage.close();
            
        }catch(Exception e){
            System.out.print(e);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{           
            loadComboBox();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }     
}
