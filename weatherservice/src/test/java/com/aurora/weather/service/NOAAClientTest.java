package com.aurora.weather.service;

import org.junit.Assert;
import org.junit.Test;

import com.aurora.weather.model.ForecastCondition;
import com.aurora.weather.model.Weather;
import com.aurora.weather.service.noaa.NOAAClient;

public class NOAAClientTest {

	@Test
	public void testNOAAClient() throws WeatherException{
		Weather weather = NOAAClient.getNOAAResponse(39.045526,-76.8908917);
		Assert.assertNotNull(weather);
		Assert.assertNotNull(weather.getLocation());
		Assert.assertNotNull(weather.getCurrentCondition());
		Assert.assertNotNull(weather.getCurrentCondition().getCondition());
		Assert.assertNotNull(weather.getCurrentCondition().getIconPath());
		Assert.assertNotNull(weather.getCurrentCondition().getTemperature());
		Assert.assertNotNull(weather.getForecastConditions());
		assertCondition(weather.getForecastConditions()[0]);
		assertCondition(weather.getForecastConditions()[1]);
		assertCondition(weather.getForecastConditions()[2]);
		assertCondition(weather.getForecastConditions()[3]);
		assertCondition(weather.getForecastConditions()[4]);
		assertCondition(weather.getForecastConditions()[5]);
		assertCondition(weather.getForecastConditions()[6]);
	}
	
	private void assertCondition(ForecastCondition forecastCondition){
		Assert.assertNotNull(forecastCondition.getDay());
		Assert.assertNotNull(forecastCondition.getConditionText());
		Assert.assertNotNull(forecastCondition.getIconPath());
		Assert.assertTrue(forecastCondition.getHigh() != null || forecastCondition.getLow() != null);
	}

}
