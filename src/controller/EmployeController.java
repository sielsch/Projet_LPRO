package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
	private TableColumn<Employe, String> numBadgeColumn;
	@FXML
	private TableColumn<Employe, String> nomColumn;
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
	@FXML
	private TableView<Employe> employeTable;
	@FXML
	private void initialize() {

		numBadgeColumn.setCellValueFactory(cellData -> cellData.getValue().numBadgeProperty());
		nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
		idFonctionColumn.setCellValueFactory(cellData -> cellData.getValue().idFonctionProperty().asObject());
		libelleFonctionColumn.setCellValueFactory(cellData -> cellData.getValue().libelleFonctinoProperty());
		populateFonctionEditList();
	}

	@FXML
	private void searchEmployes(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		try {
			ObservableList<Employe> employesList = EmployeDAO.searchEmployeByNomAndNum(numBadgeSearchText.getText(),
					nomSearchText.getText());
			populateEmployees(employesList);
			employeTable.refresh();
		} catch (SQLException e) {
			e.printStackTrace();
			resultArea.setText("Error occurred while getting employee information from DB.\n" + e);
			throw e;
		}
	}

	@FXML
	private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			ObservableList<Employe> empData = EmployeDAO.searchEmployes();
			populateEmployees(empData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting employees information from DB.\n" + e);
			throw e;
		}
	}

	@FXML
	private void populateEmploye(Employe emp) throws ClassNotFoundException {

		ObservableList<Employe> empData = FXCollections.observableArrayList();
		empData.add(emp);
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
	private void populateEmployees(ObservableList<Employe> empData) throws ClassNotFoundException {
		// Set items to the employeeTable
		employeTable.setItems(empData);
	}

	private void setEmpInfoToTextArea(Employe emp) {
		resultArea.setText("First Name: " + emp.getNom() + "\n" + "Last Name: " + emp.getPrenom());
	}

	@FXML
	private void insertEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

		if (correctInsert()) {
			try {
				EmployeDAO.insertEmp(numBadgeText.getText(), nomText.getText(), prenomText.getText(),
						passwordText.getText(), listFonctionBtn.getSelectionModel().getSelectedItem().getIdFonction());
				resultArea.setText("Employee inserted! \n");
				numBadgeText.setText("");
				nomText.setText("");
				prenomText.setText("");
				passwordText.setText("");
			} catch (SQLException e) {
				resultArea.setText("Problem occurred while inserting employee " + e);
				throw e;
			}
			employeTable.refresh();
		}
	}

	// Delete an employee with a given employee Id from DB
	@FXML
	private void deleteEmploye(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// EmployeDAO.deleteEmpWithId(numBadgeText.getText());
			EmployeDAO.deleteEmpWithId(employeTable.getSelectionModel().getSelectedItem().getNumBadge());
			resultArea.setText("Employee deleted! Employee id: " + numBadgeText.getText() + "\n");
		} catch (SQLException e) {
			resultArea.setText("Problem occurred while deleting employee " + e);
			throw e;
		}
		employeTable.refresh();
	}

	@FXML
	private void updateEmploye(ActionEvent actionEvent) {
		try {
			showWindow(employeTable.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void populateFonctionEditList() {
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
		// stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Modifier Employe");
		stage.initOwner(numBadgeText.getScene().getWindow());
		stage.setScene(scene);
		controller.setDialogStage(stage);

		stage.show();
	}

	private boolean correctInsert() {
		String errorMessage = "";
		boolean correct = true;
		if (numBadgeText.getText().length() != 20) {
			errorMessage += "Le numéro de badge doit contenir 20 chiffres.\n";
			correct = false;
		}
		if ((!passwordText.getText().matches("[0-9]+") || passwordText.getText().length() != 5)) {
			errorMessage += "Le mot de passe doit être un nombre à 5 chiffre.\n";
			correct = false;
		}
		if (nomText.getText().length() == 0) {
			errorMessage += "Vous devez rentrer un nom.\n";
			correct = false;
		}
		if (prenomText.getText().length() == 0) {
			errorMessage += "Vous devez rentrer un prénom.\n";
			correct = false;
		}
		if (listFonctionBtn.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Vous devez choisir une fonction.\n";
			correct = false;
		}

		if (!correct) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Les informations ne sont pas valides");
			alert.setContentText(errorMessage);
			alert.showAndWait();
		}
		return correct;
	}
}
