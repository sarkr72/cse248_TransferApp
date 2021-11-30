package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import javafx.scene.Cursor;

public class Stores {

	private static TreeMap<String, User> accounts;

	public Stores() {
		accounts = new TreeMap<>();
	}

	public void add(String userName, User user) {
		accounts.put(userName, user);
	}

	public static TreeMap<String, User> getAccounts() {
		return accounts;
	}

	public void backUp() throws ClassNotFoundException, IOException, SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println(Class.forName("org.sqlite.JDBC").toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection("jdbc:sqlite:data/db/users.sqlite");
		Statement statement = conn.createStatement();
//		String count = "SELECT count(*) FROM users";
//		if(count.compareTo("0") != 0) {
		ResultSet rs = statement.executeQuery("Select username, password, zip FROM users");
		while (rs.next()) {
//			if (rs.next() != null) {
				String userName = rs.getString("userName");
				String password = rs.getString("password");
				String zip = rs.getString("zip");
				accounts.put(userName, new User(userName, password, zip));
//			}
		}
		conn.close();
	}
}
