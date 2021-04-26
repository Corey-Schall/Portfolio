package com.techelevator.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.RoomDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Floor;
import com.techelevator.model.FloorType;
import com.techelevator.model.Project;
import com.techelevator.model.Room;
import com.techelevator.model.WallType;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class RoomController {

	private RoomDAO roomDAO;
	private UserDAO userDAO;

	public RoomController(RoomDAO roomDAO, UserDAO userDAO) {
		this.roomDAO = roomDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(value = "/listAllRooms", method = RequestMethod.GET)
	public List<Room> listAllRoomsWithFloorId(int floorId, Principal principal) {
		return roomDAO.listAllRoomsWithFloorId(floorId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/createRoom", method = RequestMethod.POST)
	public Room createRoom(@RequestBody Room room, Principal principal) {
		return roomDAO.createRoom(room);
	}

	@RequestMapping(value = "/updateRoom", method = RequestMethod.PUT)
	public boolean updateRoom(@RequestBody Room room, Principal principal) {
		return roomDAO.updateRoom(room);
	}

	@RequestMapping(value = "/wallNames", method = RequestMethod.GET)
	public List<String> listWallTypeNames(Principal principal) {
		return roomDAO.listWallTypeNames();
	}

	@RequestMapping(value = "/floorTypeNames", method = RequestMethod.GET)
	public List<String> listFloorTypeNames(Principal principal) {
		return roomDAO.listFloorTypeNames();
	}

	@RequestMapping(value = "/fixtureNames", method = RequestMethod.GET)
	public List<String> listFixtureNames(Principal principal) {
		return roomDAO.listFixtureNames();
	}
	
	@RequestMapping(value = "/listWallTypeDetails", method = RequestMethod.GET)
	public List<WallType> listWallTypeDetails(Principal principal){
		return roomDAO.listWallTypeDetails();
	}
	
	@RequestMapping(value = "/listFloorTypeDetails", method = RequestMethod.GET)
	public List<FloorType> listFloorTypeDetails(Principal principal){
		return roomDAO.listFloorTypeDetails();
	}



}
