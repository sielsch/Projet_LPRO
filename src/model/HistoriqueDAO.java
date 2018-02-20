package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class HistoriqueDAO {

	
	
	public static ObservableList<Historique> searchHistoriques() throws ClassNotFoundException, SQLException{
		String select ="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone;";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
		
	}
	
	
	public static ObservableList<Historique> getHistoriqueList(ResultSet resultSet) throws SQLException {
		
		ObservableList<Historique> list = FXCollections.observableArrayList();
		
		while(resultSet.next()){
			Historique historique = new Historique();
			System.out.println(resultSet.getTimestamp("dateAcces").toString());
				historique.setNumBadge(resultSet.getString("numBadge"));
				historique.setNom(resultSet.getString("nom"));
				historique.setLibelleZone(resultSet.getString("libelleZone"));
				historique.setNumZone(resultSet.getInt("idZone"));
				historique.setDateAcces(resultSet.getTimestamp("dateAcces").toString());

				list.add(historique);
			
		}
		return list;
		
	}
	
	
	public static void insertHistoriqueNow(String numBadge, int numZone) throws ClassNotFoundException, SQLException{
		
		String insert ="insert into DateAccesZone values ("+numBadge+", "+numZone+", CURRENT_TIMESTAMP);";
		DBUtil.dbExecuteUpdate(insert);
		
	}
	
}
