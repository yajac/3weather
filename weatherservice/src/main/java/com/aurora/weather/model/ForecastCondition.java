package com.aurora.weather.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JSON object for the forecasted weather conditions.
 * 
 * @author imcewan
 * 
 */
@XmlRootElement(name = "forecastCondition")
public class ForecastCondition {

	private String condition;
	private String conditionText;
	private String day;
	private String high;
	private String low;
	private String iconPath;
	private int date;

	@XmlElement
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@XmlElement
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@XmlElement
	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	@XmlElement
	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	@XmlElement
	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getConditionText() {
		return conditionText;
	}

	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
}
