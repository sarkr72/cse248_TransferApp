package model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StoreAndReadDatabase {

	private int totalPages = 1;
	public static LinkedList<Uniteable> list;

	public void storeDataIntoDB() throws SQLException {

		String collegeName = "";
		int collegeId = 0;
		String zip = "";
		String city = "";
		String state = "";
		int type = 0;
		int size = 0;
		float lat = 0;
		float lon = 0;
		int academicYearCost = 0;
		int inStateCost = 0;
		int outOfStateCost = 0;
		int pageNumber = 0;
//		int count = 0;

		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println(Class.forName("org.sqlite.JDBC").toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn2 = DriverManager.getConnection("jdbc:sqlite:data/db/colleges.sqlite");
//		Statement statement = conn2.createStatement();
		while (pageNumber != totalPages) {
			String inline = "";
			try {
				URL url = new URL(
						"https://api.data.gov/ed/collegescorecard/v1/schools.json?school.degrees_awarded.predominant=3&_fields=id,latest.academics.program.bachelors.computer,school.ownership,school.name,school.city,latest.cost.tuition.out_of_state,latest.cost.tuition.in_state,latest.cost.attendance.academic_year,school.state,school.zip,location.lat,location.lon,2019.student.size&api_key=mh9CnyR4TxPZt5pOSPxEtJnwUwFBUNHNxmNcv2tJ&per_page=100&page="
								+ pageNumber);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				int responsecode = conn.getResponseCode();
				System.out.println("Response code is: " + responsecode);

				if (responsecode != 200)
					throw new RuntimeException("HttpResponseCode: " + responsecode);
				else {
					// Scanner functionality will read the JSON data from the stream
					Scanner sc = new Scanner(url.openStream());
					while (sc.hasNext()) {
						inline += sc.nextLine();
					}
					System.out.println(inline);
					sc.close();
				}
				ObjectMapper objectMapper = new ObjectMapper();

				JsonNode node = objectMapper.readValue(inline, JsonNode.class);
				getTotalPages(node);
				JsonNode array = node.get("results");
				
				for (int i = 0; i < array.size(); i++) {
					JsonNode jsonNameNode = array.get(i);
					JsonNode computerScience = jsonNameNode.get("latest.academics.program.bachelors.computer");
					
					if (computerScience.asInt() > 0) {
						collegeName = jsonNameNode.get("school.name").asText();
						collegeId = jsonNameNode.get("id").asInt();
						zip = jsonNameNode.get("school.zip").asText();
						city = jsonNameNode.get("school.city").asText();
						state = jsonNameNode.get("school.state").asText();
						type = jsonNameNode.get("school.ownership").asInt();
						size = jsonNameNode.get("2019.student.size").asInt();
						lat = jsonNameNode.get("location.lat").floatValue();
						lon = jsonNameNode.get("location.lon").floatValue();
//						academicYearCost = jsonNameNode.get("latest.cost.attendance.academic_year").asInt();
						inStateCost = jsonNameNode.get("latest.cost.tuition.in_state").asInt();
						outOfStateCost = jsonNameNode.get("latest.cost.tuition.out_of_state").asInt();

						PreparedStatement prst = conn2.prepareStatement(
								"INSERT INTO colleges(id, schoolName, zip, collegeType, studentSize, state, city, latitude, longitude, inStateCost, outOfStateCost) "
										+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						prst.setInt(1, collegeId);
						prst.setString(2, collegeName);
//				prst.setObject(3, type);
						prst.setString(3, zip);
						prst.setInt(4, type);
						prst.setInt(5, size);
						prst.setString(6, state);
						prst.setString(7, city);
						prst.setFloat(8, lat);
						prst.setFloat(9, lon);
						prst.setInt(10, inStateCost);
						prst.setInt(11, outOfStateCost);
						prst.execute();
//						System.out.println(count);
//						count++;
					}
				}

//			ResultSet rs = statement.executeQuery(
//					"Select username, password, city, state, zip FROM users INNER JOIN addresses on addresses.addressID = users.addressID");
//			while (rs.next()) {

//			System.out.print(rs.getString("userName") + "\n");
//			}
			} catch (IOException e) {
				e.printStackTrace();
			}
			pageNumber++;
		}
		conn2.close();
	}

	public void readSqlDB() throws SQLException {
		list = new LinkedList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println(Class.forName("org.sqlite.JDBC").toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection locationConn = DriverManager.getConnection("jdbc:sqlite:data/db/points.sqlite");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:data/db/colleges.sqlite");
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(
				"Select id, schoolName, zip, collegeType, studentSize, state, city, latitude, longitude, inStateCost, outOfStateCost FROM colleges");
		while (rs.next()) {
			int collegeType = rs.getInt("collegeType");
			String type = "";
			if (collegeType == 1) {
				type = "Public";
			} else if (collegeType == 2) {
				type = "Private nonprofit";
			} else if (collegeType == 3) {
				type = "Private for-profit";
			}
			Uniteable college = new College(rs.getInt("id"), rs.getString("schoolName"), rs.getString("zip"),
					rs.getString("state"), rs.getString("city"), type, rs.getInt("studentSize"),
					rs.getFloat("latitude"), rs.getFloat("longitude"), rs.getInt("inStateCost"),
					rs.getInt("outOfStateCost"));
			list.add(college);
//			System.out.println(college);
//			collegesWithZip.put(rs.getString("zip"), college);
//			collegesWithTuition.put(rs.getInt("academicYearCost"), college);
//			collegesWithType.put(type, college);
//			collegesWithStudentSize.put(rs.getInt("studentSize"), college);
//			list.add(new TuitionLevel(rs.getInt("academicYearCost"),rs.getInt("inStateCost"), rs.getInt("outOfStateCost")));
//			list.add(new Address(rs.getString("zip"), rs.getString("state"), rs.getString("city")));
		}
		conn.close();
	}

	public int getTotalPages(JsonNode node) {
//		JsonNode node = getMainNode(url);
		JsonNode child = node.get("metadata");
		JsonNode totalField = child.get("total");
//		String total = totalField.asText();
//		System.out.println("total = " + total);
		JsonNode pageField = child.get("page");
		String page = pageField.asText();
		System.out.println("page = " + page);
		JsonNode per_pageField = child.get("per_page");
//		String per_page = per_pageField.asText();
//		System.out.println("per_page=" + per_page);
		totalPages = (int) Math.ceil(totalField.asDouble() / per_pageField.asInt());
		return totalPages;
	}

}
