package app;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StoreAndReadDatabase;
import model.Stores;

public class Main extends Application{

	public static void main(String[] args) throws SQLException {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Stores st = new Stores();
		st.backUp();
		StoreAndReadDatabase storeDb = new StoreAndReadDatabase();
//		storeDb.storeDataIntoDB();
		storeDb.readSqlDB();
		Parent root = FXMLLoader.load(getClass().getResource("/view/LogInView.fxml"));
		Scene scene = new Scene(root, 700, 700);
		Stage primaryStage = new Stage();
		primaryStage.setWidth(700);
		primaryStage.setHeight(700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}