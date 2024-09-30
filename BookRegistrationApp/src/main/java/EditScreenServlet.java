import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final String query = "SELECT NAME,EDITION,PRICE FROM BOOKDATA where ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter 
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Get the id of record 
		Long id = Long.valueOf(req.getParameter("id"));
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
			ResultSet rs = ps.executeQuery();
			rs.next();
			pw.println("<form action = 'editurl?id="+id+" ' method = 'post'>");
			pw.println("<table align ='center'>");
			pw.println("<tr>");
			pw.println("<td>Book Name</td>");
			pw.println("<td><input type = 'text' name='bookName' value = '"+rs.getString(1)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Book Edition</td>");
			pw.println("<td><input type = 'text' name='bookEdition' value='"+rs.getString(2)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Book Price</td>");
			pw.println("<td><input type = 'text' name = 'bookPrice' value='"+rs.getFloat(3)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type = 'submit' value = 'Edit'></td>");
			pw.println("<td><input type = 'reset' value = 'cancel'></td>");
			pw.println("</tr>");			
			pw.println("</table>");
			pw.println("</form>");
			
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h2>" + se.getMessage() + "</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h2>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href ='home.html'>Home</a>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
	}

}
