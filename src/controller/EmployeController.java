package controller;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Employe;
import model.EmployeDAO;

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
       
    }
 
    
    
  //Search an employee
    @FXML
    private void searchEmploye (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Employee information
            Employe emp = EmployeDAO.searchEmploye(numBadgeText.getText());
            //Populate Employee on TableView and Display on TextArea
            populateAndShowEmploye(emp);
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
    	System.out.println(employeTable);
    	System.out.println(empData);
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
            EmployeDAO.insertEmp("10",nomText.getText(),prenomText.getText(),passwordText.getText(),1);
            resultArea.setText("Employee inserted! \n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while inserting employee " + e);
            throw e;
        }
    }
 
    //Delete an employee with a given employee Id from DB
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeDAO.deleteEmpWithId(numBadgeText.getText());
            resultArea.setText("Employee deleted! Employee id: " + numBadgeText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while deleting employee " + e);
            throw e;
        }
    }
    
    
    
}
