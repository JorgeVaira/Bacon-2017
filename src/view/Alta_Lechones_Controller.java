package view;

import controller.Alimento;
import javafx.stage.Stage;
import controller.Grupo_Lechon;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import model.DB_Connection;
import model.DB_Query;

public class Alta_Lechones_Controller implements Initializable {
   
    private ObservableList<String> lista_tipo_alimento = javafx.collections.FXCollections.observableArrayList();;
    private ObservableList<String> lista_id_alimento = javafx.collections.FXCollections.observableArrayList();;
    
    @FXML
    private ComboBox alimento,caravana,fechaDestete;

    @FXML 
    private Button cancelar;
    
    @FXML
    private TextField cantidad, pesoInicial;
    
    @FXML
    private TextArea observaciones;
    
    
    @FXML
    private void cargar(ActionEvent event) throws SQLException {

        if(!caravana.getValue().equals("") && !alimento.getValue().equals("") &&
        !fechaDestete.getValue().equals("") && !cantidad.getText().equals("") && !pesoInicial.getText().toString().equals("")){
            
            Grupo_Lechon grupo = new Grupo_Lechon(caravana.getValue().toString(),
            fechaDestete.getValue().toString(),Integer.parseInt(cantidad.getText()),
            Integer.parseInt(pesoInicial.getText()),alimento.getValue().toString(),
            observaciones.getText());

            if(grupo.discharge()){ 
                JOptionPane.showMessageDialog(null,"¡CARGA EXITOSA!","Carga de Lechones",JOptionPane.INFORMATION_MESSAGE);
                caravana.selectionModelProperty().setValue(null);
                pesoInicial.setText(null);
            }
        }
        else JOptionPane.showMessageDialog(null,"Algunos campos obligatorios no han sido cargados","Carga de Lechones",
                JOptionPane.ERROR_MESSAGE);
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
//      Pdf pdf = new Pdf();
//      pdf.generarPdf();
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void loadComboBoxAlimento()throws SQLException{
        ResultSet aux = Alimento.getFoods();
        
	aux.beforeFirst();
        while(aux.next()) lista_tipo_alimento.add(aux.getString("tipo"));

        alimento.setItems(lista_tipo_alimento);
    }
    
    @FXML 
    private void loadFechaDestete() throws SQLException{
        fechaDestete.disableProperty().setValue(Boolean.FALSE);
        fechaDestete.setItems(Grupo_Lechon.getWeaningDates(caravana.getValue().toString()));
    }
    
    private void loadCaravanaMadre() throws SQLException{
        caravana.setItems(Grupo_Lechon.getMotherCaravans());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
 
        try{
            fechaDestete.disableProperty().setValue(Boolean.TRUE);
            loadCaravanaMadre();
            loadComboBoxAlimento();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }    
}