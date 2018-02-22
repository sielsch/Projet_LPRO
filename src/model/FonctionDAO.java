package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class FonctionDAO {

	public static ObservableList<Fonction> searchFonctions() throws SQLException, ClassNotFoundException {

		String select = "select * from Fonctions";

		try {

			ResultSet rsEmps = DBUtil.dbExecuteQuery(select);


			ObservableList<Fonction> fonctionList = getFonctionsList(rsEmps);


			return fonctionList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);

			throw e;
		}
	}

	private static ObservableList<Fonction> getFonctionsList(ResultSet rs) throws SQLException, ClassNotFoundException {

		ObservableList<Fonction> fonctionList = FXCollections.observableArrayList();

		while (rs.next()) {
			Fonction fonction = new Fonction();
			fonction.setIdFonction(rs.getInt("idFonction"));
			fonction.setLibelleFonction(rs.getString("libelleFonction"));

			fonctionList.add(fonction);
		}

		return fonctionList;
	}

}
