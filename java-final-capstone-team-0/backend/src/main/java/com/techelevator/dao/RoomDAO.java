package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.FloorType;
import com.techelevator.model.Project;
import com.techelevator.model.Room;
import com.techelevator.model.WallType;

public interface RoomDAO {

	public Room createRoom(Room room);

	List<Room> listAllRoomsWithFloorId(int floorId);

	boolean updateRoom(Room room);

	List<String> listWallTypeNames();

	List<String> listFloorTypeNames();

	List<String> listFixtureNames();

	Project populateFloor(Project project);
	
	List<WallType> listWallTypeDetails();
	
	List<FloorType> listFloorTypeDetails();

}
