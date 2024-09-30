package testsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class jdbctest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Step 1: Register the driver
		Class.forName("org.postgresql.Driver"); //This can possibly throw ClassNotFound Exception
		
		//Step 2: Establish the database connection
		Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/BookRegistrationApp", "postgres", "pilsch");
		
		System.out.println("JDBC connected successfully");
		}
}
