import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String query = "DELETE from BOOKDATA where ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter 
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Get the id of record 
		long id = Long.parseLong(req.getParameter("id"));
		//Load JDBC Driver
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		//Generate a connection 
		try(Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/BookRegistrationApp", "postgres", "pilsch");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setLong(1, id);
			
			int count  = ps.executeUpdate();
			if(count == 1) {
				pw.println("<h3>Record is Deleteded Successfully </h3>");
			}
			else {
				pw.println("<h3>Record is not Deleted </h3>");
			}
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h2>" + se.getMessage() + "</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h2>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href ='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href ='BookList'>Book List</a>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
	}

}
