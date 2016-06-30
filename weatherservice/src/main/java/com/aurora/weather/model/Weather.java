package com.aurora.weather.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JSON object for the whole weather return. Current, forecasted conditions, and
 * location of return.
 * 
 * @author imcewan
 * 
 */
@XmlRootElement(name = "weather")
public class Weather {

	private String location;
	private CurrentCondition currentCondition;
	private ForecastCondition[] forecastConditions;
	private String url;

	@XmlElement
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@XmlElement
	public CurrentCondition getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(CurrentCondition currentCondition) {
		this.currentCondition = currentCondition;
	}

	@XmlElement
	public ForecastCondition[] getForecastConditions() {
		return forecastConditions;
	}

	public void setForecastConditions(ForecastCondition[] forecastConditions) {
		this.forecastConditions = forecastConditions;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
