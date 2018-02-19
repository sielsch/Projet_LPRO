package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Employe;

public class AutorisationController {

	
	@FXML
    private TextField numBadgeAutorisationText;
	@FXML
    private TextField zoneAutorisationText;
    @FXML
    private TextArea zoneAutorisationArea;
    @FXML
    private TableView<Employe> AutorisationTable;
    @FXML
    private TableColumn<Employe, String>  numBadgeAutorisationColumn;
    @FXML
    private TableColumn<Employe, String>  nomAutorisationColumn;
    @FXML
    private TableColumn<Employe, Integer> zoneAutorisationColumn;
    
    @FXML
    private void initialize () {


    	numBadgeAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().numBadgeProperty());
    	nomAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        zoneAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().idFonctionProperty().asObject());

    }
}
