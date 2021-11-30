package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Stores;
import model.Uniteable;
import model.User;

public class SignUpController implements Initializable {

	private String userName;
	private String passWord;
	private String zip = null;
	private TreeMap<String, User> accounts = Stores.getAccounts();
	@FXML
	private Pane pane;
	@FXML
	private TextField userNameField;

	@FXML
	private Label userNameLabel;

	@FXML
	private PasswordField passWordField;

	@FXML
	private Label passwordLabel;

	@FXML
	private Button signUp;

	@FXML
	private Label showLabel;

	@FXML
	void backToLogIn(ActionEvent event) throws IOException {
		changeScene(event, "/view/LogInView.fxml");
	}

	@FXML
	void signUp(ActionEvent event) throws IOException {
		userName = userNameField.getText().toLowerCase();
		passWord = passWordField.getText();

		if (Stores.getAccounts().containsKey(userName) || Stores.getAccounts().containsKey(userName)
				|| Stores.getAccounts().containsKey(userName))

		{
			userNameLabel.setText("userName already exists");
		} else {
			userNameLabel.setText("");
			if (passWord.length() < 8) {
				passwordLabel.setText("Enter at least 8 characters");
			} else if (!passWord.matches(".*[A-Z].*")) {
				passwordLabel.setText("Enter at least one capitol letter");
			} else if (!passWord.matches(".*[a-z].*")) {
				passwordLabel.setText("Enter at least one lowerCase letter");
			} else if (!passWord.matches(".*\\d.*")) {
				passwordLabel.setText("Enter at least one digit");
			} else {
				User user = new User(userName, passWord, zip);
				Stores.getAccounts().put(userName, user);
				try {
					Class.forName("org.sqlite.JDBC");
					String url = "jdbc:sqlite:data/db/users.sqlite";
					Connection conn = DriverManager.getConnection(url);
//				Statement statement = conn.createStatement();
					PreparedStatement prst = conn
							.prepareStatement("INSERT INTO users(userName, password) " + "VALUES(?, ?)");
					prst.setString(1, userName);
					prst.setString(2, passWord);
					prst.execute();
				} catch (Exception e) {
					System.out.println(e);
				}
				changeScene(event, "/view/LogInView.fxml");
			}
		}
	}

	public void changeScene(ActionEvent event, String str) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(str));
		Parent root = loader.load();
		Scene scene = new Scene(root, 600, 600);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setWidth(600);
		window.setHeight(600);
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
