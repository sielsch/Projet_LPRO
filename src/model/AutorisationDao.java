package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class AutorisationDao {

	public static Autorisation searchAutorisationParZone(int numZone) throws ClassNotFoundException, SQLException {

		String select = "select AutorisationAcces.numBadge, employes.nom, AutorisationAcces.idZone, zones.libelleZone "
				+ "from AutorisationAcces " + "inner join Employes on Employes.numBadge=AutorisationAcces.numBadge "
				+ "inner join Zones on Zones.idZone=AutorisationAcces.idZone" + "where Autorisation.idZone=" + numZone
				+ ";";

		try {
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			Autorisation autorisation = getAutorisationFromResultSet(resultSet);
			return autorisation;

		} catch (SQLException e) {
			System.out.println("While searching an autorisation with " + numZone + " id zone, an error occurred: " + e);
			// Return exception
			throw e;
		}

	}

	public static Autorisation searchAutorisationParEmploye(String numEmploye)
			throws ClassNotFoundException, SQLException {

		String select = "select AutorisationAcces.numBadge, employes.nom, AutorisationAcces.idZone, zones.libelleZone "
				+ "from AutorisationAcces " + "inner join Employes on Employes.numBadge=AutorisationAcces.numBadge "
				+ "inner join Zones on Zones.idZone=AutorisationAcces.idZone" + "where Autorisation.numBadge='"
				+ numEmploye + "';";

		try {
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			Autorisation autorisation = getAutorisationFromResultSet(resultSet);
			return autorisation;

		} catch (SQLException e) {
			System.out.println(
					"While searching an autorisation with " + numEmploye + " employe num, an error occurred: " + e);
			// Return exception
			throw e;
		}
	}

	public static Autorisation getAutorisationFromResultSet(ResultSet resultSet) throws SQLException {
		Autorisation autorisation = new Autorisation();

		if (resultSet.next()) {
			autorisation.setLibelleZone(resultSet.getString("libelleZone"));
			autorisation.setNom(resultSet.getString("nom"));
			autorisation.setNumBadge(resultSet.getString("numBadge"));
			autorisation.setNumZone(resultSet.getInt("idZone"));
		}

		return autorisation;

	}

	public static ObservableList<Autorisation> searchAutorisations() throws ClassNotFoundException, SQLException {

		String select = "select AutorisationAcces.numBadge, employes.nom, AutorisationAcces.idZone, zones.libelleZone "
				+ "from AutorisationAcces " + "inner join Employes on Employes.numBadge=AutorisationAcces.numBadge "
				+ "inner join Zones on Zones.idZone=AutorisationAcces.idZone; ";
		try {
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			ObservableList<Autorisation> listAutorisation = getAutorisationList(resultSet);
			
			return listAutorisation;
		} catch (SQLException e) {
			throw e;
		}
	}

	public static ObservableList<Autorisation> getAutorisationList(ResultSet resultSet) throws SQLException {

		ObservableList<Autorisation> autorisationList = FXCollections.observableArrayList();

		while (resultSet.next()) {
			Autorisation autorisation = new Autorisation();
			autorisation.setNumBadge(resultSet.getString("numBadge"));
			autorisation.setNom(resultSet.getString("nom"));
			autorisation.setLibelleZone(resultSet.getString("libelleZone"));
			autorisation.setNumZone(resultSet.getInt("idZone"));

			autorisationList.add(autorisation);
		}

		return autorisationList;

	}
}
