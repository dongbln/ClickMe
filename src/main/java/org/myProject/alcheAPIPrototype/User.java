/**
 * 
 */
package org.myProject.alcheAPIPrototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tsi Tawatchai Siripanya
 * 
 */
public class User {
	private String id;
	//private List<Category> categories;
	private List<String> categories;

	public User(String id) {
		this.id = id;
		//this.categories=new ArrayList<Category>();
		this.categories=new ArrayList<String>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", categories=" + categories + "]";
	}
 
	

}
