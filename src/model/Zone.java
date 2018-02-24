package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Zone {

	IntegerProperty idZone;
	StringProperty libelleZone;

	public Zone() {
		idZone=new SimpleIntegerProperty();
		libelleZone=new SimpleStringProperty();
	}

	public int getIdZone() {
		return idZone.get();
	}

	public void setIdZone(int idZone) {
		this.idZone.set(idZone);
	}

	public IntegerProperty idZoneProperty() {
		return idZone;
	}
	
	public String getLibelleZone() {
		return libelleZone.get();
	}

	public void setLibelleZone(String libelleZone) {
		this.libelleZone.set(libelleZone);
	}

	public StringProperty libelleZoneProperty() {
		return libelleZone;
	}
}
