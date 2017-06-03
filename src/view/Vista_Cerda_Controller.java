package view;


import controller.Cerda;
import model.DB_Connection;
import model.DB_Query;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.stage.*;
import controller.Cerda;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;


public class Vista_Cerda_Controller  implements Initializable {
    @FXML private TableView tabla ;
    
    @FXML private Button salir;

    @FXML private ObservableList data;

    @FXML
    private void cancelar(ActionEvent event) {
        try{
            Stage stage = (Stage) salir.getScene().getWindow();
            stage.close();
        }catch(Exception e){
            System.out.print(e);
        }
    }  

    public void load_data() throws SQLException{
        System.out.println("------------------------------------");
        DB_Connection DB_con=DB_Connection.getInstance();
        Connection c=DB_con.getConnection();
        String sql = "SELECT caravana as Caravana, fecha_inicio as Fecha_de_Inicio, fecha_fin as Fecha_de_Fin, nro_grupo as Nro_Grupo, observaciones as Observaciones, alimento_tipo as Tipo_Alimento FROM cerda";
        DB_Query q=new DB_Query();
        ResultSet rs=q.query(c, sql);
       
        
        data = javafx.collections.FXCollections.observableArrayList();
        try{
            rs.first();
         
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                    return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
                tabla.getColumns().addAll(col); 
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            rs.beforeFirst();
            
            while(rs.next()){
                //Iterate Row
              
                ObservableList<String> row = javafx.collections.FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    if(rs.getString(i)!=null){
                        row.add(rs.getString(i));
                    }else{
                        row.add("-");
                    }
                }
                data.add(row);
            }

            

            //FINALLY ADDED TO TableView
            tabla.setItems(data);
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");             
        }
    }
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            try {
                load_data();
            } catch (SQLException ex) {
                Logger.getLogger(Vista_Cerda_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    }     
}
