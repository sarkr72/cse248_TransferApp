package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.College;
import model.StoreAndReadDatabase;
import model.Stores;
import model.Uniteable;

public class SearchColleges implements Initializable {

	private String zip;
	private String collegeName;
	private int inStateCost;
	private int outOfStateCost;
	private String collegeType;
	private int tuition = 0;
	private int size = 0;
	private int collegeId;
	private String city;
	private String state;
	private String type;
	private float lat;
	private float lon;
	private String userName;
	private ObservableList<Uniteable> list = FXCollections.observableArrayList();
	private ObservableList<Uniteable> allColleges = FXCollections.observableArrayList();
	private ObservableList<Uniteable> favoriteCollege = FXCollections.observableArrayList();
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
	private Label sizeOfFavC;

	@FXML
	private Label zipLabel;
	@FXML
	private Button deleteBtn;

	@FXML
	void deleteCollege(ActionEvent event) throws SQLException {
		Uniteable college = tableView.getSelectionModel().getSelectedItem();
		if (college != null) {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			String url = "jdbc:sqlite:data/db/favoriteC.sqlite";
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement prst = conn.prepareStatement("DELETE FROM favoriteColleges WHERE id = ?");
			prst.setInt(1, college.getCollegeId());
			prst.executeUpdate();
			favoriteCollege.remove(college);
		}
		showFavoriteColleges(event);
	}

	@FXML
	void filter(ActionEvent event) {

	}

	@FXML
	void logOUt(ActionEvent event) throws IOException {
		changeScene(event, "/view/LogInView.fxml");
	}

	@FXML
	void search(ActionEvent event) {
		deleteBtn.setVisible(false);
		sizeOfFavC.setText("");
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
		List<Uniteable> sameZip = allColleges.stream()
				.filter(e -> e.getZip().compareTo(Stores.getAccounts().get(LogInController.userName).getZip()) == 0)
				.collect(Collectors.toList());
		if (!zipField.getText().isEmpty()) {
			filtered.add(sameZip.get(0));
		} else {
			if (!tuitionField.getText().isEmpty()) {
				tuition = Integer.parseInt(tuitionField.getText());
			}
			if (!sizeField.getText().isEmpty()) {
				size = Integer.parseInt(sizeField.getText());
			}

			if (collegeType == null && size == 0 && tuition != 0) {
				filtered = StoreAndReadDatabase.list.stream()
						.filter(i -> i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition)
						.collect(Collectors.toList());
			} else if (collegeType == null && size != 0 && tuition != 0) {
				filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getStudentSize() <= size
						&& i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition)
						.collect(Collectors.toList());
			} else if (collegeType != null && size != 0 && tuition != 0) {
				filtered = StoreAndReadDatabase.list.stream()
						.filter(i -> i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition
								&& i.getCollegeType().compareTo(collegeType) == 0 && i.getStudentSize() <= size)
						.collect(Collectors.toList());
			} else if (collegeType == null && size != 0 && tuition != 0) {
				filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getInStateCost() <= tuition
						&& i.getOutOfStateCost() <= tuition && i.getStudentSize() <= size).collect(Collectors.toList());
			} else if (collegeType != null && size == 0 && tuition != 0) {
				filtered = StoreAndReadDatabase.list
						.stream().filter(i -> i.getCollegeType().compareTo(collegeType) == 0
								&& i.getInStateCost() <= tuition && i.getOutOfStateCost() <= tuition)
						.collect(Collectors.toList());
			} else if (collegeType != null && size != 0 && tuition == 0) {

				filtered = StoreAndReadDatabase.list.stream()
						.filter(i -> i.getCollegeType().compareTo(collegeType) == 0 && i.getStudentSize() <= size)
						.collect(Collectors.toList());
			} else if (collegeType != null && size == 0 && tuition == 0) {

				filtered = StoreAndReadDatabase.list.stream()
						.filter(i -> i.getCollegeType().compareTo(collegeType) == 0).collect(Collectors.toList());
			} else if (collegeType == null && size != 0 && tuition == 0) {

				filtered = StoreAndReadDatabase.list.stream().filter(i -> i.getStudentSize() <= size)
						.collect(Collectors.toList());
			} else if (collegeType == null && size == 0 && tuition == 0) {

				list = FXCollections.observableArrayList(StoreAndReadDatabase.list);
			}
		}
		list = FXCollections.observableArrayList(filtered);
		tableView.setItems(list);
