package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employe {

	private StringProperty numBadge;
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty password;
	private IntegerProperty idFonction;
	private StringProperty libelleFonction;
	
	public Employe(){
		numBadge= new SimpleStringProperty();
		nom= new SimpleStringProperty();
		prenom= new SimpleStringProperty();
		password= new SimpleStringProperty();
		idFonction= new SimpleIntegerProperty();
		libelleFonction = new SimpleStringProperty();		
	}

	public String getNumBadge() {
		return numBadge.get();
	}

	public void setNumBadge(String numBadge) {
		this.numBadge.set(numBadge);;
	}

	public StringProperty numBadgeProperty(){
        return numBadge;
    }
	
	public String getNom() {
		return nom.get();
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

	public StringProperty nomProperty(){
        return nom;
    }
	
	public String getPrenom() {
		return prenom.get();
	}

	public void setPrenom(String prenom) {
		this.prenom.set(prenom); 
	}

	public StringProperty prenomProperty(){
        return prenom;
    }
	
	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public StringProperty passwordProperty(){
        return password;
    }
	
	
	public int getIdFonction() {
		return idFonction.get();
	}

	public void setIdFonction(int idFonction) {
		this.idFonction.set(idFonction);
	}

	public IntegerProperty idFonctionProperty(){
        return idFonction;
    }
	
	public String getLibelleFonction()
		{
		return libelleFonction.get();
	}
	
	public void setLibelleFonction(String libelleFonction){
		this.libelleFonction.set(libelleFonction);
	}
	
	public StringProperty libelleFonctinoProperty(){
		return libelleFonction;
	}
	
	public String toString(){
		return libelleFonction.get();
	}
}
