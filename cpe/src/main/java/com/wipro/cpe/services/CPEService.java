package com.wipro.cpe.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.wipro.cpe.common.Constants;

public abstract class CPEService {

	protected String pullData(final String url) throws IOException {

		URL weburl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) weburl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty(Constants.X_ECM_API_ID, Constants.X_ECM_API_ID_VAL);
		conn.setRequestProperty(Constants.X_ECM_API_KEY, Constants.X_ECM_API_KEY_VAL);
		conn.setRequestProperty(Constants.X_CP_API_ID, Constants.X_CP_API_ID_VAL);
		conn.setRequestProperty(Constants.X_CP_API_KEY, Constants.X_CP_API_KEY_VAL);

		// System.out.println("Output is: " + conn.getResponseCode());
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		StringBuilder sb = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		return sb.toString();
	}

	protected String pullData_POST(final String url, Map<String, Object> params) throws IOException {
		URL weburl = new URL(url);
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), String.valueOf(StandardCharsets.UTF_8)));
			postData.append('=');
			postData.append(
					URLEncoder.encode(String.valueOf(param.getValue()), String.valueOf(StandardCharsets.UTF_8)));
		}
		byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

		HttpURLConnection conn = (HttpURLConnection) weburl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty(Constants.X_ECM_API_ID, Constants.X_ECM_API_ID_VAL);
		conn.setRequestProperty(Constants.X_ECM_API_KEY, Constants.X_ECM_API_KEY_VAL);
		conn.setRequestProperty(Constants.X_CP_API_ID, Constants.X_CP_API_ID_VAL);
		conn.setRequestProperty(Constants.X_CP_API_KEY, Constants.X_CP_API_KEY_VAL);
		conn.getOutputStream().write(postDataBytes);
		// System.out.println("Output is: " + conn.getResponseCode());
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		StringBuilder sb = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		return sb.toString();
	}

	protected String pullData_GET(final String url, Map<String, Object> params) throws IOException {
		StringBuilder data = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (data.length() != 0)
				data.append('&');
			data.append(URLEncoder.encode(param.getKey(), String.valueOf(StandardCharsets.UTF_8)));
			data.append('=');
			data.append(URLEncoder.encode(String.valueOf(param.getValue()), String.valueOf(StandardCharsets.UTF_8)));
		}
		URL weburl = new URL(url + "?" + data);
		HttpURLConnection conn = (HttpURLConnection) weburl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty(Constants.X_ECM_API_ID, Constants.X_ECM_API_ID_VAL);
		conn.setRequestProperty(Constants.X_ECM_API_KEY, Constants.X_ECM_API_KEY_VAL);
		conn.setRequestProperty(Constants.X_CP_API_ID, Constants.X_CP_API_ID_VAL);
		conn.setRequestProperty(Constants.X_CP_API_KEY, Constants.X_CP_API_KEY_VAL); // System.out.println("Output is: "
																						// + conn.getResponseCode());
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		return sb.toString();
	}

}
