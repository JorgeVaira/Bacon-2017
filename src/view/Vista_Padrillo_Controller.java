/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import controller.Padrillo;
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

/**
 *
 * @author Jorge Vaira
 */
public class Vista_Padrillo_Controller implements Initializable {
    @FXML private TableView tabla ;
    
    @FXML private Button salir;

    @FXML private ObservableList data;

    @FXML private ObservableList<ObservableList> data2;

    @FXML
    private void salir(ActionEvent event) {
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
        String sql="SELECT * FROM padrillo";
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
                Logger.getLogger(Vista_Padrillo_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    }     
    
}
