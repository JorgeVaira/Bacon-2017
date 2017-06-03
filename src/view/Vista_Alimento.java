package view;

import javafx.scene.Scene;

import javafx.stage.*;
import javafx.scene.layout.Pane;
import javafx.fxml.*;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Vista_Alimento {
    private Stage stage;
    private Scene scene;
    private static final int j = 1980;
    
    public Vista_Alimento() throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("vista_alimento.fxml")); 
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
