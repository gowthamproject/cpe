package com.wipro.cpe.services;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.wipro.cpe.certificate.Certificate;
import com.wipro.cpe.common.Constants;
import com.wipro.cpe.dao.LocationDAO;
import com.wipro.cpe.model.Location;
import com.wipro.cpe.utils.Parser;

public class LocationService extends CPEService {

	@SuppressWarnings("unchecked")
	public void pull_LocationDetailsFromRaemisAPI() throws Exception {

		Certificate.doTrustToCertificates();
		String responseJson = super.pullData(Constants.LOCATION_URL);
		// System.out.println("Router RESPONSE ----: " + responseJson);
		if (responseJson != null && !responseJson.isEmpty()) {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(responseJson);
			JSONArray data = (JSONArray) json.get("data");
			new LocationDAO()
					.pollRecords((List<Location>) Parser.parseJsonStrToObject(data.toString(), Constants.LOCATION));
		}
	}

}
