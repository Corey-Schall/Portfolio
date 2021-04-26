package com.techelevator.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.FixtureDAO;
import com.techelevator.dao.FloorDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Fixture;
import com.techelevator.model.FixtureType;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class FixtureController {

	private FixtureDAO fixtureDAO;
	private UserDAO userDAO;

	
	public FixtureController(FixtureDAO fixtureDAO, UserDAO userDAO) {
		this.fixtureDAO = fixtureDAO;
		this.userDAO = userDAO;
	}
	
	@RequestMapping(value = "/{roomId}/listAllFixtures", method = RequestMethod.GET)
	public List<Fixture> listAllFixturesWithRoomId(@PathVariable int roomId, Principal principal){
		return fixtureDAO.listAllFixturesWithRoomId(roomId);
	};
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/addFixture", method = RequestMethod.POST)
	public Fixture addFixtureToRoom(@RequestBody Fixture fixture, Principal principal) {
		return fixtureDAO.addFixtureToRoom(fixture);
	};
	
	@RequestMapping(value = "/updateFixture", method = RequestMethod.PUT)
	public boolean updateFixture(@RequestBody Fixture fixture, Principal principal) {
		return fixtureDAO.updateFixture(fixture);
	};
	
	@RequestMapping(value = "/deleteFixture/{fixtureId}", method = RequestMethod.DELETE)
	public boolean deleteFixtureWithFixtureId(@PathVariable int fixtureId, Principal principal) {
		return fixtureDAO.deleteFixtureWithFixtureId(fixtureId);
	};
	
	@RequestMapping(value = "/listAllFixtureTypeDetails", method = RequestMethod.GET)
	public List<FixtureType> listAllFixtureTypeDetails(Principal principal){
		return fixtureDAO.listAllFixtureTypeDetails();
	};
}
