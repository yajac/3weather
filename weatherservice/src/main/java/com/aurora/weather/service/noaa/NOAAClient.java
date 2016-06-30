/*
 * Copyright 2012 YAJAC
 */
package com.aurora.weather.service.noaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.aurora.weather.model.Weather;
import com.aurora.weather.service.WeatherException;

/**
 * NOAA Client Connects to noaa and get weather xml response
 * 
 * @author imcewan
 * 
 */
public class NOAAClient {
	
	private static final String path = "http://forecast.weather.gov/MapClick.php";
	private static final String noaaOptions = "&unit=0&lg=english&FcstType=dwml";

	public static Weather getNOAAResponse(double latitude, double longitude) throws WeatherException{
		try {
			HttpURLConnection httpConnection = connect(latitude, longitude);
			Weather weather = parseWeatherXML(httpConnection.getInputStream());
			return weather;
		} catch (Exception e) {
			throw new WeatherException("Unable to connect to NOAA", e);
		}
	}

	private static HttpURLConnection connect(double latitude, double longitude)
			throws IOException, MalformedURLException {
		StringBuilder stringBuilder = new StringBuilder(path);
		stringBuilder.append("?lat=");
		stringBuilder.append(latitude);
		stringBuilder.append("&lon=");
		stringBuilder.append(longitude);
		stringBuilder.append(noaaOptions);
		HttpURLConnection httpConnection = (HttpURLConnection)new URL(stringBuilder.toString()).openConnection();
		httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
		httpConnection.setDoOutput(true);
		httpConnection.connect();
		return httpConnection;
	}
	
	public static Weather parseWeatherXML(InputStream htmlEntityStream) throws WeatherException {
		try {
			NOAAWeatherHandler weatherHandler = new NOAAWeatherHandler();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(htmlEntityStream, weatherHandler);
			return weatherHandler.getWeather();
		} catch (Exception e) {
			throw new WeatherException("Unable to parse Weather Return", e);
		}
	}
}
