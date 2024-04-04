package com.wipro.cpe.dao;

import com.wipro.cpe.common.Constants;
import com.wipro.cpe.common.Core5GDetails;
import com.wipro.cpe.model.Router;
import com.wipro.cpe.utils.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RouterDAO implements DAOInterface<Router> {
	private static final String INSERT_ROUTER_QUERY = "INSERT INTO gnb VALUES";
	private static final String VIEW_ROUTER_QUERY = "SELECT * FROM gnb";
	private static final String UPDATE_ROUTER_QUERY = "UPDATE gnb SET plmn_id=?, name=?, status=? where gnb_id=?";
	private static final String DELETE_ROUTER_QUERY = "DELETE from gnb where core_id = '" + Core5GDetails._5G_CORE_ID
			+ "' and gnb_id=";

	private static Connection connection = null;

	public RouterDAO() {
		connection = DAOConnection.create_connection();
	}

	@Override
	public List<Router> getRecordByParam(Map<String, Object> paramMap) throws SQLException {
		List<Router> routers = new ArrayList<Router>();
		try (Statement statement = connection.createStatement()) {
			String param = Util.parseAndGetSqlParam(paramMap);
			ResultSet resultSet = statement.executeQuery(VIEW_ROUTER_QUERY + param);

			while (resultSet.next()) {
				Router router = new Router();
				router.setName(resultSet.getString(1));
				router.setId(resultSet.getInt(3));
				router.setState(resultSet.getString(6));
				routers.add(router);
			}
		} catch (SQLException e) {
			connection.close();
			System.out.println("Connection Closed while fetching router records");
		}
		return routers;
	}

	@Override
	public void insertRecords(List<Router> listOfData) throws SQLException {
		for (Router data : listOfData) {
			insertRecord(data);
		}
	}

	@Override
	public void insertRecord(Router data) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String status = getState(data.getState());
			String plmn_id = "00101";
			String queryParam = "('" + data.getName() + "', '" + plmn_id + "', " + data.getId() + ", " + null + ","
					+ " '" + data.getIpv4_address() + "', '" + status + "','" + Core5GDetails._5G_CORE_ID + "')";

			int res = statement.executeUpdate(INSERT_ROUTER_QUERY + queryParam);
			if (res != 0) {
				// System.out.println("router id ----:" + data.getGnb_id() + " successfully
				// polled.!");
			}
		} catch (SQLException e) {
			connection.close();
			System.out.println("Connection Closed while inserting router records");
		}

	}

	private String getState(String status) {
		String state = Constants.DISCONNECTED;
		if (status.equals("initialized")) {
			state = Constants.CONNECTED;
		}
		return state;
	}

	@Override
	public void updateRecord(Router data) throws SQLException {
		{
			try {
				String status = getState(data.getState());
				PreparedStatement preparedStmt = connection.prepareStatement(UPDATE_ROUTER_QUERY);
				preparedStmt.setString(1, null);
				preparedStmt.setString(2, data.getName());
				preparedStmt.setString(3, status);
				preparedStmt.setInt(4, data.getId());
				int res = preparedStmt.executeUpdate();
				if (res != 0) {
					// System.out.println("router ----:" + data.getName() + " successfully
					// updated.!");
				}
			} catch (SQLException e) {
				connection.close();
				System.out.println("Connection Closed while updating router records");
			}
		}
	}

	@Override
	public void deleteRecords(List<String> params) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			for (String param : params) {
				statement.executeUpdate(DELETE_ROUTER_QUERY + param);
			}
		} catch (SQLException e) {
			connection.close();
		}
	}

	public void deleteRecordss(List<Integer> params) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			for (Integer param : params) {
				statement.executeUpdate(DELETE_ROUTER_QUERY + param);
			}
		} catch (SQLException e) {
			connection.close();
		}
	}

	@Override
	public void updateOrInsertRecords(List<Router> listOfData) throws SQLException {
		if (listOfData == null || listOfData.isEmpty())
			return;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("core_id", Core5GDetails._5G_CORE_ID);
		List<Router> existingRouters = getRecordByParam(paramMap);

		if (existingRouters.isEmpty()) {
			insertRecords(listOfData);
		} else {

			List<Integer> existingRouterIds = existingRouters.stream().map(router -> router.getId())
					.collect(Collectors.toList());
			List<Integer> currentRouterIds = listOfData.stream().map(router -> router.getId())
					.collect(Collectors.toList());

			List<Integer> deleteRouters = existingRouterIds.stream().filter(i -> isNOTContains(currentRouterIds, i))
					.collect(Collectors.toList());
			deleteRecordss(deleteRouters);
			List<Router> insertRouters = listOfData.stream().filter(i -> isNOTContains(existingRouterIds, i.getId()))
					.collect(Collectors.toList());
			insertRecords(insertRouters);

			for (Router curr_router : listOfData) {
				for (Router ext_router : existingRouters) {
					if (curr_router.getId() == ext_router.getId()) {
						String status = getState(curr_router.getState());
						if (!status.equals(ext_router.getState()))
							updateRecord(curr_router);
					}
				}
			}
		}
	}

	private boolean isNOTContains(List<Integer> listOfData, int compare_Id) {
		for (Integer id : listOfData) {
			if (id == compare_Id)
				return false;
		}
		return true;
	}

	@Override
	public void pollRecords(List<Router> listOfData) throws SQLException, InterruptedException {
		updateOrInsertRecords(listOfData);
		System.out.println("cpe::gNB records are polling..");
	}
}
