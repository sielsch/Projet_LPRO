package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
	public static ObservableList<Historique> searchHistorique(String numBadge, int idZone, LocalDate dateMin,
			LocalDate dateMax) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%' and DateAccesZone.idZone="+idZone
				+ " and dateAcces between '"+dateMin.format(DateTimeFormatter.ISO_LOCAL_DATE)+"' and '"+dateMax.format(DateTimeFormatter.ISO_LOCAL_DATE)+"';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	public static ObservableList<Historique> searchHistorique(String numBadge, LocalDate dateMin,
			LocalDate dateMax) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%'"
				+ " and dateAcces between '"+dateMin.format(DateTimeFormatter.ISO_LOCAL_DATE)+"' and '"+dateMax.format(DateTimeFormatter.ISO_LOCAL_DATE)+"';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	public static ObservableList<Historique> searchHistoriqueDateMin(String numBadge, int idZone, LocalDate dateMin) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%' and DateAccesZone.idZone="+idZone
				+ " and dateAcces >= '"+dateMin.format(DateTimeFormatter.ISO_LOCAL_DATE)+"';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	public static ObservableList<Historique> searchHistoriqueDateMax(String numBadge, int idZone, 
			LocalDate dateMax) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%' and DateAccesZone.idZone="+idZone
				+ " and dateAcces <= '"+dateMax.format(DateTimeFormatter.ISO_LOCAL_DATE)+"';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	public static ObservableList<Historique> searchHistoriqueDateMin(String numBadge, LocalDate dateMin) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%' and "
				+ " dateAcces >= '"+dateMin.format(DateTimeFormatter.ISO_LOCAL_DATE)+"';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	public static ObservableList<Historique> searchHistoriqueDateMax(String numBadge, 
			LocalDate dateMax) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%' and "
				+ " dateAcces <= '"+dateMax.format(DateTimeFormatter.ISO_LOCAL_DATE)+"';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	
	public static ObservableList<Historique> searchHistorique(String numBadge, int idZone) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%' and DateAccesZone.idZone="+idZone
				+";";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}
	
	public static ObservableList<Historique> searchHistoriqueByNumBadge(String numBadge) throws ClassNotFoundException, SQLException{
		String select="select DateAccesZone.numBadge, employes.nom, DateAccesZone.idZone, zones.libelleZone, dateAcces "
				+ "from DateAccesZone "
				+ "inner join Employes on Employes.numBadge=DateAccesZone.numBadge "
				+ "inner join Zones on Zones.idZone=DateAccesZone.idZone "
				+ "where DateAccesZone.numBadge like '%"+numBadge+"%';";
		try{
			ResultSet resultSet = DBUtil.dbExecuteQuery(select);
			return getHistoriqueList(resultSet);
		} catch (SQLException e){
			throw e;
		}
	}

	
}
