package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.StoreAndReadDatabase;
import model.Stores;
import model.Uniteable;

public class SearchColleges implements Initializable {

	private int id;
	private String zip = null;
	private String collegeName;
	private int inStateCost;
	private int outOfStateCost;
	private String collegeType;
	private int academicYearCost = 0;
	private int tuition = 0;
	private int size = 0;
	private ObservableList<Uniteable> list = FXCollections.observableArrayList();
	private ObservableList<Uniteable> list2 = FXCollections.observableArrayList();
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
		File file = new File("data/users/Accounts.dat");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream dos = new ObjectOutputStream(fos);
		dos.writeObject(Stores.getAccounts());
		dos.close();
		fos.close();
		changeScene(event, "/view/LogInView.fxml");
	}

	@FXML
	void search(ActionEvent event) {
		List<Uniteable> filtered = new LinkedList<>();
		idCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("collegeId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeType"));
		zipCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("zip"));
		inStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("inStateCost"));
		outOfStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("outOfStateCost"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("studentSize"));
		tuition = 0;
		size = 0;
		collegeType = typeBox.getValue();
		zip = zipField.getText();
		if (!tuitionField.getText().isEmpty()) {
			tuition = Integer.parseInt(tuitionField.getText());
		}
		if (!sizeField.getText().isEmpty()) {
			size = Integer.parseInt(sizeField.getText());
			System.out.println("u");
		}
//		for (Entry<String, Uniteable> entry : StoreAndReadDatabase.collegesWithStudentSize.entrySet()) {
//		    for (String s : entry.getValue()) {
//		        if (s.startsWith(searchString)) {
//		            System.out.println(entry.getKey());
//		            break;
//		        }
//		    }
		if (collegeType == null && size == 0 && tuition != 0) {
			System.out.println("4");
			filtered = StoreAndReadDatabase.list.stream()
					.filter(i -> i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition)
					.collect(Collectors.toList());
		} else if (collegeType == null && size != 0 && tuition != 0) {
			System.out.println("3");
			filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getStudentSize() <= size
					&& i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition).collect(Collectors.toList());
		} else if (collegeType != null && size != 0 && tuition != 0) {
			System.out.println("3");
			filtered = StoreAndReadDatabase.list.stream()
					.filter(i -> i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition
							&& i.getCollegeType().compareTo(collegeType) == 0 && i.getStudentSize() <= size)
					.collect(Collectors.toList());
		} else if (collegeType == null && size != 0 && tuition != 0) {
			System.out.println("3");
			filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getInStateCost() <= tuition
					&& i.getOutOfStateCost() <= tuition && i.getStudentSize() <= size).collect(Collectors.toList());
		} else if (collegeType != null && size == 0 && tuition != 0) {
			System.out.println("2");
			filtered = StoreAndReadDatabase.list
					.stream().filter(i -> i.getCollegeType().compareTo(collegeType) == 0
							&& i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition)
					.collect(Collectors.toList());
		} else if (collegeType != null && size != 0 && tuition == 0) {
			System.out.println("32");
			filtered = StoreAndReadDatabase.list.stream()
					.filter(i -> i.getCollegeType().compareTo(collegeType) == 0 && i.getStudentSize() <= size)
					.collect(Collectors.toList());
		} else if (collegeType != null && size == 0 && tuition == 0) {
			System.out.println("32");
			filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getCollegeType().compareTo(collegeType) == 0)
					.collect(Collectors.toList());
		} else if (collegeType == null && size != 0 && tuition == 0) {
			System.out.println("32");
			filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getStudentSize() <= size)
					.collect(Collectors.toList());
		} else if (collegeType == null && size == 0 && tuition == 0) {
			System.out.println("1");
			list = FXCollections.observableArrayList(StoreAndReadDatabase.list);
		}
//		List<Uniteable>filtered2 = filtered.stream()
//                .filter(i -> i.getInStateCost() < () )
//                .collect(Collectors.toList());
//		 List<Uniteable> sorted = filtered.stream().sorted().collect(Collectors.toList());
//		Collections.reverse(sorted);
		list = FXCollections.observableArrayList(filtered);
		tableView.setItems(list);
		System.out.println("type" + collegeType + " zip " + zip + " size" + size + " academicYearCost " + tuition);
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
		ObservableList<String> list = FXCollections.observableArrayList("Public", "Private nonprofit",
				"Private pro-profit");
		typeBox.setItems(list);
		idCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("collegeId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeType"));
		zipCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("zip"));
		inStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("inStateCost"));
		outOfStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("outOfStateCost"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("studentSize"));
		list2 = FXCollections.observableArrayList(StoreAndReadDatabase.list);
		tableView.setItems(list2);
//		System.out.println(StoreAndReadDatabase.list);
//		for(int i = 0; i < 2040; i++) {
//			System.out.println(i + " " + StoreAndReadDatabase.list.get(i));
//		}
	}
}
