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


@WebServlet("/Submit")
public class FeedbackMain extends HttpServlet {
	private static final String query = "INSERT INTO users_feedback(name, email, phone_number, Age, grade, msg) VALUES(?,?,?,?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		//Get the book info from html page
		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		Long phone_number = Long.parseLong(req.getParameter("phone"));
		int age = Integer.parseInt(req.getParameter("age"));
		String experience = req.getParameter("experience");
		String message = req.getParameter("message");
		
		
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
			ps.setString(5, experience);
			ps.setString(6, message);
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record is submitted successfully</h2>");
			}
			else {
				pw.println("<h2>Record is not submitted</h2>");
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
