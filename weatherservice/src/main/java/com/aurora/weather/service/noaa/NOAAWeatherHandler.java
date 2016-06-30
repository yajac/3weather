/*
 * Copyright 2012 YAJAC
 */
package com.aurora.weather.service.noaa;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.aurora.weather.model.CurrentCondition;
import com.aurora.weather.model.ForecastCondition;
import com.aurora.weather.model.Weather;


/**
 * SAX Parser, parses out the values for forecast and current conditions.
 * 
 * @author imcewan
 * 
 */
public class NOAAWeatherHandler extends DefaultHandler {
	
	private String location;
	private String currentWeatherCondition;
	private String currentTemperature;
	private String currentIcon;
	
	private String firstKey;
	private String secondKey;
	private String thirdKey;
	private String mininumKey;
	private String maxinumKey;
	
	private List<String> mininumTemps = new ArrayList<String>();
	private List<String> maxinumTemps = new ArrayList<String>();
	private List<String> weatherConditions = new ArrayList<String>();
	private List<String> icons = new ArrayList<String>();
	private List<String> forecastText = new ArrayList<String>();
			
	private List<String> layoutKeys = new ArrayList<String>();
	private List<String> allStartTimeName = new ArrayList<String>();
	private List<String> allStartTimes = new ArrayList<String>();
	private List<String> firstStartTimeName = new ArrayList<String>();
	private List<String> firstStartTimes = new ArrayList<String>();
	private List<String> secondStartTimeName = new ArrayList<String>();
	private List<String> secondStartTimes = new ArrayList<String>();
	
	private static final String locationDescription = "description";
	private static final String layoutKey = "layout-key";
	private static final String startValidTime = "start-valid-time";
	private static final String temperature = "temperature";
	private static final String value = "value";
	private static final String weatherCondition = "weather-conditions";
	private static final String data = "data";
	private static final String iconLink = "icon-link";
	private static final String text = "text";
	
	private boolean isDescription;
	private boolean isLayoutKey;
	private boolean isStartValidTime;
	private boolean isTemperature;
	private boolean isValue;
	private boolean isCurrent;
	private boolean isCurrentTemperature;
	private boolean isIconLink;
	private boolean isText;
	
	private boolean isMininum;
	private boolean isMaxinum;
	
	private StringBuilder iconPath = new StringBuilder();

	public Weather getWeather(){
		Weather weather = new Weather();
		weather.setLocation(location);
		CurrentCondition currentCondition = new CurrentCondition();
		currentCondition.setCondition(currentWeatherCondition);
		currentCondition.setTemperature(currentTemperature);
		currentCondition.setIconPath(currentIcon);
		weather.setCurrentCondition(currentCondition);
		ForecastCondition[] forecastConditions = new ForecastCondition[allStartTimeName.size()]; 
		for(int i = 0; i < allStartTimeName.size(); i++){
			forecastConditions[i] = new ForecastCondition();
			forecastConditions[i].setIconPath(icons.get(i));
			forecastConditions[i].setDay(allStartTimeName.get(i));
			forecastConditions[i].setConditionText(forecastText.get(i));
			forecastConditions[i].setCondition(weatherConditions.get(i));
			
			String startTime = allStartTimes.get(i);
			if(firstStartTimes.contains(startTime) && mininumKey.equals(secondKey)){
				int index = firstStartTimes.indexOf(startTime);
				forecastConditions[i].setLow(mininumTemps.get(index));
			} else if(firstStartTimes.contains(startTime) && maxinumKey.equals(secondKey)){
				int index = firstStartTimes.indexOf(startTime);
				forecastConditions[i].setHigh(maxinumTemps.get(index));
			} else if(secondStartTimes.contains(startTime) && mininumKey.equals(thirdKey)){
				int index = secondStartTimes.indexOf(startTime);
				forecastConditions[i].setLow(mininumTemps.get(index));
			} else if(secondStartTimes.contains(startTime) && maxinumKey.equals(thirdKey)){
				int index = secondStartTimes.indexOf(startTime);
				forecastConditions[i].setHigh(maxinumTemps.get(index));
			}
		}
		weather.setForecastConditions(forecastConditions);
		return weather;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals(locationDescription)){
			isDescription = true;
		} else if (qName.equals(layoutKey)){
			isLayoutKey = true;
		} else if (qName.equals(temperature)){
			isTemperature = true;
			String type = attributes.getValue("type");
			if(type.equalsIgnoreCase("apparent")){
				isCurrentTemperature = true;
			} else if(type.equalsIgnoreCase("minimum")){
				isMininum = true;
				mininumKey = attributes.getValue("time-layout");
			} else if(type.equalsIgnoreCase("maximum")){
				isMaxinum = true;
				maxinumKey = attributes.getValue("time-layout");
			}
		} else if (qName.equals(value)){
			isValue = true;
		} else if (qName.equals(startValidTime)){
			isStartValidTime = true;
			if(layoutKeys.size() == 1){
				allStartTimeName.add(attributes.getValue("period-name"));
			} else if(layoutKeys.size() == 2){
				firstStartTimeName.add(attributes.getValue("period-name"));
			} else if(layoutKeys.size() == 3){
				secondStartTimeName.add(attributes.getValue("period-name"));
			}
		} else if(qName.equals(weatherCondition)){
			if(isCurrent){
				if(attributes.getValue("weather-summary") != null){
					currentWeatherCondition = attributes.getValue("weather-summary");
				}
			} else {
				weatherConditions.add(attributes.getValue("weather-summary"));
			}
		} else if(qName.equals(data)){
			if("current observations".equals(attributes.getValue("type"))){
				isCurrent = true;
			}
		} else if (qName.equals(iconLink)){
			isIconLink = true;
		} else if (qName.equals(text)){
			isText = true;
		} 
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals(locationDescription)){
			isDescription = false;
		} else if (qName.equals(layoutKey)){
			isLayoutKey = false;
		} else if (qName.equals(temperature)){
			isTemperature = false;
			isMininum = false;
			isMaxinum = false;
			isCurrentTemperature = false;
		} else if (qName.equals(value)){
			isValue = false;
		} else if (qName.equals(startValidTime)){
			isStartValidTime = false;
		} else if(qName.equals(data)){
			isCurrent = false;
		} else if (qName.equals(iconLink)){
			isIconLink = false;
			icons.add(iconPath.toString());
			iconPath = new StringBuilder();
		} else if (qName.equals(text)){
			isText = false;
		} 
	}
	
	public void characters(char ch[], int start, int length) {
		String value = new String(ch, start, length);
		if(isDescription){
			location = value;
		} else if(isLayoutKey){
			if(value.startsWith("k-p12h")){
				firstKey = value;
				layoutKeys.add(value);
			}else if(firstKey != null && secondKey == null){
				secondKey = value;
				layoutKeys.add(value);
			} else if(secondKey != null && thirdKey == null){
				thirdKey = value;
				layoutKeys.add(value);
			}
		} else if(isStartValidTime){
			if(layoutKeys.size() == 1){
				allStartTimes.add(value);
			} else if(layoutKeys.size() == 2){
				firstStartTimes.add(value);
			} else if(layoutKeys.size() == 3){
				secondStartTimes.add(value);
			}
		} else if(isTemperature && isValue){
			if(isMininum){
				mininumTemps.add(value);
			}
			if(isMaxinum){
				maxinumTemps.add(value);
			}
			if(isCurrentTemperature){
				currentTemperature = value;
			}
		} else if(isIconLink){
			if(isCurrent){
				currentIcon = value;
			} else {
				iconPath.append(value);
			}
		} else if(isText){
			forecastText.add(value);
		}
	}
}