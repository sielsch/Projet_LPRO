package controller;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Historique;
import model.HistoriqueDAO;
import model.Zone;
import model.ZoneDAO;

public class HistoriqueController {

	@FXML
	private TextField numBadgeHistoriqueText;
	@FXML
	private TextArea zoneHistoriqueArea;
	@FXML
	private ChoiceBox<Zone> zoneHistoriqueChoice;
	@FXML
	private DatePicker dateMin;
	@FXML
	private DatePicker dateMax;

	@FXML
	private TableView<Historique> historiqueTable;
	@FXML
	private TableColumn<Historique, String> numBadgeHistoriqueColumn;
	@FXML
	private TableColumn<Historique, String> nomHistoriqueColumn;
	@FXML
	private TableColumn<Historique, Integer> numZoneHistoriqueColumn;
	@FXML
	private TableColumn<Historique, String> libelleZoneHistoriqueColumn;
	@FXML
	private TableColumn<Historique, String> dateAccesHistoriqueColumn;

	@FXML
	private void initialize() {

		numBadgeHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().numBadgeProperty());
		nomHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		numZoneHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().numZoneProperty().asObject());
		libelleZoneHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().libelleZoneProperty());
		dateAccesHistoriqueColumn.setCellValueFactory(cellData -> cellData.getValue().dateAccesProperty());
		populateZoneChoiceBox();

	}

	@FXML
	private void searchHistoriques(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			// Get all Employees information
			ObservableList<Historique> historiqueData = HistoriqueDAO.searchHistoriques();
			// Populate Employees on TableView
			populateHistoriques(historiqueData);
		} catch (SQLException e) {
			System.out.println("Error occurred while getting employees information from DB.\n" + e);
			throw e;
		}
	}

	@FXML
	private void populateHistorique(Historique historique) throws ClassNotFoundException {
		// Declare and ObservableList for table view
		ObservableList<Historique> historiqueData = FXCollections.observableArrayList();
		// Add employee to the ObservableList
		historiqueData.add(historique);
		// Set items to the employeeTable
		historiqueTable.setItems(historiqueData);
	}

	@FXML
	private void populateAndShowhistorique(Historique historique) throws ClassNotFoundException {
		if (historique != null) {
			populateHistorique(historique);
			// setEmpInfoToTextArea(autorisation);
		} else {
			// resultArea.setText("This employee does not exist!\n");
		}
		historiqueTable.refresh();
	}

	@FXML
	private void populateHistoriques(ObservableList<Historique> historiqueData) throws ClassNotFoundException {
		// Set items to the employeeTable
		System.out.println(historiqueTable);
		System.out.println(historiqueData);
		historiqueTable.setItems(historiqueData);
		historiqueTable.refresh();
	}

	@FXML
	private void searchHistorique() {
		try {

			ObservableList<Historique> historiqueData;
			// HistoriqueDAO.searchHistorique(numBadgeHistoriqueText.getText(),
			// zoneHistoriqueChoice.getSelectionModel().getSelectedItem().getIdZone(),
			// dateMin.getValue(), dateMax.getValue());
			if (zoneHistoriqueChoice.getSelectionModel().getSelectedItem() == null) {
				if (dateMin.getValue() == null) {
					if (dateMax.getValue() == null) {
						historiqueData = HistoriqueDAO.searchHistoriqueByNumBadge(numBadgeHistoriqueText.getText());
					} else {
						historiqueData = HistoriqueDAO.searchHistoriqueDateMax(numBadgeHistoriqueText.getText(),
								dateMax.getValue());
					}
				} else {
					if (dateMax.getValue() == null) {
						historiqueData = HistoriqueDAO.searchHistoriqueDateMin(numBadgeHistoriqueText.getText(),
								dateMin.getValue());
					} else {
						historiqueData = HistoriqueDAO.searchHistorique(numBadgeHistoriqueText.getText(),
								dateMin.getValue(), dateMax.getValue());
					}
				}
			} else {
				if (dateMin.getValue() == null) {
					if (dateMax.getValue() == null) {
						historiqueData = HistoriqueDAO.searchHistorique(numBadgeHistoriqueText.getText(),
								zoneHistoriqueChoice.getSelectionModel().getSelectedItem().getIdZone());
					} else {
						historiqueData = HistoriqueDAO.searchHistoriqueDateMax(numBadgeHistoriqueText.getText(),
								zoneHistoriqueChoice.getSelectionModel().getSelectedItem().getIdZone(),
								dateMax.getValue());
					}
				} else {
					if (dateMax.getValue() == null) {
						historiqueData = HistoriqueDAO.searchHistoriqueDateMin(numBadgeHistoriqueText.getText(),
								zoneHistoriqueChoice.getSelectionModel().getSelectedItem().getIdZone(),
								dateMin.getValue());
					} else {
						historiqueData = HistoriqueDAO.searchHistorique(numBadgeHistoriqueText.getText(),
								zoneHistoriqueChoice.getSelectionModel().getSelectedItem().getIdZone(),
								dateMin.getValue(), dateMax.getValue());
					}
				}
			}

			populateHistoriques(historiqueData);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void populateZoneChoiceBox() {
		try {
			zoneHistoriqueChoice.getItems().addAll(ZoneDAO.searchZones());
			zoneHistoriqueChoice.setConverter(new StringConverter<Zone>() {

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
