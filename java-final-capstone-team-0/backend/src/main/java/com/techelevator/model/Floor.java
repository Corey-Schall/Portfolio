package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {
	
private int floorId = 0;
private int projectId = 0;
private String floorName = "";
private int floorOrder = 0;
private List<Room> rooms = new ArrayList<>();

public int getFloorId() {
	return floorId;
}
public void setFloorId(int floorId) {
	this.floorId = floorId;
}
public int getProjectId() {
	return projectId;
}
public void setProjectId(int projectId) {
	this.projectId = projectId;
}
public String getFloorName() {
	return floorName;
}
public void setFloorName(String floorName) {
	this.floorName = floorName;
}
public int getFloorOrder() {
	return floorOrder;
}
public void setFloorOrder(int floorOrder) {
	this.floorOrder = floorOrder;
}
public List<Room> getRooms(){
	return rooms;
}
public void addRooms(Room room) {
	rooms.add(room);
}


}
