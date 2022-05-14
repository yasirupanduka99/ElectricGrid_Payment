package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	//Database Connection Methods
	public Connection connect() 
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electric", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
	}
	
	
	
	
	
	
	

	//Read Data Method
	public String readPayment() {
		String output = "";
		
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			 output = "<table border='1'class=\"table table-striped\"><tr><th>Payment Code</th>" 
					 + "<th>Payment Status</th>"
					 + "<th>Payment Date</th>" 
					 + "<th>Payment Card No.</th>" 
					 + "<th>Payment CVV</th>" 
					 + "<th>Payment Amount</th>" 
					 + "<th>Update</th><th>Remove</th></tr>";  

			 
			 
			 String query = "select * from payment"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
					 String p_ID = Integer.toString(rs.getInt("p_ID")); 
				 	 String p_code = rs.getString("p_code"); 
					 String p_status = rs.getString("p_status"); 
					 String p_date = rs.getString("p_date"); 
					 String p_crdnumber = rs.getString("p_crdnumber"); 
					 String p_cvv = rs.getString("p_cvv"); 
					 String p_amount = rs.getString("p_amount"); 
					 
					 // Add a row into the html table 
					 output += "<tr><td>" + p_code + "</td>"; 
					 output += "<td>" + p_status + "</td>"; 
					 output += "<td>" + p_date + "</td>";
					 output += "<td>" + p_crdnumber + "</td>";
					 output += "<td>" + p_cvv + "</td>"; 
					 output += "<td>" + p_amount + "</td>"; 
					 
					 // buttons				 
					 output += "<td><input name='btnUpdate' " + " type='button' value='Update' " + " class='btnUpdate btn btn-outline-secondary' data-itemid='" + p_ID + "'></td>"
							 + "<td><input name='btnRemove' " + " type='button' value='Remove' " + " class='btnRemove btn btn-outline-danger' data-itemid='" + p_ID + "'>"
							 + "</td></tr>";
					 
			 } 
			 
			 con.close(); 
			 // Complete the html table
			 output += "</table>";

			
		} 
		catch (Exception e) 
		{ 
			output = "Error while reading the Payments."; 
			System.err.println(e.getMessage()); 
		}
		
		return output;
	}
	
	
	
	
	
	
	//Insert Data Method
	public String insertPayment(String p_code, String p_status, String p_date, String p_crdnumber, String p_cvv, String p_amount) {
		
		String output = ""; 
		try
		 { 
			Connection con = connect(); 
			
			if (con == null) 
			{ 
				return "Error while connecting to the database"; 
			} 
		 
			// create a prepared statement
			String query = " insert into payment (`p_ID`, `p_code`,`p_status`,`p_date`,`p_crdnumber`,`p_cvv`,`p_amount`)"+ " values (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, p_code); 
			preparedStmt.setString(3, p_status); 
			preparedStmt.setString(4, p_date); 
			preparedStmt.setString(5, p_crdnumber); 
			preparedStmt.setString(6, p_cvv); 
			preparedStmt.setString(7, p_amount);
			
			//execute the statement
			preparedStmt.execute(); 
			con.close(); // close the connection
			 
			output = "Payment Details Inserted successfully!"; 
			
			String newPayment = readPayment(); 
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} 
		
		
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment details.\"}";
			System.err.println(e.getMessage()); 
		} 
		
		return output;
	}
	
	
	
	
	

	//update method
	public String updatePayment(String p_ID, String p_code, String p_status, String p_date, String p_crdnumber, String p_cvv, String p_amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET p_code=?, p_status=?, p_date=?, p_crdnumber=?, p_cvv=?, p_amount=?" + "WHERE p_ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, p_code);
			preparedStmt.setString(2, p_status);
			preparedStmt.setString(3, p_date);
			preparedStmt.setString(4, p_crdnumber);
			preparedStmt.setString(5, p_cvv);
			preparedStmt.setString(6, p_amount);
			preparedStmt.setInt(7, Integer.parseInt(p_ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Payment Details Updated successfully!";
			
			
			String newPayment = readPayment(); 
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}"; 
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Payment Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	
	
	
	
	
	
	//Delete Methods
	public String deletePayment(String p_ID)
	{ 
	 String output = ""; 
	 
	 try
	 { 
		 Connection con = connect(); 
		 if (con == null) { 
			 return "Error while connecting to the database for deleting."; 
		 }
		 
		 
		 // create a prepared statement
		 String query = "delete from payment where p_ID=?";
		 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(p_ID)); 
		  
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 output = "Payment Detail Deleted successfully!";
		 
		 String newPayment = readPayment(); 
		 output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";

	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while deleting the Payment detail.\"}";
		 System.err.println(e.getMessage()); 
	 }
	 
	 return output; 
	}

	
}
