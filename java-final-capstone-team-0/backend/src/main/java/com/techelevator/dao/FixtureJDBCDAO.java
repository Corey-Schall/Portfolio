package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class FixtureJDBCDAO implements FixtureDAO{

	private JdbcTemplate jdbcTemplate;

	public FixtureJDBCDAO(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Fixture> listAllFixturesWithRoomId(int roomId) {
		List<Fixture> fixtureList = new ArrayList<>();
		String sql = "SELECT fixture_id, room_id, fixture_type, x_coordinate, y_coordinate FROM fixture WHERE room_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, roomId);
		while (results.next()) {
			Fixture fixture = mapRowToFixture(results);
			fixtureList.add(fixture);
		}
		return fixtureList;
		
	}

	// Corey wrote this method, double-check that it works as intended.
	@Override
	public Fixture addFixtureToRoom(Fixture fixture) {
		String sql = "INSERT INTO fixture (fixture_id, room_id, fixture_type, x_coordinate, y_coordinate) VALUES (?, ?, ?, ?, ?)";
		int newFixtureId = getNextFixtureId();
		fixture.setFixtureId(newFixtureId);

		jdbcTemplate.update(sql, newFixtureId, fixture.getRoomId(),fixture.getFixtureType(), fixture.getxCoordinate(), fixture.getyCoordinate());

		return fixture;
	}

	// Corey wrote this method, double-check that it works as intended.
	@Override
	public boolean updateFixture(Fixture fixture) {
		String sql = "UPDATE fixture SET room_id = ?, fixture_type = ?, x_coordinate = ?, y_coordinate = ? WHERE fixture_id = ?";
		return jdbcTemplate.update(sql, fixture.getRoomId(), fixture.getFixtureType(), fixture.getxCoordinate(), fixture.getyCoordinate(), fixture.getFixtureId()) == 1;
	}

	// Corey wrote this method, double-check that it works as intended.
	@Override
	public boolean deleteFixtureWithFixtureId(int fixtureId) {
		String sql = "DELETE FROM fixture WHERE fixture_id = ?;";
		return jdbcTemplate.update(sql, fixtureId) == 1;
	}
	
	private int getNextFixtureId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_fixture_id'::regclass)");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id");
		}
	}

	private Fixture mapRowToFixture(SqlRowSet results) {
		Fixture fix = new Fixture();

		fix.setFixtureId(results.getInt("fixture_id"));
		fix.setRoomId(results.getInt("room_id"));
		fix.setFixtureType(results.getString("fixture_type"));
		fix.setxCoordinate(results.getDouble("x_coordinate"));
		fix.setyCoordinate(results.getDouble("y_coordinate"));
		
		return fix;
	}

	@Override
	public List<FixtureType> listAllFixtureTypeDetails() {
		List<FixtureType> list = new ArrayList<>();
		String sql = "SELECT fixture_type, length, width, economic_cost, average_cost, high_end_cost FROM fixture_type";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			FixtureType f = mapRowToFixtureType(results);
			list.add(f);
		}
		return list;
	}

	@Override
	public Project populateProject(Project project){
		for(Floor floor: project.getFloors()){
			for(Room room: floor.getRooms()){
				room.setFixtures(listAllFixturesWithRoomId(room.getRoomId()));
			}
		}
		return project;
	}
	
	private FixtureType mapRowToFixtureType(SqlRowSet results) {
		FixtureType fix = new FixtureType();

		fix.setFixtureType(results.getString("fixture_type"));
		fix.setLength(results.getInt("length"));
		fix.setWidth(results.getInt("width"));
		fix.setEconomicCost(results.getBigDecimal("economic_cost"));
		fix.setAverageCost(results.getBigDecimal("average_cost"));
		fix.setHighEndCost(results.getBigDecimal("high_end_cost"));
		
		return fix;
	}

}
