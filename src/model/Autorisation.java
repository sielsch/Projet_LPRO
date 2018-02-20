package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Autorisation {

	private StringProperty numBadge;
	private StringProperty nom;
	private IntegerProperty numZone;
	private StringProperty libelleZone;
	
	public Autorisation(){
		numBadge= new SimpleStringProperty();
		nom=new SimpleStringProperty();
		numZone = new SimpleIntegerProperty();
		libelleZone= new SimpleStringProperty();
	}
	
	
	public String getNumBadge(){
		return numBadge.get();
	}
	
	public void setNumBadge(String numBadge){
		this.numBadge.set(numBadge);
	}
	
	public StringProperty numBadgeProperty(){
		return numBadge;
	}
	
	
	
	public String getNom(){
		return numBadge.get();
	}
	
	public void setNom(String nom){
		this.nom.set(nom);
	}
	
	public StringProperty nomProperty(){
		return nom;
	}
	
	
	public int getNumZone(){
		return numZone.get();
	}
	
	public void setNumZone(int numZone){
		this.numZone.set(numZone);
	}
	
	public IntegerProperty numZoneProperty(){
		return numZone;
	}
	
	public String getLibelleZone(){
		return libelleZone.get();
	}
	
	public void setLibelleZone(String libelleZone){
		this.libelleZone.set(libelleZone);
	}
	
	public StringProperty libelleZoneProperty(){
		return libelleZone;
	}
	
	
	
}
