package model;

import java.io.Serializable;

public class TuitionLevel implements Uniteable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int academicYearCost;
	private int inStateCost;
	private int outOfStateCost;
	public TuitionLevel(int academicYearCost, int inStateCost, int outOfStateCost) {
		super();
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
	@Override
	public String toString() {
		return "TuitionLevel [academicYearCost=" + academicYearCost + ", inStateCost=" + inStateCost
				+ ", outOfStateCost=" + outOfStateCost + "]";
	}
	
}
