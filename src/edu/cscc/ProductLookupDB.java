package edu.cscc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductLookupDB {
	
	private DataSource datasource;
	
	static final String sql = "  SELECT p.ProductID, p.ProductName, p.UnitPrice, p.UnitsInStock, p.Discontinued, c.CategoryName "
			+ "FROM Products p " + "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID "
			+ "WHERE UnitPrice >= ? and UnitPrice <= ? "
			+ "and UnitsInStock > ? and CAST(Discontinued AS INT) <> ? "
			+ "ORDER BY p.ProductName ASC";
	
	// Singleton pattern - there is only ONE of these object shared with application
	// This means ONE connection to database in application used by this object
	private static ProductLookupDB instance = null;
	
	/**
	 * Return the one instance of ProductLookupDB 
	 * Create one if it doesn't exist
	 * @return ProductLookupDB object
	 * @throws NamingException 
	 */
	public static ProductLookupDB getInstance() throws NamingException {
		if (instance == null) {
			instance = new ProductLookupDB();
		}
		return instance;
	}
	
	/**
	 * Private constructor
	 * @throws NamingException 
	 */
	private ProductLookupDB() throws NamingException { 
		Context context = new InitialContext();
		datasource = (DataSource) context.lookup("java:comp/env/jdbc/Northwind");
	}

	/**
	 * Get a database connection from the data source
	 * @return Connection
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException  {
		Connection conn = datasource.getConnection();
		return conn;
	}

	/**
	 * Lookup products
	 * @param lower lower price bound
	 * @param upper upper price bound
	 * @return list of products
	 */
	public List<Product> lookup(double lower, double upper, boolean inStockOnly, boolean omitDiscontinued) {
		PreparedStatement stmt = null;		
		List<Product> list = new ArrayList<>();
		
		try (Connection conn = getConnection();) {
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lower);
			stmt.setDouble(2, upper);
			stmt.setInt(3, inStockOnly? 0 : -1);
			stmt.setInt(4, omitDiscontinued? 1 : -1);			
			ResultSet rs = stmt.executeQuery();			

			while (rs.next()) {
				long productId = rs.getLong(1);
				String pname = rs.getString(2);
				double unitPrice = rs.getDouble(3);
				boolean inStock = (rs.getLong(4) > 0);
				boolean discontinued = (rs.getLong(5) > 0);
				String category = rs.getString(6);
				
				Product prod = new Product(productId, pname, unitPrice, inStock, discontinued, category);
				list.add(prod);	
				
			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			closePreparedStmt(stmt);
		}

		return list;
	}

	/**
	 * to close the prepared statement
	 * @param PreparedStatement
	 */
	private static void closePreparedStmt(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				/* ignore */ }
		}
	}
}
