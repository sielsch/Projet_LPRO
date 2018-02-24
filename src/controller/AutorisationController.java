package controller;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Autorisation;
import model.AutorisationDao;
import model.Fonction;
import model.FonctionDAO;
import model.Zone;
import model.ZoneDAO;
import util.DBUtil;

public class AutorisationController {

	@FXML
	private TextField numBadgeAutorisationText;
	@FXML
	private ChoiceBox<Zone> zoneAutorisationChoice;
	@FXML
	private ChoiceBox<Zone> zoneAutorisationSearchChoice;

	@FXML
	private TextArea zoneAutorisationArea;

	@FXML
	private TextField numBadgeAutorisationSearchText;

	@FXML
	private TableView<Autorisation> autorisationTable;
	@FXML
	private TableColumn<Autorisation, String> numBadgeAutorisationColumn;
	@FXML
	private TableColumn<Autorisation, String> nomAutorisationColumn;
	@FXML
	private TableColumn<Autorisation, Integer> numZoneAutorisationColumn;
	@FXML
	private TableColumn<Autorisation, String> libelleZoneAutorisationColumn;

	@FXML
	private void initialize() {

		numBadgeAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().numBadgeProperty());
		nomAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		numZoneAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().numZoneProperty().asObject());
		libelleZoneAutorisationColumn.setCellValueFactory(cellData -> cellData.getValue().libelleZoneProperty());
		populateZoneChoiceBox();
	}

	@FXML
	private void searchAutorisations(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Get all Employees information
			ObservableList<Autorisation> autorisationData = AutorisationDao.searchAutorisations();
			// Populate Employees on TableView
			populateAutorisations(autorisationData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting employees information from DB.\n" + e);
			throw e;
		}
	}

	@FXML
	private void populateAutorisation(Autorisation autorisation) throws ClassNotFoundException {
		// Declare and ObservableList for table view
		ObservableList<Autorisation> autorisationData = FXCollections.observableArrayList();
		// Add employee to the ObservableList
		autorisationData.add(autorisation);
		// Set items to the employeeTable
		autorisationTable.setItems(autorisationData);
	}

	@FXML
	private void populateAndShowAutorisation(Autorisation autorisation) throws ClassNotFoundException {
		if (autorisation != null) {
			populateAutorisation(autorisation);
			// setEmpInfoToTextArea(autorisation);
		} else {
			// resultArea.setText("This employee does not exist!\n");
		}
	}

	@FXML
	private void populateAutorisations(ObservableList<Autorisation> autorisationData) throws ClassNotFoundException {
		// Set items to the employeeTable
		autorisationTable.setItems(autorisationData);
	}

	@FXML
	private void addAutorisation() {
		AutorisationDao.insertAutorisation(numBadgeAutorisationText.getText(),
				zoneAutorisationChoice.getSelectionModel().getSelectedItem().getIdZone());
		autorisationTable.refresh();
	}

	@FXML
	private void searchAutorisation() {
		ObservableList<Autorisation> autorisationList;
		try {
			if(zoneAutorisationSearchChoice.getSelectionModel().getSelectedItem()==null){
				autorisationList=AutorisationDao.searchAutorisationByBadge(numBadgeAutorisationSearchText.getText());
			}else {
				 autorisationList= AutorisationDao.searchAutorisationByBadgeZone(numBadgeAutorisationSearchText.getText(), zoneAutorisationSearchChoice.getSelectionModel().getSelectedItem().getIdZone());
			}
			 populateAutorisations(autorisationList);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		autorisationTable.refresh();
	}

	public void populateZoneChoiceBox() {
		try {
			zoneAutorisationChoice.getItems().addAll(ZoneDAO.searchZones());
			zoneAutorisationChoice.setConverter(new StringConverter<Zone>() {

				@Override
				public String toString(Zone zone) {
					return zone.getLibelleZone();
				}

				@Override
				public Zone fromString(String string) {
					// TODO Auto-generated method stub
					return null;
				}
			});
			zoneAutorisationSearchChoice.getItems().addAll(ZoneDAO.searchZones());
			zoneAutorisationSearchChoice.setConverter(new StringConverter<Zone>() {

				@Override
				public String toString(Zone zone) {
					return zone.getLibelleZone();
				}

				@Override
				public Zone fromString(String string) {
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
}
