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

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
	private static final String query = "UPDATE BOOKDATA SET  NAME=?,EDITION =?,PRICE =? where ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter 
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Get the id of record 
		Long id = Long.valueOf(req.getParameter("id"));
		//get edited data 
		String bookName = req.getParameter("bookName");
		String bookEdition = req.getParameter("bookEdition");
		float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		//Load JDBC Driver
		
		//pw.println(req);
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		//Generate a connection 
		try(Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/BookRegistrationApp", "postgres", "pilsch");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setString(1, bookName);
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			ps.setLong(4, id);
			int count  = ps.executeUpdate();
			if(count == 1) {
				pw.println("<h3>Record is Editied Successfully </h3>");
			}
			else {
				pw.println("<h3>Record is not Editied </h3>");
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
