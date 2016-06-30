package com.aurora.weather.service;

/**
 * Weather excpetion to wrap other exceptions when dealing with getting the weather from the service.
 * 
 * @author imcewan
 *
 */
public class WeatherException extends Exception {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -5622304257305676264L;


	/**
	 * @param arg0
	 */
	public WeatherException(String arg0) {
		super(arg0);	}

	/**
	 * @param arg0
	 */
	public WeatherException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public WeatherException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
