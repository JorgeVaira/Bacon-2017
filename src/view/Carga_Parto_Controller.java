package view;

import javafx.stage.Stage;
import controller.Cerda;
import controller.Parto;
import controller.Servicio;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class Carga_Parto_Controller implements Initializable{
	private DB_Connection db=null;
	private Connection con=null;
	private DB_Query dbq=null;
	private String sql="SELECT caravana FROM cerda ORDER BY caravana";
	private ResultSet rs=null;
        
  private ObservableList<String> lista;
  private ObservableList<Integer> listaux;
                   
  @FXML
  private ComboBox cerda;
  @FXML
  private DatePicker birth_date;            
  @FXML
  private TextField alive;
  @FXML
  private TextField dead;
  @FXML 
  private TextArea observation;
  
  @FXML
  private void cargar(ActionEvent event) throws SQLException{
      
    listaux=javafx.collections.FXCollections.observableArrayList();
    int id_cerd=0;
    String date_parto=null, cad1=null;
    
    cad1= (String)cerda.getValue();
    
    if(birth_date.getValue()!= null){
        date_parto=birth_date.getValue().toString();
    }
    
    int l_vivos, l_muertos;
    
    String observacion=observation.getText();
    
    //control lechones
    Pattern pat1 = Pattern.compile("[a-zA-Z]+");
    Matcher mat1 = pat1.matcher(alive.getText());
    if(alive.getText().isEmpty() || mat1.matches()){
        l_vivos = -1; 
    }else{
        l_vivos = Integer.parseInt(alive.getText());
    }
    
    Matcher mat2 = pat1.matcher(dead.getText());
    if(dead.getText().isEmpty() || mat2.matches()){
        l_muertos = -1; 
    }else{
        l_muertos = Integer.parseInt(dead.getText());
    }    
    
    //PARA CONTROLAR ERRORES.
    Alert alert_error = new Alert(Alert.AlertType.WARNING);
    alert_error.setTitle("Error");
    alert_error.setHeaderText("Modifique los campos indicados.");    
    
    if(cad1 == null || date_parto == null || l_vivos<0 || l_muertos<0){
        //ERROR EN LA CARGA 
        alert_error.setContentText(" TODOS LOS CAMPOS SON OBLIGATORIOS \n (MENOS LAS OBSERVACIONES)\n");
        alert_error.showAndWait();
        if(l_vivos<0){
            alive.clear();
        }
        if(l_muertos<0){
            dead.clear();
        }
        
    }else{
        
        String sql="SELECT id_cerda FROM cerda WHERE caravana='"+cad1+"'";
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        rs=dbq.query(con,sql);
        rs.beforeFirst();
        while(rs.next()){
            listaux.add(rs.getInt("id_cerda"));
        }
        id_cerd=(int)listaux.get(0);
        
        Parto p=new Parto(id_cerd,date_parto,"0000-00-00",l_vivos,l_muertos,observacion);
        p.saveParto();
        
        //CARGA REALIZADA
        Alert alert_exito = new Alert(Alert.AlertType.INFORMATION);
        alert_exito.setTitle("");
        alert_exito.setHeaderText(null);
        alert_exito.setContentText("CARGA DE DATOS EXITOSA");

        alert_exito.showAndWait();
        
        //Vuelvo a los valores por defecto para volver a cargar.
        cerda.setValue("");
        birth_date.setValue(LocalDate.now());
        alive.clear();
        dead.clear();
        observation.clear();
    }
  }

  private void loadComboBox()throws SQLException{
    lista=FXCollections.observableArrayList();
    db=DB_Connection.getInstance();
    con=db.getConnection();
    dbq=new DB_Query();
    rs=dbq.query(con,sql);
    rs.beforeFirst();
    while(rs.next()){
      lista.add(rs.getString("caravana"));
    }
    cerda.setItems(lista);
  }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            loadComboBox();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}