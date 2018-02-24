package controller;

import java.awt.Choice;
import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import model.Employe;
import model.EmployeDAO;
import model.Fonction;
import model.FonctionDAO;

public class EmployeController {

	@FXML
    private TextField numBadgeText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField nomText;
    @FXML
    private TextField prenomText;
    @FXML
    private TextField passwordText;
    @FXML
	private TextField numBadgeSearchText; 	
	@FXML
	private TextField nomSearchText;
    @FXML
    private TableView<Employe> employeTable;
    @FXML
    private TableColumn<Employe, String>  numBadgeColumn;
    @FXML
    private TableColumn<Employe, String>  nomColumn;
    @FXML
    private TableColumn<Employe, String> prenomColumn;
    @FXML
    private TableColumn<Employe, String> passwordColumn;
    @FXML
    private TableColumn<Employe, Integer> idFonctionColumn;
    @FXML
    private TableColumn<Employe, String> libelleFonctionColumn;
    @FXML
    private ChoiceBox<Fonction> listFonctionBtn;
    
  //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize () {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Employee objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe
 
        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
        numBadgeColumn.setCellValueFactory(cellData -> cellData.getValue().numBadgeProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
       idFonctionColumn.setCellValueFactory(cellData -> cellData.getValue().idFonctionProperty().asObject());
       libelleFonctionColumn.setCellValueFactory(cellData -> cellData.getValue().libelleFonctinoProperty());
       populateFonctionEditList();
    }
 
    
    
  //Search an employee
    @FXML
    private void searchEmployes (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
        	ObservableList<Employe> employesList = EmployeDAO.searchEmployeByNomAndNum(numBadgeSearchText.getText(), nomSearchText.getText());
//            //Get Employee information
//        	if(!numBadgeTextSearch.getText().equals("") && nomSearch.getText().equals("")){
//        		System.out.println("chaine non vide");
//        		emp = EmployeDAO.searchEmploye(numBadgeText.getText());
//        	} else if (numBadgeTextSearch.getText().equals("") && !nomSearch.getText().equals("")) {
//        		
//        	} else if (!numBadgeTextSearch.getText().equals("") && !nomSearch.getText().equals("")){
//        		
//        	}
             
            //Populate Employee on TableView and Display on TextArea
            populateEmployees(employesList);
            employeTable.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }

    
  //Search all employees
    @FXML
    private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            ObservableList<Employe> empData = EmployeDAO.searchEmployes();
            //Populate Employees on TableView
            populateEmployees(empData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
    
    
    
    @FXML
    private void populateEmploye (Employe emp) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Employe> empData = FXCollections.observableArrayList();
        //Add employee to the ObservableList
        empData.add(emp);
        //Set items to the employeeTable
        employeTable.setItems(empData);
    }
    
    
    
    @FXML
    private void populateAndShowEmploye(Employe emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmploye(emp);
            setEmpInfoToTextArea(emp);
        } else {
            resultArea.setText("This employee does not exist!\n");
        }
    }
	
    @FXML
    private void populateEmployees (ObservableList<Employe> empData) throws ClassNotFoundException {
        //Set items to the employeeTable
        employeTable.setItems(empData);
    }
    
    
    private void setEmpInfoToTextArea ( Employe emp) {
        resultArea.setText("First Name: " + emp.getNom() + "\n" +
                "Last Name: " + emp.getPrenom());
    }
    

 
    //Insert an employee to the DB
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeDAO.insertEmp(numBadgeText.getText(),nomText.getText(),prenomText.getText(),passwordText.getText(),1);
            resultArea.setText("Employee inserted! \n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while inserting employee " + e);
            throw e;
        }
        employeTable.refresh();
    }
 
    //Delete an employee with a given employee Id from DB
    @FXML
    private void deleteEmploye (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
//            EmployeDAO.deleteEmpWithId(numBadgeText.getText());
            EmployeDAO.deleteEmpWithId(employeTable.getSelectionModel().getSelectedItem().getNumBadge());
            resultArea.setText("Employee deleted! Employee id: " + numBadgeText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while deleting employee " + e);
            throw e;
        }
        employeTable.refresh();
    }
    
    @FXML
    private void updateEmploye (ActionEvent actionEvent){
    	try {
			showWindow(employeTable.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void populateFonctionEditList(){
   try {
	listFonctionBtn.getItems().addAll(FonctionDAO.searchFonctions());
	listFonctionBtn.setConverter(new StringConverter<Fonction>() {
		
		@Override
		public String toString(Fonction fonction) {
			return fonction.getLibelleFonction();
		}
		
		@Override
		public Fonction fromString(String string) {
			// TODO Auto-generated method stub
			return null;
		}
	});
} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
    }
    
    
    private void showWindow(Employe employe) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateEmployeDialogView.fxml"));
        UpdateEmployeController controller = new UpdateEmployeController(employe, this);
        loader.setController(controller);
        final Parent root = loader.load();
        final Scene scene = new Scene(root);
        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Modifier Employe");
        stage.initOwner(numBadgeText.getScene().getWindow());
        stage.setScene(scene);
        controller.setDialogStage(stage);
        
        stage.show();
    }
    
    
    
}
