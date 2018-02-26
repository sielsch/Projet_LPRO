package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;

import apple.laf.JRSUIUtils.TabbedPane;
import controller.DbMenuController;
import controller.RootLayoutController;

//Main class which extends from Application Class
public class Main extends Application {

	// This is our PrimaryStage (It contains everything)
	private Stage primaryStage;

	// This is the BorderPane of RootLayout

	private BorderPane rootLayout;

	private TabPane tabLayout;
	
	private RootLayoutController rootController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Poste Administrateur");
		initRootLayout();
		try {
			if(DBUtil.dbConnect()){
				showEmployeeView();
				showAutorisationView();
				showHistoriqueView();
				rootLayout.setCenter(tabLayout);
			} else {
				rootController.showDbMenu();
				start(primaryStage);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		

		
	}

	public void initRootLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/RootLayout.fxml"));
			rootController= new RootLayoutController();
			loader.setController(rootController);
			rootLayout = (BorderPane) loader.load();
			tabLayout = new TabPane();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					try {
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showHistoriqueView() {

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
