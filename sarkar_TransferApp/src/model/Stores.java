package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeMap;

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

	public void backUp() throws ClassNotFoundException, IOException {
		if (new File("data/users/Accounts.dat").exists()) {
			FileInputStream fis = new FileInputStream("data/users/Accounts.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			accounts =  (TreeMap<String, User>) (ois.readObject());
			ois.close();
			fis.close();
			if (accounts == null) {
			}
		}
	}
}
