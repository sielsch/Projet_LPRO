package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Employe;
import model.EmployeDAO;
import model.Fonction;
import model.FonctionDAO;

public class UpdateEmployeController {

	private Employe employe;

	@FXML
	private Label numBadgeLabel;
	@FXML
	private TextField nomEditText;
	@FXML
	private TextField prenomEditText;
	@FXML
	private ChoiceBox<Fonction> fonctionEditList;
	@FXML
	private TextField pswEditText;
	
	
	
	private Stage dialogStage;
	
	private EmployeController employeController;

	public UpdateEmployeController(Employe employe, EmployeController employeController) {
		this.employe = employe;
		this.employeController=employeController;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void initialize() {
		numBadgeLabel.setText(employe.getNumBadge());
		nomEditText.setText(employe.getNom());
		prenomEditText.setText(employe.getPrenom());
		pswEditText.setText(employe.getPassword());
		populateFonctionEditList();
		try {
			System.out.println(FonctionDAO.searchFonctionById(employe.getIdFonction()));
			fonctionEditList.getSelectionModel().select(FonctionDAO.searchFonctionById(employe.getIdFonction()));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public void populateFonctionEditList() {
		try {
			fonctionEditList.getItems().addAll(FonctionDAO.searchFonctions());
			fonctionEditList.setConverter(new StringConverter<Fonction>() {

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

	@FXML
	private void validerEdit() {

		try {
			EmployeDAO.updateEmploye(employe.getNumBadge(), employe.getNom(), employe.getPrenom(), employe.getPassword(), employe.getIdFonction());
		//	employeController.refresh();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	private void annulerEdit() {
		dialogStage.close();
	}

}
