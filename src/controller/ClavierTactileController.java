package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import model.Employe;
import model.EmployeDAO;

public class ClavierTactileController {

	@FXML
	PasswordField pswText;

	Employe employe = new Employe();

	@FXML
	private void write1() {
		pswText.appendText("1");
	}

	@FXML
	private void write2() {
		pswText.appendText("2");
	}

	@FXML
	private void write3() {
		pswText.appendText("3");
	}

	@FXML
	private void write4() {
		pswText.appendText("4");
	}

	@FXML
	private void write5() {
		pswText.appendText("5");
	}

	@FXML
	private void write6() {
		pswText.appendText("6");
	}

	@FXML
	private void write7() {
		pswText.appendText("7");
	}

	@FXML
	private void write8() {
		pswText.appendText("8");
	}

	@FXML
	private void write9() {
		pswText.appendText("9");
	}

	@FXML
	private void write0() {
		pswText.appendText("0");
	}

	@FXML
	private void corriger() {
		pswText.setText("");
	}
	
	@FXML
	private void annuler() {
		pswText.setText("");
	}

	@FXML
	private boolean valider() {
		try {
			employe = EmployeDAO.searchEmploye("2");
			if (employe.getPassword().equals(pswText.getText())) {
				System.out.println("mot de passe correct");
				return true;
			} else {
				System.out.println("mot de passe incorrect");
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
