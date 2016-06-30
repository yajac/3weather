package com.aurora.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Spark;

import com.aurora.weather.model.Weather;
import com.aurora.weather.service.WeatherException;
import com.aurora.weather.service.noaa.NOAAClient;
import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class WeatherService 
{
	private final static Logger logger = LoggerFactory.getLogger(WeatherService.class);
	
	public static void main(String[] args) {
		Gson gson = new Gson();
        Spark.get("/weather/:longitude/:latitude", (request, response) -> {
        	return getWeather(request.params(":longitude"), request.params(":latitude"), response);
        }, gson::toJson);
    }

	private static Weather getWeather(String longitude, String latitude, Response response)
			throws WeatherException {
		try {
			double longitudeValue = Double.parseDouble(longitude);
			double latitudeValue = Double.parseDouble(latitude);
			Weather weather = NOAAClient.getNOAAResponse(longitudeValue,latitudeValue);
			response.status(200);
			response.type("application/json");
			return weather;
		} catch (Exception e) {
			logger.error("Unable to get weather", e);
			response.status(500);
			throw new WeatherException(e);
		}
	}
}
