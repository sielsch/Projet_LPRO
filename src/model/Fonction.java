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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFonction == null) ? 0 : idFonction.hashCode());
		result = prime * result + ((libelleFonction == null) ? 0 : libelleFonction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fonction other = (Fonction) obj;
		if (idFonction == null) {
			if (other.idFonction != null)
				return false;
		} else if (! (idFonction.get() == (other.idFonction.get())))
			return false;
		if (libelleFonction == null) {
			if (other.libelleFonction != null)
				return false;
		} else if (!libelleFonction.get().equals(other.libelleFonction.get()))
			return false;
		return true;
	}
	
	
}
