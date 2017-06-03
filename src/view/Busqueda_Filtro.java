/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Vaira
 */
public class Busqueda_Filtro {
    private Stage stage;
    private Scene scene;
   
   public Busqueda_Filtro() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("busqueda_filtro.fxml"));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
   }
}
