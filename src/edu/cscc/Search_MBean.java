package edu.cscc;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;

@ManagedBean(name="search")
@RequestScoped
public class Search_MBean {
	private double lower;
	private double upper;
	private boolean inStockOnly;
	private boolean omitDiscontinued;
	private List<Product> productList;
	
	public Search_MBean() { }

	public double getLower() {
		return lower;
	}

	public void setLower(double lower) {
		this.lower = lower;
	}

	public double getUpper() {
		return upper;
	}

	public void setUpper(double upper) {
		this.upper = upper;
	}	
	
	public boolean isInStockOnly() {
		return inStockOnly;
	}

	public void setInStockOnly(boolean inStockOnly) {
		this.inStockOnly = inStockOnly;
	}

	public boolean isOmitDiscontinued() {
		return omitDiscontinued;
	}

	public void setOmitDiscontinued(boolean omitDiscontinued) {
		this.omitDiscontinued = omitDiscontinued;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	/**
	 * A managed beans method to look up a product
	 * @throws NamingException
	 */
	public void lookup() throws NamingException {
		ProductLookupDB plkup = ProductLookupDB.getInstance();
		productList = plkup.lookup(lower,upper,inStockOnly,omitDiscontinued);
	}
}
