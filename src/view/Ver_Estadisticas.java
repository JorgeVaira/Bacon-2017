package view;

import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.layout.Pane;
import javafx.fxml.*;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;


public class Ver_Estadisticas {
    private Stage stage;
    private Scene scene;
    public Ver_Estadisticas() throws Exception{
		Parent root=FXMLLoader.load(getClass().getResource("Ver_Estadisticas.fxml"));
		stage=new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		scene=new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
