package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Employe;
import model.Fonction;
import model.FonctionDAO;

public class UpdateEmployeController {

	Employe employe;
	
	@FXML
    private TextField numBadgeEditText;
    @FXML
    private TextArea nomEditText;
    @FXML
    private TextField prenomEditText;
    @FXML
    private MenuButton fonctionEditList;
    
    
   public UpdateEmployeController(Employe employe) {
		this.employe=employe;
	}
    
    @FXML
    private void initialize () {
    	numBadgeEditText.setText(employe.getNumBadge());
    	nomEditText.setText(employe.getNom());
    	prenomEditText.setText(employe.getPrenom());
    	populateFonctionEditList();
    }
    
    public void populateFonctionEditList(){
    	try {
			for (Fonction fonction : FonctionDAO.searchFonctions()) {
				fonctionEditList.getItems().add(new MenuItem(fonction.getLibelleFonction()));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
