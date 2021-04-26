package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

	int projectId = 0;
	int foundationLength = 0;
	int foundationWidth = 0;
	String regionName;
	String styleName;
	String description;
	String projectName;
	int stepNumber = 0;
	List<Floor> floors = new ArrayList<>();
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getFoundationLength() {
		return foundationLength;
	}
	public void setFoundationLength(int foundationLength) {
		this.foundationLength = foundationLength;
	}
	public int getFoundationWidth() {
		return foundationWidth;
	}
	public void setFoundationWidth(int foundationWidth) {
		this.foundationWidth = foundationWidth;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public List<Floor> getFloors(){
		return floors;
	}
	
	public void addFloor(Floor floor) {
		floors.add(floor);
	}
	
	public int getStepNumber() {
		return stepNumber;
	}
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}
	


	
	
	
}
