package com.wipro.cpe.services;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.wipro.cpe.certificate.Certificate;
import com.wipro.cpe.common.Constants;
import com.wipro.cpe.dao.RouterDAO;
import com.wipro.cpe.model.Router;
import com.wipro.cpe.utils.Parser;

public class RouterService extends CPEService {

	@SuppressWarnings("unchecked")
	public void pull_RouterDetailsFromRaemisAPI() throws Exception {

		Certificate.doTrustToCertificates();
		String responseJson = super.pullData(Constants.ROUTER_URL);
		// System.out.println("Router RESPONSE ----: " + responseJson);
		if (responseJson != null && !responseJson.isEmpty()) {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(responseJson);
				JSONArray data = (JSONArray) json.get("data");
			new RouterDAO().pollRecords((List<Router>) Parser.parseJsonStrToObject(data.toString(), Constants.ROUTER));
		}
	}
}
