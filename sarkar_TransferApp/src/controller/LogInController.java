package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Stores;

public class LogInController implements Initializable {

	public static String userName;
	private String passWord;
	private String zip = null;

	@FXML
	private TextField userNameField;

	@FXML
	private Pane pane;

	@FXML
	private Label userNameLabel;

	@FXML
	private PasswordField passWordField;

	@FXML
	private Label passwordLabel;

	@FXML
	private Label showLabel;

	@FXML
	void signIn(ActionEvent event) throws IOException {
		userName = userNameField.getText().toLowerCase();
		passWord = passWordField.getText();
//		if (Stores.getAccounts() == null) {
//			userNameLabel.setText("user doesnt exist");
//		} else {
		if (Stores.getAccounts().containsKey(userName)) {
			if (Stores.getAccounts().get(userName).getPassWord().compareTo(passWord) == 0) {
				changeScene(event, "/view/SearchCollegeView.fxml");
			} else {
				passwordLabel.setText("Password is incorrect");
				userNameLabel.setText("");
			}
		} else {
			userNameLabel.setText("Username isn't recognized");
			passwordLabel.setText("");
//			}
		}
	}

	@FXML
	void exit(ActionEvent event) throws IOException {
//		if(userName.is)
//		try {
//			Class.forName("org.sqlite.JDBC");
//			String url = "jdbc:sqlite:data/db/users.sqlite";
//			Connection conn = DriverManager.getConnection(url);
////			Statement statement = conn.createStatement();
//			PreparedStatement prst = conn.prepareStatement(
//					"INSERT INTO users(userName, password, zip) "
//							+ "VALUES(?, ?, ?)");
//			prst.setString(1, userName);
//			prst.setString(2, passWord);
//			prst.setString(3, zip);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		System.exit(0);
	}

	@FXML
	void signUp(ActionEvent event) throws IOException {
		changeScene(event, "/view/SignUpView.fxml");
	}

	public void changeScene(ActionEvent event, String str) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(str));
		Parent root = loader.load();
		Scene scene = new Scene(root, 700, 700);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setWidth(600);
		window.setHeight(600);
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		userStore = Stores.getUserStore();
//		managerStore = Stores.getManagerStore();
//		administratorStore = Stores.getAdministratorStore();
	}
}