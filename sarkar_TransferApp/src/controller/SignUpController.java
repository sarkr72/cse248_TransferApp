package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
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
	private String type;
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
		File file = new File("/data/users/Accounts.dat");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream dos = new ObjectOutputStream(fos);
		dos.writeObject(accounts);
		dos.close();
		fos.close();
		changeScene(event,  "/view/LogInView.fxml");
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
				type = "user";
				User user = new User(userName, passWord, type);
				Stores.getAccounts().put(userName, user);
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
