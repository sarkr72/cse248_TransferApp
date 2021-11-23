package model;

import java.io.Serializable;

public class Address implements Serializable, Uniteable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zip;
	private String state;
	private String city;
	public Address(String zip, String state, String city) {
		super();
		this.zip = zip;
		this.state = state;
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public String toString() {
		return "Address [zip=" + zip + ", state=" + state + ", city=" + city+"]";
	}
	
	
}
