package com.Home.Application;

import java.io.Serializable;
import java.util.ArrayList;

public class House implements Serializable {

	private ArrayList<Room> rooms;

	public House(ArrayList<Room> rooms) {
		super();
		this.rooms = rooms;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void addRooms(Room room) {
		rooms.add(room);
	}
	
	public boolean turnOnDevice(String room,String d) {
		for(Room r : rooms) {
			if(r.getType().equals(room)) {
				
				r.turnOnDevice(d);
				return true;
			}
		}
		return false;
	}
	
	public boolean turnOffDevice(String room,String d) {
		for(Room r : rooms) {
			if(r.getType().equals(room)) {
				r.turnOffDevice(d);
				return true;
			}
		}
		return false;
	}
	
	public void houseStatus() {
		for(Room r : rooms) {
			r.roomStatus();
		}
	}
}
