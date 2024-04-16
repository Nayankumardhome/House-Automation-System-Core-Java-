package com.Home.Application;

import java.io.Serializable;

public class User implements Serializable{
	
	private final String username;
    private final String password;
	private House house;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.house = null;
    }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
}
