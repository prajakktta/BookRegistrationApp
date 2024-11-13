package testsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class jdbctest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Step 1: Register the driver
		Class.forName("org.postgresql.Driver"); //This can possibly throw ClassNotFound Exception
		
		//Step 2: Establish the database connection
		Connection con = DriverManager.getConnection("");//to establish the connection write the jbdc driver and the database name and password with the correct port numbers and user name
		
		System.out.println("JDBC connected successfully");
		}
}
