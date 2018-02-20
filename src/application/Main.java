package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import apple.laf.JRSUIUtils.TabbedPane;

//Main class which extends from Application Class
public class Main extends Application {

	// This is our PrimaryStage (It contains everything)
	private Stage primaryStage;

	// This is the BorderPane of RootLayout

	private BorderPane rootLayout;

	private TabPane tabLayout;

	@Override
	public void start(Stage primaryStage) {
		// 1) Declare a primary stage (Everything will be on this stage)
		this.primaryStage = primaryStage;

		// Optional: Set a title for primary stage
		this.primaryStage.setTitle("SW Test Academy - Sample JavaFX App");

		// 2) Initialize RootLayout
		initRootLayout();

		// 3) Display the EmployeeOperations View
		showEmployeeView();
		showAutorisationView();
		showHistoriqueView();
		rootLayout.setCenter(tabLayout);
	}

	// Initializes the root layout.
	public void initRootLayout() {
		try {
			// First, load root layout from RootLayout.fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			tabLayout = new TabPane();

			// Second, show the scene containing the root layout.
			Scene scene = new Scene(rootLayout); // We are sending rootLayout to
													// the Scene.
			primaryStage.setScene(scene); // Set the scene in primary stage.

			/*
			 * //Give the controller access to the main. RootLayoutController
			 * controller = loader.getController(); controller.setMain(this);
			 */

			// Third, show the primary stage
			primaryStage.show(); // Display the primary stage
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showHistoriqueView(){
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/HistoriqueAcces.fxml"));
			AnchorPane historiqueOperationsView = (AnchorPane) loader.load();
			
			Tab tabHistorique = new Tab("Historique", historiqueOperationsView);
			tabLayout.getTabs().add(tabHistorique);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Shows the employee operations view inside the root layout.
	public void showEmployeeView() {
		try {
			// First, load EmployeeView from EmployeeView.fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EmployeView.fxml"));
			AnchorPane employeeOperationsView = (AnchorPane) loader.load();

			//
			// FXMLLoader loaderAutorisation = new FXMLLoader();
			// loaderAutorisation.setLocation(Main.class.getResource("/view/AutorisationView.fxml"));
			// AnchorPane AutorisationOperationsView = (AnchorPane)
			// loaderAutorisation.load();
			//

			// Set Employee Operations view into the center of root layout.

			Tab tabEmploye = new Tab("employe", employeeOperationsView);
			// Tab tabAutorisation = new Tab("employe",
			// AutorisationOperationsView);

			tabLayout.getTabs().add(tabEmploye);
			// tabLayout.getTabs().add(tabAutorisation);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showAutorisationView() {
		try {
			FXMLLoader loaderAutorisation = new FXMLLoader();
			loaderAutorisation.setLocation(Main.class.getResource("/view/AutorisationView.fxml"));
			AnchorPane AutorisationOperationsView;
			AutorisationOperationsView = (AnchorPane) loaderAutorisation.load();
			Tab tabAutorisation = new Tab("Autorisation", AutorisationOperationsView);
			tabLayout.getTabs().add(tabAutorisation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}

// package application;
//
// import javafx.application.Application;
// import javafx.stage.Stage;
// import javafx.scene.Scene;
// import javafx.scene.layout.BorderPane;
//
//
// public class Main extends Application {
// @Override
// public void start(Stage primaryStage) {
// try {
// BorderPane root = new BorderPane();
// Scene scene = new Scene(root,400,400);
// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
// primaryStage.setScene(scene);
// primaryStage.show();
// } catch(Exception e) {
// e.printStackTrace();
// }
// }
//
// public static void main(String[] args) {
// launch(args);
// }
// }
