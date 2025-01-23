package com.feedback;

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
	private static final String query = "UPDATE users_feedback set name=?, email=?, phone_number=?, age=?, grade=?, msg=? where user_id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		//Get the id of record
	    int id = Integer.parseInt(req.getParameter("id"));
		
	    //get the edited data
	    String name = req.getParameter("name");
	    String email = req.getParameter("email");
	    Long phone_number = Long.parseLong(req.getParameter("phone"));
	    int age = Integer.parseInt(req.getParameter("age"));
	    String grade = req.getParameter("grade");
	    String msg = req.getParameter("msg");
	    
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///feedback_data", "root", "Prakriti@0094");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setString(1, name); 
			ps.setString(2, email); 
			ps.setLong(3, phone_number); 
			ps.setInt(4, age); 
			ps.setString(5, grade); 
			ps.setString(6, msg); 
			ps.setInt(7, id);
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record is Edited successfully</h2>");
			}else {
				pw.println("<h2>Record is not Edited</h2>");
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



