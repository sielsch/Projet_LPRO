package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RootLayoutController {
 
    //Exit the program
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
 
    //Help Menu button behavior
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is a sample JAVAFX application for SWTESTACADEMY!");
        alert.setContentText("You can search, delete, update, insert a new employee with this program.");
        alert.show();
    }
    
    
    public void showDbMenu() throws IOException{
    	final FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DbMenu.fxml"));
    	DbMenuController controller = new DbMenuController();
		loader.setController(controller);
		final Parent root = loader.load();
		final Scene scene = new Scene(root);
		Stage stage = new Stage();
		// stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Configuration Base de Donnée");
		stage.setScene(scene);
		controller.setConfigStage(stage);
		stage.showAndWait();
    }
}