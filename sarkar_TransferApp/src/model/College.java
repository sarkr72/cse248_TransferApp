package model;

import java.io.Serializable;

public class College implements Serializable, Uniteable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int collegeId;
	private String collegeName;
	private String zip;
	private String state;
	private String city;
	private String collegeType;
	private int studentSize;
	private float lat;
	private float lon;
	private int academicYearCost;
	private int inStateCost;
	private int outOfStateCost;

	public College(int collegeId, String collegeName, String zip, String state, String city, String collegeType,
			int studentSize, float lat, float lon, int academicYearCost, int inStateCost, int outOfStateCost) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.zip = zip;
		this.state = state;
		this.city = city;
		this.collegeType = collegeType;
		this.studentSize = studentSize;
		this.lat = lat;
		this.lon = lon;
		this.academicYearCost = academicYearCost;
		this.inStateCost = inStateCost;
		this.outOfStateCost = outOfStateCost;
	}

	public int getAcademicYearCost() {
		return academicYearCost;
	}

	public void setAcademicYearCost(int academicYearCost) {
		this.academicYearCost = academicYearCost;
	}

	public int getInStateCost() {
		return inStateCost;
	}

	public void setInStateCost(int inStateCost) {
		this.inStateCost = inStateCost;
	}

	public int getOutOfStateCost() {
		return outOfStateCost;
	}

	public void setOutOfStateCost(int outOfStateCost) {
		this.outOfStateCost = outOfStateCost;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
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

	public String getCollegeType() {
		return collegeType;
	}

	public void setCollegeType(String collegeType) {
		this.collegeType = collegeType;
	}

	public int getStudentSize() {
		return studentSize;
	}

	public void setStudentSize(int studentSize) {
		this.studentSize = studentSize;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public int getCollegeId() {
		return collegeId;
	}

	@Override
	public String toString() {
		return "College [collegeId=" + collegeId + ", collegeName=" + collegeName + ", zip=" + zip + ", state=" + state
				+ ", city=" + city + ", collegeType=" + collegeType + ", studentSize=" + studentSize + ", lat=" + lat
				+ ", lon=" + lon + "]";
	}

}
