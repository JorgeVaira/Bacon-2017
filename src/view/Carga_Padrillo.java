package view;

import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.layout.Pane;
import javafx.fxml.*;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;

public class Carga_Padrillo {
    
    private Stage stage;
    private Scene scene;

    public Carga_Padrillo() throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("carga_padrillo.fxml"));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
   }
}