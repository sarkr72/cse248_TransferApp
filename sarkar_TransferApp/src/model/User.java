package model;

import java.io.Serializable;
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String passWord;
	private int zip;

	public User(String userName, String passWord, int zip) {
		this.userName = userName;
		this.passWord = passWord;
		this.zip = zip;
	}

	public int getType() {
		return zip;
	}

	public void setType(int zip) {
		this.zip = zip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", passWord=" + passWord + ", zip=" + zip + "]";
	}

}
