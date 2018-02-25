package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class EmployeDAO{
	
	public static Employe searchEmploye (String numBadge) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employes WHERE numBadge="+numBadge;
 
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
 
            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            Employe employee = getEmployeFromResultSet(rsEmp);
 
            //Return employee object
            return employee;
        } catch (SQLException e) {
            System.out.println("While searching an employee with " + numBadge + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
	
	public static ObservableList<Employe> searchEmployeByNomAndNum(String numBadge, String nom) throws ClassNotFoundException, SQLException{
		String select ="select numBadge, nom, prenom, password, employes.idFonction, libelleFonction from Employes"
				+ " inner join Fonctions on Employes.idFonction=Fonctions.idFonction"
				+ " where numBadge like '%"+numBadge+"%' AND nom like '%"+nom+"%';"; 
		
		try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(select);
 
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Employe> empList = getEmployeeList(rsEmps);
 
            //Return employee object
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
	}
	
	
	private static Employe getEmployeFromResultSet(ResultSet rs) throws SQLException
    {
        Employe emp = null;
        if (rs.next()) {
            emp = new Employe();
            emp.setNumBadge(rs.getString("numBadge"));
            emp.setNom(rs.getString("nom"));
            emp.setPrenom(rs.getString("prenom"));
            emp.setPassword(rs.getString("password"));
            emp.setIdFonction(rs.getInt("idFonction"));
        }
        return emp;
    }
	
	 public static ObservableList<Employe> searchEmployes () throws SQLException, ClassNotFoundException {
	        //Declare a SELECT statement
//	        String selectStmt = "SELECT * FROM employes";
	 
	        String select ="select numBadge, nom, prenom, password, employes.idFonction, libelleFonction from Employes inner join Fonctions on Employes.idFonction=Fonctions.idFonction;";
	        //Execute SELECT statement
	        try {
	            //Get ResultSet from dbExecuteQuery method
	            ResultSet rsEmps = DBUtil.dbExecuteQuery(select);
	 
	            //Send ResultSet to the getEmployeeList method and get employee object
	            ObservableList<Employe> empList = getEmployeeList(rsEmps);
	 
	            //Return employee object
	            return empList;
	        } catch (SQLException e) {
	            System.out.println("SQL select operation has been failed: " + e);
	            //Return exception
	            throw e;
	        }
	    }
	 
	 
	 private static ObservableList<Employe> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
	        //Declare a observable List which comprises of Employee objects
	        ObservableList<Employe> empList = FXCollections.observableArrayList();
	 
	        while (rs.next()) {
	        	 Employe emp = new Employe();
	             emp.setNumBadge(rs.getString("numBadge"));
	             emp.setNom(rs.getString("nom"));
	             emp.setPrenom(rs.getString("prenom"));
	             emp.setPassword(rs.getString("password"));
	             emp.setIdFonction(rs.getInt("idFonction"));
	             emp.setLibelleFonction(rs.getString("libelleFonction"));
	            //Add employee to the ObservableList
	            empList.add(emp);
	        }
	        //return empList (ObservableList of Employees)
	        return empList;
	    }
	 
	 public static void deleteEmpWithId (String numBadge) throws SQLException, ClassNotFoundException {

	        String delete = "delete from employes where numBadge='"+numBadge+"';";
	               
	 

	        try {
	            DBUtil.dbExecuteUpdate(delete);
	        } catch (SQLException e) {
	            System.out.print("Error occurred while DELETE Operation: " + e);
	            throw e;
	        }
	    }
	 
	    //*************************************
	    //INSERT an employee
	    //*************************************
	    public static void insertEmp (String numBadge, String nom, String prenom, String password, int idFonction ) throws SQLException, ClassNotFoundException {
	 
	        String insert =
	                        "INSERT INTO employes\n" +
	                        "(numBadge, nom, prenom, password, idFonction)\n" +
	                        "VALUES\n" +
	                        "('"+numBadge+"', '"+nom+"', '"+prenom+"','"+password+"','"+idFonction+"');\n";


	        try {
	            DBUtil.dbExecuteUpdate(insert);
	        } catch (SQLException e) {
	            System.out.print("Error occurred while DELETE Operation: " + e);
	            throw e;
	        }
	    }
	    
	    
	    public static void updateEmploye(String numBadge, String nom, String prenom, String password, int idFonction) throws ClassNotFoundException, SQLException{
	    	String update="Update Employes set nom='"+nom+"', prenom='"+prenom+"', password='"+password+"',idFonction="+idFonction
	    			+ " where numBadge='"+numBadge+"';";
	    	System.out.println(update);
	    	DBUtil.dbExecuteUpdate(update);
	    }
	    

	}
	 
	 

