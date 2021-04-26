package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Fixture;
import com.techelevator.model.FixtureType;
import com.techelevator.model.Project;

public interface FixtureDAO {

	List<Fixture> listAllFixturesWithRoomId(int roomId);
	
	Fixture addFixtureToRoom(Fixture fixture);
	
	boolean updateFixture(Fixture fixture);
	
	boolean deleteFixtureWithFixtureId(int fixtureId);
	
	List<FixtureType> listAllFixtureTypeDetails();

	Project populateProject(Project project);
}
