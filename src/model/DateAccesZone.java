package model;

import java.sql.Date;

public class DateAccesZone {

	private int idEmploye;
	private int idZone;
	private Date date;

	public DateAccesZone() {

	}

	public DateAccesZone(int idEmploye, int idZone, Date date) {
		super();
		this.idEmploye = idEmploye;
		this.idZone = idZone;
		this.date = date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
