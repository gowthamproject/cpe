package com.wipro.cpe.utils;

import java.io.StringReader;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.wipro.cpe.common.Constants;
import com.wipro.cpe.model.Location;
import com.wipro.cpe.model.Router;

public class Parser {

	public static Object parseJsonStrToObject(String jsonStr, String oper) {

		jsonStr = jsonStr.replace("\"", "'");
		JsonReader reader = new JsonReader(new StringReader(jsonStr.trim()));
		reader.setLenient(true);

		switch (oper) {
		case Constants.LOCATION:
			return Arrays.asList(new Gson().fromJson(reader, Location[].class));
		case Constants.ROUTER:
			return Arrays.asList(new Gson().fromJson(reader, Router[].class));
		}
		return null;
	}

}
