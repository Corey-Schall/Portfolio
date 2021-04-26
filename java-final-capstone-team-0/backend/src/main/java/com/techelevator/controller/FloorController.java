package com.techelevator.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.FloorDAO;
import com.techelevator.dao.RoomDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Floor;
import com.techelevator.model.Project;
import com.techelevator.model.Room;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class FloorController {

	private FloorDAO floorDAO;
	private UserDAO userDAO;

	public FloorController(FloorDAO floorDAO, UserDAO userDAO) {
		this.floorDAO = floorDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(value = "/floor", method = RequestMethod.GET)
	public List<Floor> listAllFloors(Principal principal) {
		return floorDAO.listAllFloors();
	};

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/floor", method = RequestMethod.POST)
	public Floor createFloor(@RequestBody Floor floor, Principal principal) {
		return floorDAO.createFloor(floor);
	};

	public boolean updateFloor(Floor floor, Principal principal) {
		return floorDAO.updateFloor(floor);
	};


}
