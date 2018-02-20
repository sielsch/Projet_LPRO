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

import model.Historique;
import model.HistoriqueDAO;

public class HistoriqueController {

	@FXML
    private TextField numBadgeHistoriqueText;
	@FXML
    private TextField zoneHistoriqueText;
    @FXML
    private TextArea zoneHistoriqueArea;
    @FXML
    private TableView<Historique> historiqueTable;
    @FXML
    private TableColumn<Historique, String>  numBadgeHistoriqueColumn;
    @FXML
    private TableColumn<Historique, String>  nomHistoriqueColumn;
    @FXML
    private TableColumn<Historique, Integer> numZoneHistoriqueColumn;
    @FXML
    private TableColumn<Historique, String> libelleZoneHistoriqueColumn;
    @FXML
    private TableColumn<Historique, String> dateAccesHistoriqueColumn;
	
    
    @FXML
    private void initialize () {

    	numBadgeHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().numBadgeProperty());
    	nomHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
    	numZoneHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().numZoneProperty().asObject());
        libelleZoneHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().libelleZoneProperty());
        dateAccesHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().dateAccesProperty());
        
    }
    
    @FXML
    private void searchHistoriques(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            ObservableList<Historique> historiqueData = HistoriqueDAO.searchHistoriques();
            //Populate Employees on TableView
            populateHistoriques(historiqueData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
    
    @FXML
    private void populateHistorique (Historique historique) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Historique> historiqueData = FXCollections.observableArrayList();
        //Add employee to the ObservableList
        historiqueData.add(historique);
        //Set items to the employeeTable
        historiqueTable.setItems(historiqueData);
    }
    
    
    
    @FXML
    private void populateAndShowhistorique(Historique historique) throws ClassNotFoundException {
        if (historique != null) {
            populateHistorique(historique);
 //           setEmpInfoToTextArea(autorisation);
        } else {
//            resultArea.setText("This employee does not exist!\n");
        }
    }
	
    @FXML
    private void populateHistoriques (ObservableList<Historique> historiqueData) throws ClassNotFoundException {
        //Set items to the employeeTable
    	System.out.println(historiqueTable);
    	System.out.println(historiqueData);
    	historiqueTable.setItems(historiqueData);
    }
}
