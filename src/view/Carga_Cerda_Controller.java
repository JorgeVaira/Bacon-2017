package view;

import javafx.stage.Stage;
import controller.Cerda;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.DB_Connection;
import model.DB_Query;

public class Carga_Cerda_Controller implements Initializable {
    
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
    private TextField caravana, grupo;
    
    @FXML
    private TextArea observaciones;
    
    @FXML
    private DatePicker fecha_ingreso;
    
    @FXML
    private void cargar(ActionEvent event) throws SQLException {
        //con lo que hay adentro del constructor es lo que extraemos de los campos del formulario de carga de Cerda, si quieren probarlo, yo ya lo hice y funciona
        //Nota por favor no tocar los archivos FXML, ni los controllers que haya en la carpeta vista
        
        //Aca uso los array para checkear y guardar el id del alimento en vez del nombre.
        lista_id_Alimento=javafx.collections.FXCollections.observableArrayList(); 
        lista_tipo_Alimento=javafx.collections.FXCollections.observableArrayList();
        
        String tipo_alimento = (String)alimento.getValue();
        
        int id_Alimento = 0;
        String date= null;

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
                    id_Alimento = Integer.parseInt((String)lista_id_Alimento.get(i));
                }
            }
        }
        
        int group;

        String caravan= caravana.getText();
        
        //PARA CONTROLAR ERRORES.
        Alert alert_error = new Alert(AlertType.WARNING);
        alert_error.setTitle("Error");
        alert_error.setHeaderText("Modifique los campos indicados.");
        
        String error_grupo="";
        String error_caravana="";
        String error_alimento="";
        String error_fecha="";
        
        //FECHA
        if(fecha_ingreso.getValue()!= null){
            date=fecha_ingreso.getValue().toString();            
        }else{
            error_fecha = "DEBE INGRESAR UNA FECHA";
        }
        
        //GRUPO
        Pattern pat1 = Pattern.compile("[a-zA-Z]+");
        Matcher mat1 = pat1.matcher(grupo.getText());
        if(grupo.getText().isEmpty() || mat1.matches()){
                group=0; 
            }else{
                group= Integer.parseInt(grupo.getText());
            }
                    
        if (group<=0 || group>4){
            error_grupo="EL GRUPO DEBE SER 4 Y NO PUEDE CONTENER LETRAS";
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

        if(error_alimento!="" || error_caravana!="" || error_grupo!="" || error_fecha!=""){
            alert_error.setContentText(""+error_caravana+"\n"+error_grupo+"\n"+error_alimento+"\n"+error_fecha);
            alert_error.showAndWait();
            
            //Para que limpie solo el que tiene error.
            if(error_alimento!=""){
                alimento.setValue("");    
            }
            if(error_caravana!=""){
                caravana.clear();
            }
            if(error_grupo!=""){
                grupo.clear();
            }
        }else{
            
        //Cargda realizada 
        Cerda puerca = new Cerda(caravan,group,date,id_Alimento,observaciones.getText());
        puerca.save_Cerda();
        
        Alert alert_exito = new Alert(AlertType.INFORMATION);
        alert_exito.setTitle("");
        alert_exito.setHeaderText(null);
        alert_exito.setContentText("CARGA DE DATOS EXITOSA");

        alert_exito.showAndWait();
        
        caravana.clear();
        grupo.clear();
        alimento.setValue("");
        fecha_ingreso.setValue(LocalDate.now());
        observaciones.clear();
        
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
//      Pdf pdf = new Pdf();
//      pdf.generarPdf();
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
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