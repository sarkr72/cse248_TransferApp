package app;

import java.sql.SQLException;
import model.StoreAndReadDatabase;

public class Main {

	public static void main(String[] args) throws SQLException {
		StoreAndReadDatabase storeDb = new StoreAndReadDatabase();
//		storeDb.storeDataIntoDB();
		storeDb.readSqlDB();
		
	}

}