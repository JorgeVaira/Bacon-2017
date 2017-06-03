package view;

import controller.Padrillo;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DB_Connection;
import model.DB_Query;

public class Carga_Padrillo_Controller implements Initializable {
    
    private DB_Connection db=null;
    private Connection con=null;
    private DB_Query dbq=null;
    private String sql1="SELECT id_alimento, tipo FROM alimento ORDER BY id_alimento";
    private ResultSet rs1=null;
    private ObservableList<String> lista_tipo_Alimento;
    private ObservableList<String> lista_id_Alimento;
    
    @FXML
    private ComboBox alimento;
    
    @FXML
    private Button cancelar;
    
    @FXML
    private TextField caravana;
        
    @FXML
    private TextArea observaciones;
    
    @FXML
    private DatePicker fecha_ingreso;
    
    
    @FXML
    private void cargar(ActionEvent event) throws SQLException {
        //con lo que hay adentro del constructor es lo que extraemos de los campos del formulario de carga de Cerdas, si quieren probarlo, yo ya lo hice y funciona
        //hagan System.out.print(caravana.getText());
        //Nota por favor no tocar los archivos FXML, ni los controllers que haya en la carpeta vista
         
        //Aca uso los array para checkear y guardar el id del alimento en vez del nombre.
        lista_id_Alimento=javafx.collections.FXCollections.observableArrayList(); 
        lista_tipo_Alimento=javafx.collections.FXCollections.observableArrayList();
        String tipo_alimento = (String)alimento.getValue();
        int id_alimento = 0;
        String date= null;
                
        String error_caravana="";
        String error_alimento="";
        String error_fecha="";
        
        
	db=DB_Connection.getInstance();
	con=db.getConnection();
	dbq=new DB_Query();
	rs1=dbq.query(con,sql1);
	rs1.beforeFirst();
        while(rs1.next()){
            lista_id_Alimento.add(rs1.getString("id_alimento"));
            lista_tipo_Alimento.add(rs1.getString("tipo"));
        }
        for(int i=0; i < lista_id_Alimento.size(); i++){
            if(tipo_alimento!=null){
                if (tipo_alimento.equals(lista_tipo_Alimento.get(i))){
                    id_alimento = Integer.parseInt((String)lista_id_Alimento.get(i));
                }
            }
        }
        
        String caravan= caravana.getText();
        
        
        //PARA CONTROLAR ERRORES.
        Alert alert_error = new Alert(Alert.AlertType.WARNING);
        alert_error.setTitle("Error");
        alert_error.setHeaderText("Modifique los campos indicados.");

        //FECHA
        if(fecha_ingreso.getValue()!= null){
            date=fecha_ingreso.getValue().toString();            
        }else{
            error_fecha = "DEBE INGRESAR UNA FECHA";
        }
        
        //CARAVANA
        Pattern pat2 = Pattern.compile("[a-zA-Z0-9]+");
        Matcher mat2 = pat2.matcher(caravan);
            if (mat2.matches()) {
                System.out.println("SI");
            } else {
                error_caravana="CARAVANA POSEE ERROR";   
            }
        //ALIMENTO
        if(tipo_alimento == null){
            error_alimento="NO SE HA SELECCIONADO NINGUN ALIMENTO";
        }
        
        if(error_caravana!="" || error_alimento!="" || error_fecha!=""){
            alert_error.setContentText(""+error_caravana+"\n"+error_alimento+"\n"+error_fecha);
            alert_error.showAndWait();
            
            //Para que limpie solo el que tiene error.
            if(error_alimento!=""){
                alimento.setValue("");
                tipo_alimento=null;
            }
            if(error_caravana!=""){
                caravana.clear();
            }
        }else{
            
            Padrillo puerco = new Padrillo(caravan,date,id_alimento,observaciones.getText());
            puerco.save_Padrillo();
        
            //Cargda realizada 
            Alert alert_exito = new Alert(Alert.AlertType.INFORMATION);
            alert_exito.setTitle("");
            alert_exito.setHeaderText(null);
            alert_exito.setContentText("CARGA DE DATOS EXITOSA");

            alert_exito.showAndWait();
        
            //Vuelvo a los valores por defecto para volver a cargar.
            caravana.clear();
            alimento.setValue("");
            tipo_alimento=null;
            fecha_ingreso.setValue(LocalDate.now());
            observaciones.clear();
        }
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
    
    @FXML
    private void loadComboBox1()throws SQLException{
 
        lista_tipo_Alimento=javafx.collections.FXCollections.observableArrayList();
        lista_id_Alimento=javafx.collections.FXCollections.observableArrayList();
	db=DB_Connection.getInstance();
	con=db.getConnection();
	dbq=new DB_Query();
	rs1=dbq.query(con,sql1);
	rs1.beforeFirst();
        while(rs1.next()){
            lista_tipo_Alimento.add(rs1.getString("tipo"));
            lista_id_Alimento.add(rs1.getString("id_alimento"));
        }
        alimento.setItems(lista_tipo_Alimento);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            loadComboBox1();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }     
}