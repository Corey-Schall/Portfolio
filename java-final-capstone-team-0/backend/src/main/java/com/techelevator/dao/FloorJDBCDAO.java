package com.techelevator.dao;

import java.util.ArrayList;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Floor;
import com.techelevator.model.Project;
import com.techelevator.model.Room;

@Service
public class FloorJDBCDAO implements FloorDAO{
	
	private JdbcTemplate jdbcTemplate;

	public FloorJDBCDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Floor> listAllFloors() {
		List<Floor> floorList = new ArrayList<>();

		String sql = "SELECT floor_id, project_id, floor_name, floor_order FROM floor";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Floor floor = mapRowToFloor(results);
			floorList.add(floor);
		}
		return floorList;
	}
	
	@Override
	public List<Floor> listFloorsPerProject(int projectId) {
		List <Floor> floorListPerProject = new ArrayList<>();
		String sql = "SELECT floor_id, project_id, floor_name, floor_order FROM floor WHERE project_id = ? ORDER BY floor_order ASC";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
		while(results.next()) {
			Floor floor = mapRowToFloor(results);
			floorListPerProject.add(floor);
		}
		
		return floorListPerProject;
	}

	@Override
	public boolean updateFloor(Floor floor) {
		String sql = "UPDATE floor SET floor_name = ?, floor_order = ?  WHERE floor_id = ?";
		return jdbcTemplate.update(sql, floor.getFloorName(), floor.getFloorOrder(), floor.getFloorId())==1;
	}
	
	private int getNextFloorId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_floor_id'::regclass)");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id");
		}

	}

	private Floor mapRowToFloor(SqlRowSet results) {
		Floor newFloor = new Floor();

		newFloor.setFloorId(results.getInt("floor_id"));
		newFloor.setProjectId(results.getInt("project_id"));
		newFloor.setFloorName(results.getString("floor_name"));
		newFloor.setFloorOrder(results.getInt("floor_order"));

		return newFloor;
	}

	@Override
	public Floor createFloor(Floor floor) {
		System.out.println(floor.getProjectId());
		String sql = "INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES (?, ? , ?, ?)";
		
		int newFloorId = getNextFloorId();
		floor.setFloorId(newFloorId);
		
		jdbcTemplate.update(sql, newFloorId, floor.getProjectId(), floor.getFloorName(), floor.getFloorOrder());
		
		return floor;
	}

	@Override
	public Project populateProject(Project project) {
		List<Floor> listOfFloors = listFloorsPerProject(project.getProjectId());

		for (Floor floor : listOfFloors) {
			project.addFloor(floor);
		}
		return project;

	}

}
