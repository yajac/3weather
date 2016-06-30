package com.aurora.weather.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JSON object for the current weather conditions.
 * 
 * @author imcewan
 *
 */
@XmlRootElement(name = "currentCondition")
public class CurrentCondition {

	private String condition;
	private String temperature;
	private String humidity;
	private String iconPath;
	
	@XmlElement
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	@XmlElement
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	@XmlElement
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	@XmlElement
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
}
