package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class clavierTactileMain extends Application {
	
	private Stage primaryStage;

	// This is the BorderPane of RootLayout

	private BorderPane layout;


	
	@Override
	public void start(Stage primaryStage) {
		// 1) Declare a primary stage (Everything will be on this stage)
		this.primaryStage = primaryStage;

		// Optional: Set a title for primary stage
		this.primaryStage.setTitle("Authentification");

		// 2) Initialize RootLayout
		initLayout();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void initLayout(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/view/clavierTactile.fxml"));
		try {
			layout = (BorderPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(layout); // We are sending rootLayout to
												// the Scene.
		primaryStage.setScene(scene); 
		primaryStage.show(); // Display the primary stage
	}
	
	
}
