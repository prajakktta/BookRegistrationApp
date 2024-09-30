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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query = "INSERT INTO BOOKDATA(NAME,EDITION,PRICE) VALUES(?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter 
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Get the book information
		String bookName = req.getParameter("bookName");
		String bookEdition = req.getParameter("bookEdition");
		float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		//Load JDBC Driver
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
			int count = ps.executeUpdate();
			if(count == 1) {
				pw.println("<h2> Record is Registerted Sucessfully </h2>");
			}
			else {
				pw.println("<h2> Record not Registerted </h2>");
			}
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h2>" + se.getMessage() + "</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h2>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href ='home.html'>Homet</a>");
		pw.println("<br>");
		pw.println("<a href ='BookList'>Book List</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
	}

}