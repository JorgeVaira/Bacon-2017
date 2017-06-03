package view;

import javafx.stage.Stage;
import controller.Cerda;
import controller.Servicio;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.DB_Connection;
import model.DB_Query;
import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javax.swing.JOptionPane;

public class Carga_Servicio_Controller implements Initializable{

    private DB_Connection db=null;
    private Connection con=null;
    private DB_Query dbq=null;
    private String sql1="SELECT caravana FROM cerda ORDER BY caravana";
    private String sql2="SELECT caravana FROM padrillo ORDER BY caravana";             
    private ResultSet rs1=null;
    private ResultSet rs2=null;
    private ObservableList<String> lista_Cerda;
    private ObservableList<String> lista_Padrillo;
    private ObservableList<Integer> lista;
    
    @FXML
    private DatePicker fecha_servicio;

    @FXML
    private ComboBox cerda;

    @FXML
    private ComboBox padrillo;

    @FXML
    private TextArea observaciones;

    @FXML
    private void cargar(ActionEvent event) throws SQLException{
        lista=javafx.collections.FXCollections.observableArrayList();
        int id_cerda=0, id_padrillo=0;
        String date=null, cad1=null, cad2=null, observation=null;
        
        cad1=(String)cerda.getValue();
        cad2=(String)padrillo.getValue();
        observation=observaciones.getText();
        
        if(fecha_servicio.getValue()!= null){
            date=fecha_servicio.getValue().toString();            
        }
         
        //PARA CONTROLAR ERRORES.
        Alert alert_error = new Alert(Alert.AlertType.WARNING);
        alert_error.setTitle("Error");
        alert_error.setHeaderText("Modifique los campos indicados.");
        
        if(cad1 == null || cad2 == null || date == null){
            //ERROR EN LA CARGA 
            alert_error.setContentText(" TODOS LOS CAMPOS SON OBLIGATORIOS \n (MENOS LAS OBSERVACIONES) \n");
            alert_error.showAndWait();
            
        }else{
        
            String sql="SELECT id_cerda FROM cerda WHERE caravana='"+cad1+"' AND fecha_fin IS NULL";
            db=DB_Connection.getInstance();
            con=db.getConnection();
            dbq=new DB_Query();
            rs1=dbq.query(con, sql);
            rs1.beforeFirst();
            while(rs1.next()){
                lista.add(rs1.getInt("id_cerda"));
            }
            id_cerda=(int)lista.get(0);
        
            sql="SELECT id_padrillo FROM padrillo WHERE caravana='"+cad2+"'";
            rs1=dbq.query(con,sql);
            rs1.beforeFirst();
            while(rs1.next()){
                lista.add(rs1.getInt("id_padrillo"));
            }
            id_padrillo=(int)lista.get(1);
            
            Servicio serv;
            serv = new Servicio(id_cerda,id_padrillo,date,observation);
            serv.saveService();
        
            //CARGA REALIZADA
            Alert alert_exito = new Alert(Alert.AlertType.INFORMATION);
            alert_exito.setTitle("");
            alert_exito.setHeaderText(null);
            alert_exito.setContentText("CARGA DE DATOS EXITOSA");

            alert_exito.showAndWait();
        
            //Vuelvo a los valores por defecto para volver a cargar.
            cerda.setValue("");
            padrillo.setValue("");
            observaciones.clear();
            fecha_servicio.setValue(LocalDate.now());
        }
    }
                    
    @FXML
    private void loadComboBox1()throws SQLException{
        
        lista_Cerda=FXCollections.observableArrayList();
    	//padrillo=new ComboBox();
	db=DB_Connection.getInstance();
	con=db.getConnection();
	dbq=new DB_Query();
	rs1=dbq.query(con,sql1);
	rs1.beforeFirst();
        while(rs1.next()){
            lista_Cerda.add(rs1.getString("caravana"));
        }
        cerda.setItems(lista_Cerda);
    }
        
    @FXML
    private void loadComboBox2()throws SQLException{
        lista_Padrillo=FXCollections.observableArrayList();
        //padrillo=new ComboBox();
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        rs2=dbq.query(con,sql2);
        rs2.beforeFirst();
        while(rs2.next()){
            lista_Padrillo.add(rs2.getString("caravana"));
        }
        padrillo.setItems(lista_Padrillo);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try{
            loadComboBox1();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            loadComboBox2();
        }
        catch(Exception e){
            e.printStackTrace();
        }   
    }
}