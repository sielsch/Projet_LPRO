package model;

public class AutorisationAcces {
	
	private int idEmploye;
	private int idZone;
	
	
	public AutorisationAcces(){
		
	}
	
	public AutorisationAcces(int idEmploye, int idZone) {
		super();
		this.idEmploye = idEmploye;
		this.idZone = idZone;
	}
	public int getIdEmploye() {
		return idEmploye;
	}
	public void setIdEmploye(int idEmploye) {
		this.idEmploye = idEmploye;
	}
	public int getIdZone() {
		return idZone;
	}
	public void setIdZone(int idZone) {
		this.idZone = idZone;
	}
	
	
	
}
