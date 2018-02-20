package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Historique extends Autorisation {

	private StringProperty dateAcces;
	
	public Historique() {
		super();
		dateAcces=new SimpleStringProperty();
		// TODO Auto-generated constructor stub
	}


	
	public String getDateAcces(){
		return dateAcces.get();
	}
	
	public void setDateAcces(String dateAcces){
		this.dateAcces.set(dateAcces);
	}
	
	public StringProperty dateAccesProperty(){
		return dateAcces;
	}
	
	
	
}
