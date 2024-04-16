package com.Home.Application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApplication {

	static ArrayList<User> users = new ArrayList<>();
    static final String FILE_NAME = "data.dat";

    public static void main(String[] args) {
        
    	loadUsersFromFile();
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
        	System.out.println("+===================================+");
            System.out.println("|-------------> Menu <--------------|");
            System.out.println("|===================================|");
            System.out.println("|1. Login                           |");
            System.out.println("|-----------------------------------|");
            System.out.println("|2. Sign Up 	                    |");
            System.out.println("|-----------------------------------|");
            System.out.println("|3. Exit                            |");
            System.out.println("|===================================|");
            System.out.println("Enter Your Choice : ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        login(sc);
                        break;
                    case 2:
                        signUp(sc);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }

    private static void login(Scanner sc) {
        try {
        	System.out.println("Enter Username:");
            String username = sc.nextLine();
            System.out.println("Enter Password:");
            String password = sc.nextLine();
            User ref = authenticate(username,password);
        	
	        if (ref != null) {
	            System.out.println("Authentication successful!");
	            
	            if (ref.getHouse() == null) {
	                System.out.println("You do not have a house. Please create one to proceed.");
	                manageHouse(sc,ref);
	            } 
	            if(ref.getHouse() != null){
		            int choice;
		            do {
		                System.out.println("+===================================+");
		                System.out.println("|-------------> Menu <--------------|");
		                System.out.println("|===================================|");
		                System.out.println("|1. Add Device 	                    |");
		                System.out.println("|-----------------------------------|");
		                System.out.println("|2. Remove Device                   |");
		                System.out.println("|-----------------------------------|");
		                System.out.println("|3. Add Room                        |");
		                System.out.println("|-----------------------------------|");
		                System.out.println("|4. Perform Oprations on device     |");
		                System.out.println("|-----------------------------------|");
		                System.out.println("|5. Check Status                    |");
		                System.out.println("|-----------------------------------|");
		                System.out.println("|6. Exit                            |");
		                System.out.println("+===================================+");
		                System.out.println("Enter the choice :");
		                choice = sc.nextInt();
		                sc.nextLine(); // Consume newline
		
		                
		                switch (choice) {
		                    case 1:{
		                    	if(addDevice(sc, ref.getHouse())) {
		                    		System.out.println("Device added Sucessfully..");
		                    	}
		                    	else {
		                    		System.out.println("Device adding is failed..");
		                    	}
		                    	break;
		                    }
		                    case 2:
		                    	System.out.println("Enter the name of the room:");
		                	    String roomName = sc.nextLine();
		                	    System.out.println("Enter the name of the device to remove from room:");
		                	    String deviceName = sc.nextLine();
		                        for(Room room : ref.getHouse().getRooms()) {
		                        	if (room.getType().equalsIgnoreCase(roomName)) {
		                	            room.removeDevice(deviceName);
		                	            break;
		                	        }
		                        }
		                        break;
		                    case 3:
		                    	ref.getHouse().addRooms(selectRoom(sc));
		                        break;
		                    case 4:
		                        if(performOprations(sc, ref.getHouse())) {
		                        	System.out.println("");
		                        }
		                        break;
		                    case 5:{
		                    	int selectType = 0;
		                    	while(selectType != 4) {
		                    		System.out.println("Which Status do you want to check : ");
			                    	System.out.println("1.House");
			                    	System.out.println("2.Room");
			                    	System.out.println("3.Device");
			                    	System.out.println("4.Exit");
			                    	selectType = sc.nextInt();
			                    	sc.nextLine();
			                    	switch(selectType) {
				                    	case 1:{
				                    		ref.getHouse().houseStatus();
				                    		break;
				                    	}
				                    	case 2:{
				                    		System.out.println("Enter Room Name : ");
				                    		String statusRoomName = sc.nextLine();
				                    		for(Room room : ref.getHouse().getRooms()) {
				                    			if(room.getType().equalsIgnoreCase(statusRoomName)) {
				                    				room.roomStatus();
				                    			}
				                    		}
				                    		break;
				                    	}
				                    	case 3:{
				                    		System.out.println("Enter Room Name : ");
				                    		String statusRoomName = sc.nextLine();
				                    		System.out.println("Enter Device Name : ");
				                    		String statusDeviceName = sc.nextLine();
				                    		for(Room room : ref.getHouse().getRooms()) {
				                    			if(room.getType().equalsIgnoreCase(statusRoomName)) {
				                    				for(Device device : room.getDevices()) {
				                    					if(device.getDeviceType().equalsIgnoreCase(statusDeviceName)) {
				                    						device.overAllStatus();
				                    					}
				                    				}
				                    			}
				                    		}
				                    		break;
				                    	}
				                    	case 4:{
				                    		System.out.println("Exit...");
				                    	}
				                    	default:{
				                    		System.out.println("Invalid choice for Status checking...");
				                    	}
			                    	}
		                    	}
		                    	break;
		                    }
		                    case 6:
		                        System.out.println("Exiting...");
		                        break;
		                    default:
		                        System.out.println("Invalid choice! Please enter again.");
		                }
		            } while (choice != 6);
	            }
	        } else {
	            System.out.println("Authentication failed!");
	        }
        } catch (NullPointerException e) {
            System.out.println("An error occurred during authentication.");
        } catch(InputMismatchException e) {
        	e.printStackTrace();
        }catch (Exception e) {
        	e.printStackTrace();
        }
    }


    private static boolean addDevice(Scanner sc, House house) {
        System.out.println("Enter the name of the room:");
        String roomName = sc.nextLine();
        
        for (Room room : house.getRooms()) {
            if (room.getType().equalsIgnoreCase(roomName)) {
                System.out.println("Enter the type of device to add:");
                System.out.println("+===================================+");
                System.out.println("|-------> Available Devices <-------|");
                System.out.println("|===================================|");
                System.out.println("|1. Lights                          |");
                System.out.println("|2. Air Conditioner                 |");
                System.out.println("|3. Television                      |");
                System.out.println("|4. Shower                          |");
                System.out.println("+===================================+");
                System.out.println("Enter your choice:");
                int deviceChoice = sc.nextInt();
                sc.nextLine();
                
                System.out.println("Enter the name of the device:");
                String deviceName = sc.nextLine();
                boolean deviceExists = false;
                for (Device device : room.getDevices()) {
                    if (device.getDeviceType().equalsIgnoreCase(deviceName)) {
                        deviceExists = true;
                        break;
                    }
                }

                if (deviceExists) {
                    System.out.println("Device already exists in room " + roomName + ". Device not added.");
                    return false;
                }
                
                switch (deviceChoice) {
                    case 1:
                        room.addDevice(new Light());
                        break;
                    case 2:
                        room.addDevice(new AirConditioner());
                        break;
                    case 3:
                        room.addDevice(new Television());
                        break;
                    case 4:
                        room.addDevice(new Shower());
                        break;
                    default:
                        System.out.println("Invalid choice! Device not added.");
                        return false;
                }
                System.out.println("Device added successfully to room " + roomName);
                return true;
            }
        }
        
        System.out.println("Room not found. Device not added.");
        return false;
    }



	private static User authenticate(String userName, String password) {
		if (users == null) {
            throw new NullPointerException("User list is not initialized.");
        }
        for (User checkUser : users) {
            if (checkUser.getUsername().equals(userName) && checkUser.getPassword().equals(password)) {
                return checkUser;
            }
        }
        return null;
    }


    private static void signUp(Scanner sc) {
    	try {
            System.out.println("Enter a new username:");
            String newUsername = sc.nextLine();

	        for (User user : users) {
	            if (user.getUsername().equals(newUsername)) {
	                System.out.println("Username already exists. Please choose a different one.");
	                return;
	            }
	        }
	
	        System.out.println("Enter a new password:");
	        String newPassword = sc.nextLine();
	
	        User newUser = new User(newUsername, newPassword);
	        users.add(newUser);
	        
	        System.out.println("User successfully registered!");
    	} catch (Exception e) {
            System.out.println("An error occurred during sign up.");
            e.printStackTrace();
        }
    }


    private static House manageHouse(Scanner sc,User user) {
        ArrayList<Room> rooms = new ArrayList<>();
        int roomNos = 0;
        boolean fine = false;
        while (!fine) {
            try {
                System.out.println("How many rooms do you want to add to your house?");
                roomNos = sc.nextInt();
                sc.nextLine();
                if (roomNos < 2) {
                    throw new LessRoomsException("Rooms cannot be less than 2");
                }
                fine = true;
            } catch (LessRoomsException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }

        for (int i = 0; i < roomNos; i++) {
            Room room = selectRoom(sc);
            rooms.add(room);
        }
        House house = new House(rooms);
        user.setHouse(house);
        saveUsersToFile();
        return house;
    }

    private static Room selectRoom(Scanner sc) {
        System.out.println("+===================================+");
        System.out.println("|----->  Available Room Types <-----|");
        System.out.println("|===================================|");
        System.out.println("|1. Kitchen                         |");
        System.out.println("|2. Living Area                     |");
        System.out.println("|3. Dining Area                     |");
        System.out.println("|4. Bedroom                         |");
        System.out.println("|5. Washroom                        |");
        System.out.println("|6. Corridors                       |");
        System.out.println("+===================================+");
        System.out.println("Enter Room type (name) : ");
        String type = sc.nextLine();

        ArrayList<Device> devices = new ArrayList<>();
        int deviceType;
        while (true) {
            System.out.println("+===================================+");
            System.out.println("|-------> Available Devices <-------|");
            System.out.println("|===================================|");
            System.out.println("|1. Lights                          |");
            System.out.println("|2. Air Conditioner                 |");
            System.out.println("|3. Television                      |");
            System.out.println("|4. Shower                          |");
            System.out.println("|5. Done Adding Devices             |");
            System.out.println("+===================================+");
            System.out.println("Enter device type (number) : ");
            deviceType = sc.nextInt();
            sc.nextLine();

            switch (deviceType) {
                case 1:
                    devices.add(new Light());
                    break;
                case 2:
                    devices.add(new AirConditioner());
                    break;
                case 3:
                    devices.add(new Television());
                    break;
                case 4:
                    devices.add(new Shower());
                    break;
                case 5:
                    System.out.println("Exiting device selection menu..");
                    return new Room(type, devices);
                default:
                    System.out.println("Invalid choice! Please enter again.");
            }
        }
    }
    public static boolean performOprations(Scanner sc, House house) {
        System.out.println("Which operation do you want to perform?");
        System.out.println("+===================================+");
        System.out.println("|-----------> Oprations <-----------|");
        System.out.println("|===================================|");
        System.out.println("|1. Turn On Device                  |");
        System.out.println("|-----------------------------------|");
        System.out.println("|2. Turn Off Device 	            |");
        System.out.println("|===================================|");
        System.out.println("Enter your choice:");

        int operationChoice = sc.nextInt();
        sc.nextLine();

        switch (operationChoice) {
            case 1:
                
                return turnOnDevice(sc, house);
            case 2:
                return turnOffDevice(sc, house);
            default:
                System.out.println("Invalid choice! Please enter again.");
                return false;
        }
    }

	private static boolean turnOffDevice(Scanner sc, House house) {
			System.out.println("Enter the name of the room:");
		    String roomName = sc.nextLine();
		    if(!showDevices(house,roomName)) {
		    	System.out.println("Devices are Not-found in the Room");
		    }
		    System.out.println("Enter the name of the device to turn on:");
		    String deviceName = sc.nextLine();
		    
		return house.turnOffDevice(roomName, deviceName);
	}

	private static boolean turnOnDevice(Scanner sc, House house) {
			System.out.println("Enter the name of the room:");
		    String roomName = sc.nextLine();
		    if(!showDevices(house,roomName)) {
		    	System.out.println("Devices are Not-found in the Room");
		    }
		    System.out.println("Enter the name of the device to turn on:");
		    String deviceName = sc.nextLine();
		    
		return house.turnOnDevice(roomName, deviceName);
	}

	private static boolean showDevices(House house, String roomName) {
		for(Room room : house.getRooms()) {
	    	if(room.getType().equalsIgnoreCase(roomName)) {
	    		System.out.println("Name of Devices in the Room : ");
	    		for(Device device : room.getDevices()) {
	    			
	    			System.out.println(device.getDeviceType());
	    		}
	    		return true;
	    	}
	    }
		return false;
	}
	private static void loadUsersFromFile() {
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {

	        users = (ArrayList<User>) ois.readObject();
	        System.out.println("Users loaded successfully from file.");

	    } catch (FileNotFoundException e) {
	        System.out.println("No existing user data file found. Creating a new file.");
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Error loading users from file: " + e.getMessage());
	    }
	}
	
	private static void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(users);
            System.out.println("Users saved successfully to file.");

        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }
}
