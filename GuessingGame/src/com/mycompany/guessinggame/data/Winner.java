package com.mycompany.guessinggame.data;

/**
 * @author Oleksandr Lysun
 * 
 */
public class Winner {
	// data fields
	private String name;
	private Integer numOfTries;
	
	// default constructor
	public Winner() {
	}
	
	// constructor that takes parameters: name, number of tries
	public Winner(String name, Integer numOfTries) {
		this.name = name;
		this.numOfTries = numOfTries;
	}
	
	// getters and setters
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the numOfTries
	 */
	public Integer getNumOfTries() {
		return numOfTries;
	}
	
	/**
	 * @param numOfTries
	 *            the numOfTries to set
	 */
	public void setNumOfTries(Integer numOfTries) {
		this.numOfTries = numOfTries;
	}

	@Override
	public String toString() {
		return "Winner [name=" + name + ", numOfTries=" + numOfTries + "]";
	}
}
