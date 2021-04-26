package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Floor;
import com.techelevator.model.Project;

public interface FloorDAO {
	public List<Floor> listAllFloors();

	public Floor createFloor(Floor floor);

	public boolean updateFloor(Floor floor);

	public List<Floor> listFloorsPerProject(int projectId);

	Project populateProject(Project project);

}
