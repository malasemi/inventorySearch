package edu.cscc;


/**
 * Looking up for products in the inventory
 * @author Majed Alasemi
 * @version 1.0
 */
public class Product {	
	private long productId;
	private String name;
	private double unitPrice;
	private boolean inStock;
	private boolean discontinued;
	private String category;
	
	/**
	 * Constructor
	 * @param productId
	 * @param name
	 * @param unitPrice
	 * @param inStock
	 * @param discontinued
	 * @param category
	 */
	public Product(long productId, String name, double unitPrice, boolean inStock, boolean discontinued,
			String category) {
		super();
		this.productId = productId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.inStock = inStock;
		this.discontinued = discontinued;
		this.category = category;
	}
	
	/**
	 * @return productId
	 */
	public long getProductId() {
		return productId;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * @return inStock
	 */
	public boolean isInStock() {
		return inStock;
	}

	/**
	 * @return discontinued
	 */
	public boolean isDiscontinued() {
		return discontinued;
	}

	/**
	 * @return category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * @return This method returns a string of the ID, name, price, in-stock, Discontinued, and category of the product
	 */
	public String toString() {
		return String.format("ID: %d Name: %s Price: $%.2f In-Stock: %s Discontinued: %s Category: %s",
				productId,
				name,
				unitPrice,
				(inStock?"T":"F"),
				(discontinued?"T":"F"),
				category);
	}
}
