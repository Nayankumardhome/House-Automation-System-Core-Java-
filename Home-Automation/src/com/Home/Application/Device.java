package com.Home.Application;

import java.io.Serializable;

public interface Device extends Serializable{

	public void turnOn();
	public void turnOff();
	public String getDeviceType();
	public void overAllStatus();
}
