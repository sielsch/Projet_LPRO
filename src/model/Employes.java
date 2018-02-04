package model;

public class Employes {

	private int idEmploye;
	private String nom;
	private String prenom;
	private String password;
	private int idFonction;
	
	
	public Employes(){
		
	}
	
	public Employes(int idEmploye, String nom, String prenom, String password, int idFonction) {
		this.idEmploye = idEmploye;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.idFonction = idFonction;
	}
	
	public int getIdEmploye() {
		return idEmploye;
	}
	public void setIdEmploye(int idEmploye) {
		this.idEmploye = idEmploye;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIdFonction() {
		return idFonction;
	}
	public void setIdFonction(int idFonction) {
		this.idFonction = idFonction;
	}
	
	
	
}
