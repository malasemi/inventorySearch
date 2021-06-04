package edu.cscc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;


@WebServlet("/TestConnection")
public class TestConnectionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private final static String SQL = "SELECT TOP 10 ProductName FROM Products";
	
	@Resource(name = "jdbc/Northwind")
	private DataSource datasource;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
				
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			while(rs.next()) {
				out.println(rs.getString(1));
			}
		} catch(Exception ex) {
			out.println(ex.getMessage());
		}
		
	}
	

}
