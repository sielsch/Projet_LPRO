package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class ZoneDAO {
	
	public static ObservableList<Zone> searchZones() throws SQLException, ClassNotFoundException {

		String select = "select * from Zones";

		try {

			ResultSet rsEmps = DBUtil.dbExecuteQuery(select);


			ObservableList<Zone> zoneList = getZoneList(rsEmps);


			return zoneList;
		} catch (SQLException e) {
			System.out.println("SQL select operation has been failed: " + e);

			throw e;
		}
	}

	private static ObservableList<Zone> getZoneList(ResultSet rs) throws SQLException, ClassNotFoundException {

		ObservableList<Zone> zoneList = FXCollections.observableArrayList();

		while (rs.next()) {
			Zone zone = new Zone();
			zone.setIdZone(rs.getInt("idZone"));
			zone.setLibelleZone(rs.getString("libelleZone"));

			zoneList.add(zone);
		}

		return zoneList;
	}

	public static Zone searchZoneById(int idZone) throws ClassNotFoundException, SQLException{
		String select="select * from Zones where idZone="+idZone+";";
		 try {
	            ResultSet rs = DBUtil.dbExecuteQuery(select); 
	            Zone zone = getZoneFromResultSet(rs);
	            return zone;
	        } catch (SQLException e) {
	            System.out.println("While searching a fonction with " + idZone + " id, an error occurred: " + e);
	            //Return exception
	            throw e;
	        }
		
	}
	
	private static Zone getZoneFromResultSet(ResultSet rs) throws SQLException{
		 Zone zone = null;
	        if (rs.next()) {
	        	zone = new Zone();
	        	zone.setIdZone(rs.getInt("idZone"));
	        	zone.setLibelleZone(rs.getString("libelleZones"));
	        }
	        return zone;
	}
}
