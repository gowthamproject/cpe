package com.wipro.cpe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wipro.cpe.common.Core5GDetails;
import com.wipro.cpe.model.Location;
import com.wipro.cpe.utils.Util;


public class LocationDAO implements DAOInterface<Location> {

	private static final String INSERT_LOCATION_QUERY = "INSERT INTO location VALUES";
	private static final String VIEW_LOCATION_QUERY = "SELECT * FROM location";
	private static final String UPDATE_LOCATION_QUERY = "UPDATE location SET latitude=?, longitude=? where core_id=? and gnb_id=?";
	private static final String DELETE_LOCATION_QUERY = "DELETE from location where core_id = '"
			+ Core5GDetails._5G_CORE_ID + "' and gnb_id=";

	private static Connection connection = null;

	public LocationDAO() {
		connection = DAOConnection.create_connection();
	}

	@Override
	public List<Location> getRecordByParam(Map<String, Object> paramMap) throws SQLException {
		List<Location> locations = new ArrayList<Location>();
		try (Statement statement = connection.createStatement()) {
			String param = Util.parseAndGetSqlParam(paramMap);
			ResultSet resultSet = statement.executeQuery(VIEW_LOCATION_QUERY + param);

			while (resultSet.next()) {
				Location location = new Location();
				location.setRouter(resultSet.getInt(2));
				location.setName(resultSet.getString(3));
				location.setRegion_state(resultSet.getString(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			connection.close();
			System.out.println("Connection Closed while fetching location records");
		}
		return locations;
	}

	@Override
	public void insertRecords(List<Location> listOfData) throws SQLException {
		for (Location data : listOfData) {
			insertRecord(data);
		}
	}

	@Override
	public void insertRecord(Location data) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String queryParam = "('" + Core5GDetails._5G_CORE_ID + "'," + data.getRouter() + ", '" + data.getName()
					+ "', " + data.getLatitude() + ", " + data.getLongitude() + ", '"+data.getRegion_state()+"')";
			int res = statement.executeUpdate(INSERT_LOCATION_QUERY + queryParam);
			if (res != 0) {
				// System.out.println("location id ----:" + data.getRouter() + " successfully
				// polled.!");
			}
		} catch (SQLException e) {
			connection.close();
			System.out.println("Connection Closed while inserting location records");
		}

	}

	@Override
	public void updateRecord(Location data) throws SQLException {
		{
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(UPDATE_LOCATION_QUERY);
				preparedStmt.setDouble(1, data.getLatitude());
				preparedStmt.setDouble(2, data.getLatitude());
				preparedStmt.setString(4, Core5GDetails._5G_CORE_ID);
				preparedStmt.setInt(5, data.getRouter());
				int res = preparedStmt.executeUpdate();
				if (res != 0) {
					// System.out.println("location ----:" + data.getName() + " successfully
					// updated.!");
				}
			} catch (SQLException e) {
				connection.close();
				System.out.println("Connection Closed while updating location records");
			}
		}
	}

	@Override
	public void deleteRecords(List<String> params) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			for (String param : params) {
				statement.executeUpdate(DELETE_LOCATION_QUERY + "'" + param + "'");
			}
		} catch (SQLException e) {
			connection.close();
		}
	}

	@Override
	public void updateOrInsertRecords(List<Location> listOfData) throws SQLException {
		if (listOfData == null || listOfData.isEmpty())
			return;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("core_id", Core5GDetails._5G_CORE_ID);
		List<Location> existingLocations = getRecordByParam(paramMap);

		if (existingLocations.isEmpty()) {
			insertRecords(listOfData);
		} else {

			List<String> existinggNBIds = existingLocations.stream().map(location -> location.getRouter())
					.map(p -> p.toString()).collect(Collectors.toList());
			List<String> currentgNBIds = listOfData.stream().map(location -> location.getRouter())
					.map(p -> p.toString()).collect(Collectors.toList());

			List<String> deleteLocations = existinggNBIds.stream().filter(i -> !currentgNBIds.contains(i))
					.collect(Collectors.toList());
			deleteRecords(deleteLocations);
			List<Location> insertLocations = listOfData.stream()
					.filter(i -> !existinggNBIds.contains(i.getRouter()+"")).collect(Collectors.toList());
			insertRecords(insertLocations);

			for (Location curr_location : listOfData) {
				for (Location ext_location : existingLocations) {
					if (curr_location.getRouter() == ext_location.getRouter()) {
						String region_state = curr_location.getRegion_state();
						if (!region_state.equals(ext_location.getRegion_state()))
							updateRecord(curr_location);
					}
				}
			}
		}
	}

	@Override
	public void pollRecords(List<Location> listOfData) throws SQLException, InterruptedException {
		updateOrInsertRecords(listOfData);
		System.out.println("cpe::Location records are polling..");
	}

	public static void main(String[] args) throws SQLException, InterruptedException {
		new LocationDAO().pollRecords(null);
	}
}
