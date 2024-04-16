package com.Home.Application;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable{

    private ArrayList<Device> devices;
    private String type;

    // Constructor
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Room(String type,ArrayList ref) {
        this.type = type;
        this.devices = ref;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public String getType() {
        return type;
    }

    
    public void addDevice(Device d) {
        devices.add(d);
    }

    
    public void removeDevice(String deviceType) {
    	int flag = 0;
        for (Device dev : devices) {
            if (dev.getDeviceType().equals(deviceType)) {
                devices.remove(dev);
                flag = 1;
                break;
            }
        }
        if(flag == 0) {
        	System.out.println("Device is not found..");
        }
    }

    
    public void turnOnDevice(String deviceType) {
    	int flag = 0;
        for (Device dev : devices) {
            if (dev.getDeviceType().equals(deviceType)) {
                dev.turnOn();
                flag = 1;
                break;
            }
        }
        if(flag == 0) {
        	System.out.println("Device is not found..");
        }
    }

    
    public void turnOffDevice(String deviceType) {
    	int flag = 0;
        for (Device dev : devices) {
            if (dev.getDeviceType().equals(deviceType)) {
                dev.turnOff();
                flag = 1;
                break;
            }
        }
        if(flag == 0) {
        	System.out.println("Device is not found..");
        }
    }
    
    public void roomStatus() {
    	System.out.println("Room type : " + type);
    	for(Device d : devices) {
    		d.overAllStatus();
    	}
    }
}
