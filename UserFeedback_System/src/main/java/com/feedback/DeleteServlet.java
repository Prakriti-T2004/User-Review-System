package com.feedback;

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
	private static final String query = "delete from users_feedback where user_id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		//Get the id of record
	    int id = Integer.parseInt(req.getParameter("id"));
	    
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///feedback_data", "root", "Prakriti@0094");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setInt(1,  id);
			
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record is Deleted successfully</h2>");
			}else {
				pw.println("<h2>Record is not Deleted</h2>");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
		
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='feedbacklist'>Feedback List</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
