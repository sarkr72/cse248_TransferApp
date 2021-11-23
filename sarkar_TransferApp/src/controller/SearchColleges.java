package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.StoreAndReadDatabase;
import model.Stores;
import model.TuitionLevel;
import model.Uniteable;

public class SearchColleges implements Initializable{

	private int id;
	private String zip;
	private int tuition;
	private String collegeName;
	private int inStateCost;
	private int outOfStateCost;
	private String collegeType;
	private int size;
	
	@FXML
	private Pane pane;

	@FXML
	private TableView<Uniteable> tableView;
	@FXML
	private TableColumn<Uniteable, Integer> idCol;

	@FXML
	private TableColumn<Uniteable, String> nameCol;

	@FXML
	private TableColumn<Uniteable, String> zipCol;

	@FXML
	private TableColumn<Uniteable, String> typeCol;

	@FXML
	private TableColumn<Uniteable, Integer> sizeCol;

	@FXML
	private TableColumn<Uniteable, Integer> inStateCol;

	@FXML
	private TableColumn<Uniteable, Integer> outOfStateCol;

	@FXML
	private TextField zipField;

	@FXML
	private ComboBox<String> typeBox;

	@FXML
	private TextField tuitionField;

	@FXML
	private TextField sizeField;
	@FXML
	private Button searchBtn;

	@FXML
	private TextField searchField;

	@FXML
	private Button filterBtn;

	@FXML
	void filter(ActionEvent event) {

	}

	@FXML
	void logOUt(ActionEvent event) throws IOException {
		File file = new File("/data/favoriteColleges/favoriteC.dat");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream dos = new ObjectOutputStream(fos);
		dos.writeObject(Stores.getAccounts());
		dos.close();
		fos.close();
	}

	@FXML
	void search(ActionEvent event) {
		idCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("collegeId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeType"));
		zipCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("zip"));
		inStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("inStateCost"));
		outOfStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("outOfStateCost"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("studentSize"));
		
		collegeType = typeBox.getValue();
		zip = zipField.getText();
		inStateCost = Integer.parseInt(tuitionField.getText());
		outOfStateCost =inStateCost;
		
		if()
		ObservableList<Uniteable> list = FXCollections.observableArrayList(StoreAndReadDatabase.);
		tableView.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Public", "Private nonprofit", "Private pro-profit");
		typeBox.setItems(list);
	}
}
