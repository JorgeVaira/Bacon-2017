/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import model.DB_Connection;
import model.DB_Query;

/**
 *
 * @author Jorge Vaira
 */

public class Busqueda_Filtro_Controller implements Initializable{
    //Atributos FXML//
    @FXML private RadioButton cerda;
    @FXML private RadioButton padrillo;
    @FXML private RadioButton lechon;
    @FXML private RadioButton tunel;
    @FXML private CheckBox caravana;
    @FXML private CheckBox grupo;
    @FXML private ComboBox caravan;
    @FXML private ComboBox group;
    @FXML private ComboBox tun;
    //Atributos Comunes//
    private ToggleGroup g=new ToggleGroup();
    private ToggleGroup g2=new ToggleGroup();
    //Atributos para Conexión a Base De Datos//
    DB_Connection db;
    Connection con;
    DB_Query dbq;
    ResultSet rs;
    ObservableList<String> l1;
    ObservableList<Integer> l2;
    //Constructor//
    public Busqueda_Filtro_Controller(){
        
    }
    //Métodos para FXML//
    @FXML
    private void radio(){
        cerda.setToggleGroup(g);
        padrillo.setToggleGroup(g);
        lechon.setToggleGroup(g);
        tunel.setToggleGroup(g);
        caravana.setDisable(true);
        grupo.setDisable(true);
    }
    @FXML
    private void habilitar() throws SQLException{
        if(cerda.isSelected()){
            caravana.setDisable(false);
            caravana.setSelected(false);
            grupo.setDisable(false);
            grupo.setSelected(false);
            caravan.setDisable(true);
            group.setDisable(true);
            tun.setDisable(true);
        }
        if(padrillo.isSelected() || lechon.isSelected()){
            caravana.setDisable(false);
            caravana.setSelected(false);
            grupo.setDisable(true);
            grupo.setSelected(false);
            caravan.setDisable(true);
            group.setDisable(true);
            tun.setDisable(true);
        }
        if(tunel.isSelected()){
            caravana.setDisable(true);
            caravana.setSelected(false);
            grupo.setDisable(true);
            grupo.setSelected(false);
            caravan.setDisable(true);
            group.setDisable(true);
            tun.setDisable(false);
            rellenar("tunel","","");
        }
    }
    @FXML
    private void habilitar_ComboBox() throws SQLException{
        if(cerda.isSelected() && caravana.isSelected()){
            caravan.setDisable(false);
            group.setDisable(true);
            tun.setDisable(true);
            rellenar("cerda","caravana","");
        }
        if(cerda.isSelected() && grupo.isSelected()){
            caravan.setDisable(true);
            group.setDisable(false);
            tun.setDisable(true);
            rellenar("cerda","grupo","");
        }
        if(cerda.isSelected() && caravana.isSelected() && grupo.isSelected()){
                caravan.setDisable(false);
                group.setDisable(false);
                tun.setDisable(true);
                rellenar("cerda","caravana","grupo");
        }
        if(padrillo.isSelected() && caravana.isSelected()){
            caravan.setDisable(false);
            group.setDisable(true);
            tun.setDisable(true);
            rellenar("padrillo","caravana","");
        }
        if(lechon.isSelected() && caravana.isSelected()){
            caravan.setDisable(false);
            group.setDisable(true);
            tun.setDisable(true);
            rellenar("lechon","caravana","");
        }
        
    }
    //Método/s de Consulta//
    private void rellenar(String filtro1,String filtro2, String filtro3) throws SQLException{
        String sql;
        db=DB_Connection.getInstance();
        con=db.getConnection();
        dbq=new DB_Query();
        l1=javafx.collections.FXCollections.observableArrayList();
        l2=javafx.collections.FXCollections.observableArrayList();
        if(filtro1=="cerda"){
            if(filtro2=="caravana"){
                sql="SELECT caravana FROM cerda WHERE fecha_fin IS NULL ORDER BY caravana";
                rs=dbq.query(con,sql);
                rs.beforeFirst();
                while(rs.next()){
                    l1.add(rs.getString("caravana"));
                }
                caravan.setItems(l1);
                caravan.setValue(l1.get(0));
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        caravan.setDisable(true);
        group.setDisable(true);
        tun.setDisable(true);
        radio();
    }
}