//		System.out.println("type" + collegeType + " zip " + zip + " size" + size + " academicYearCost " + tuition);
	}

	@FXML
	void showFavoriteColleges(ActionEvent event) throws SQLException {
		deleteBtn.setVisible(true);
		idCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("collegeId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("collegeType"));
		zipCol.setCellValueFactory(new PropertyValueFactory<Uniteable, String>("zip"));
		inStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("inStateCost"));
		outOfStateCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("outOfStateCost"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<Uniteable, Integer>("studentSize"));
		tableView.setItems(favoriteCollege);

	}

	@FXML
	void addFavorite(ActionEvent event) {
		if (favoriteCollege.size() < 10) {
			ObservableList<Uniteable> colleges = tableView.getSelectionModel().getSelectedItems();
			if (colleges != null) {
				try {
					sizeOfFavC.setText("");
					Class.forName("org.sqlite.JDBC");
					String url = "jdbc:sqlite:data/db/favoriteC.sqlite";
					Connection conn = DriverManager.getConnection(url);
					PreparedStatement prst = conn.prepareStatement(
							"INSERT INTO favoriteColleges(userName, id, schoolName, zip, collegeType, studentSize, state, city, latitude, longitude, inStateCost, outOfStateCost) "
									+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					Uniteable favoriteC = colleges.get(0);
					favoriteCollege.add(favoriteC);
					userName = LogInController.userName;
					collegeId = favoriteC.getCollegeId();
					collegeName = favoriteC.getCollegeName();
					zip = favoriteC.getZip();
					type = favoriteC.getCollegeType();
					size = favoriteC.getStudentSize();
					state = favoriteC.getState();
					city = favoriteC.getCity();
					inStateCost = favoriteC.getInStateCost();
					outOfStateCost = favoriteC.getOutOfStateCost();

					prst.setString(1, userName);
					prst.setInt(2, collegeId);
					prst.setString(3, collegeName);
//	prst.setObject(3, type);
					prst.setString(4, zip);
					prst.setString(5, type);
					prst.setInt(6, size);
					prst.setString(7, state);
					prst.setString(8, city);
					prst.setFloat(9, lat);
					prst.setFloat(10, lon);
					prst.setInt(11, inStateCost);
					prst.setInt(12, outOfStateCost);
					prst.execute();
//						i++;
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} else {
			sizeOfFavC.setVisible(true);
			sizeOfFavC.setText("you already have 10 colleges saved");

		}
	}

	public void changeScene(ActionEvent event, String str) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(str));
		Parent root = loader.load();
		Scene scene = new Scene(root, 700, 700);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setWidth(700);
		window.setHeight(700);
		window.setScene(scene);
		window.show();
	}

	public void setFavoriteColleges() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println(Class.forName("org.sqlite.JDBC").toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection("jdbc:sqlite:data/db/favoriteC.sqlite");
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(
				"Select userName, id, schoolName, zip, collegeType, studentSize, state, city, latitude, longitude, inStateCost, outOfStateCost FROM favoriteColleges");
		while (rs.next()) {
			String userName = LogInController.userName;
			String u = rs.getString("userName");
			if (userName.compareTo(u) == 0) {
				String collegeType = rs.getString("collegeType");
				Uniteable college = new College(rs.getInt("id"), rs.getString("schoolName"), rs.getString("zip"),
						rs.getString("state"), rs.getString("city"), collegeType, rs.getInt("studentSize"),
						rs.getFloat("latitude"), rs.getFloat("longitude"), rs.getInt("inStateCost"),
						rs.getInt("outOfStateCost"));
				favoriteCollege.add(college);
			}
//		System.out.println(StoreAndReadDatabase.list);
//		for(int i = 0; i < 2040; i++) {
//			System.out.println(i + " " + StoreAndReadDatabase.list.get(i));
//		}
		}
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
		allColleges = FXCollections.observableArrayList(StoreAndReadDatabase.list);
		tableView.setItems(allColleges);
		try {
			setFavoriteColleges();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zipLabel.setText("Zip: " + Stores.getAccounts().get(LogInController.userName).getZip());
	}
}
