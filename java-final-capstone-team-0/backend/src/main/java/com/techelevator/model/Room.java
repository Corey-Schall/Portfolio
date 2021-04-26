package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private int roomId = 0;
	private String roomName = "";
	private int floorId = 0;
	private String floorTypeName = "Default";
	private int length = 200;
	private int width = 150;
	private double xCoordinate = -1.0;
	private double yCoordinate = -1.0;
	private String wallTypeName = "Drywall";
	private String styleName = "None";
	private List<Fixture> fixtures = new ArrayList<>();

	
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	public String getFloorTypeName() {
		return floorTypeName;
	}
	public void setFloorTypeName(String floorTypeName) {
		this.floorTypeName = floorTypeName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public double getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public double getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public String getWallTypeName() {
		return wallTypeName;
	}
	public void setWallTypeName(String wallTypeName) {
		this.wallTypeName = wallTypeName;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public List<Fixture> getFixtures() {
		return fixtures;
	}
	public void setFixtures(List<Fixture> fixtures){
		this.fixtures = fixtures;
	}
}
