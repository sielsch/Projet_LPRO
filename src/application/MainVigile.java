package application;

import java.io.IOException;
import java.sql.SQLException;

import controller.RootLayoutController;
import controller.VigileRootController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.DBUtil;

public class MainVigile extends Application {

	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	private VigileRootController rootController;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Poste vigiles");
		initRootLayout();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void initRootLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/VigileRootView.fxml"));
			rootController= new VigileRootController();
			loader.setController(rootController);
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
