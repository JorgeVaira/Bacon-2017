package view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Ivan Mansilla
 */
public class Dashboard_Controller implements Initializable {
    
    @FXML
    private void carga_cerda(ActionEvent event) {
        try{
            Carga_Cerda cc = new Carga_Cerda();
        }catch(Exception e){
        
        }
    }  
    @FXML
    private void carga_padrillo(ActionEvent event) {
        try{
            Carga_Padrillo cc = new Carga_Padrillo();
        }catch(Exception e){
        
        }
    }  
    @FXML
    private void carga_alimento(ActionEvent event) {
        try{
            Carga_Alimento ca = new Carga_Alimento();
        }catch(Exception e){
        
        }
    }
    @FXML
    private void ver_cerda(ActionEvent event){
        try {
            Vista_Cerda vc=new Vista_Cerda();
        } catch (Exception ex) {
           
        }
    }
    @FXML
    private void ver_padrillo(ActionEvent event){
        try{
            Vista_Padrillo vp=new Vista_Padrillo();
        }
        catch(Exception e){
        
        }
    }
    @FXML
    private void ver_alimento(ActionEvent event){
        try{
            Vista_Alimento vp=new Vista_Alimento();
        }
        catch(Exception e){
        
        }
    }
    @FXML
    private void carga_parto(ActionEvent event){
        try{
            Carga_Parto cp=new Carga_Parto();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void carga_servicio(ActionEvent event){
        try{
            Carga_Servicio cs= new Carga_Servicio();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void carga_modificacion(ActionEvent event){
        try{
            Carga_Modificacion cm=new Carga_Modificacion();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void Ver_Estadisticas(ActionEvent event){
        try{
            Ver_Estadisticas cm=new Ver_Estadisticas();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void alta_lechones(ActionEvent event){
        try{
            Alta_Lechones al=new Alta_Lechones();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
