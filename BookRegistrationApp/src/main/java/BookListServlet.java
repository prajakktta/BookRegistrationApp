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

@WebServlet("/BookList")
public class BookListServlet extends HttpServlet {
	private static final String query = "SELECT ID,NAME,EDITION,PRICE FROM BOOKDATA";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter 
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Load JDBC Driver
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		//Generate a connection 
		try(Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/BookRegistrationApp", "postgres", "pilsch");
				PreparedStatement ps = con.prepareStatement(query);){
			ResultSet rs = ps.executeQuery();
			pw.println("<table border = '1' align = 'center'>");
			pw.println("<tr>");
			pw.println("<th>Book Id</th>");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Edition</th>");
			pw.println("<th>Book Price</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
			
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" + rs.getInt(1)+ "</td>");
				pw.println("<td>" + rs.getString(2)+ "</td>");
				pw.println("<td>" + rs.getString(3)+ "</td>");
				pw.println("<td>" + rs.getFloat(4)+ "</td>");
				pw.println("<td><a href = 'editScreen?id="+rs.getInt(1)+"'>Edit</a> </td>");
				pw.println("<td><a href = 'deleteurl?id="+rs.getInt(1)+"'>Delete</a> </td>");
				pw.println("</tr>");
				
			}
			pw.println("</table>");
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
