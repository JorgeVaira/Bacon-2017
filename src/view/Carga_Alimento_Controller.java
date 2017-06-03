package view;

import controller.Alimento;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Ivan Mansilla
 */
public class Carga_Alimento_Controller implements Initializable {
    
    @FXML private Button cancelar;
    
    @FXML
    private TextField tipe, stock;
    
    @FXML
    private TextArea recipe;
    
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
      private void cargar(ActionEvent event) throws SQLException {
        //con lo que hay adentro del constructor es lo que extraemos de los campos del formulario de carga de Cerdas, si quieren probarlo, yo ya lo hice y funciona
        //Nota por favor no tocar los archivos FXML, ni los controllers que haya en la carpeta vista
       
       String tipo = tipe.getText();
       String receta = recipe.getText();
       int cant = 0;
       
        //PARA CONTROLAR ERRORES.
        Alert alert_error = new Alert(Alert.AlertType.WARNING);
        alert_error.setTitle("Error");
        alert_error.setHeaderText("Modifique los campos indicados.");
        
        String error_tipo="";
        String error_receta="";
        String error_cant="";
        
        //TIPO
        Pattern pat1 = Pattern.compile("[a-zA-Z1-9]+");
        Matcher mat1 = pat1.matcher(tipo);
            if (mat1.matches()) {
                System.out.println("SI");
            } else {
                error_tipo="EL NOMBRE NO PUEDE ESTAR VACIO";   
            }
            
        //RECETA
        if(receta.isEmpty())
            error_receta="LA RECETA NO PUEDE ESTAR VACIA";
        
        //CANTIDAD
        Pattern pat2 = Pattern.compile("[1-9]+");
        Matcher mat2 = pat2.matcher(stock.getText());
            if (mat2.matches()) {
                cant = Integer.parseInt(stock.getText());
            } else {
                error_cant="LA CANTIDAD DEBE SER UN NUMERO";   
            }
        
        if(error_receta!="" || error_tipo!="" || error_receta!=""){
            alert_error.setContentText(""+error_tipo+"\n"+error_receta+"\n"+error_cant);
            alert_error.showAndWait();
            
            //Para que limpie solo el que tiene error.
            if(error_tipo!="")
                tipe.clear();    
     
            if(error_receta!="")
                recipe.clear();
            
            if(error_receta!="")
                stock.clear();
        }else{
            Alimento alimento = new Alimento(tipo,cant,receta);
            alimento.save_Alimento();
        
            //Cargda realizada 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Carga de datos exitosa");

            alert.showAndWait();
        
            //Vuelvo a los valores por defecto para volver a cargar.
            tipe.clear(); 
            stock.clear();
            recipe.clear();
        }
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
//