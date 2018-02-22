package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fonction {

	IntegerProperty idFonction;
	StringProperty libelleFonction;
	
	public Fonction() {
		idFonction= new SimpleIntegerProperty();
		libelleFonction = new SimpleStringProperty();
	}
	
	public String getLibelleFonction() {
		return libelleFonction.get();
	}

	public void setLibelleFonction(String libelleFonction) {
		this.libelleFonction.set(libelleFonction);;
	}

	public StringProperty libelleFonctionProperty(){
        return libelleFonction;
    }
	
	
	public int getIdFonction(){
		return idFonction.get();
	}
	
	public void setIdFonction(int idFonction){
		this.idFonction.set(idFonction);
	}
	
	public IntegerProperty idFonctionProperty(){
		return idFonction;
	}
}
