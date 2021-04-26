package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Floor;
import com.techelevator.model.FloorType;
import com.techelevator.model.Project;
import com.techelevator.model.Room;
import com.techelevator.model.WallType;

@Service
public class RoomJDBCDAO implements RoomDAO {

	private JdbcTemplate jdbcTemplate;

	public RoomJDBCDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Room> listAllRoomsWithFloorId(int floorId) {
		List<Room> roomList = new ArrayList<>();
		String sql = "SELECT room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename FROM room WHERE floor_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, floorId);
		while (results.next()) {
			Room room = mapRowToRoom(results);
			roomList.add(room);
		}
		return roomList;
	}

	@Override
	public Room createRoom(Room room) {
		String sql = "INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int newRoomId = getNextRoomId();
		room.setRoomId(newRoomId);

		jdbcTemplate.update(sql, newRoomId, room.getRoomName(), room.getFloorId(), room.getFloorTypeName(),
				room.getLength(), room.getWidth(), room.getxCoordinate(), room.getyCoordinate(), room.getWallTypeName(),
				room.getStyleName());

		return room;
	}

	@Override
	public boolean updateRoom(Room room) {
		String sql = "UPDATE room SET room_name = ?, floor_id = ?, floor_type_name = ?, "
				+ "length = ?, width = ?, x_coordinate = ?, y_coordinate = ?, "
				+ "wall_type_name = ?, stylename = ?  WHERE room_id = ?";
		return jdbcTemplate.update(sql, room.getRoomName(), room.getFloorId(), room.getFloorTypeName(), room.getLength(), room.getWidth(), room.getxCoordinate(), room.getyCoordinate(), room.getWallTypeName(), room.getStyleName(), room.getRoomId()) == 1;
	}

	@Override
	public List<String> listWallTypeNames() {
		List<String> wallTypeList = new ArrayList<>();
		String sql = "SELECT wall_type_name FROM wall_type";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			wallTypeList.add(results.getString("wall_type_name"));
		}
		return wallTypeList;

	}

	@Override
	public List<String> listFloorTypeNames() {
		List<String> listFloorNames = new ArrayList<>();
		String sql = "SELECT floor_type_name FROM floor_type";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			listFloorNames.add(results.getString("floor_type_name"));
		}
		return listFloorNames;
	}

	@Override
	public List<String> listFixtureNames() {
		List<String> fixtureList = new ArrayList<>();
		String sql = "SELECT fixture_type FROM fixture_type";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			fixtureList .add(results.getString("fixture_type"));
		}
		return fixtureList;
	}

	@Override
	public Project populateFloor(Project project) {
		List<Floor> listOfFloors = project.getFloors();
		for (Floor floor : listOfFloors) {
			List<Room> listOfRooms = listAllRoomsWithFloorId(floor.getFloorId());
			for (Room room : listOfRooms) {
				floor.addRooms(room);
			}
		}

		return project;
	}

	private int getNextRoomId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_room_id'::regclass)");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id");
		}
	}

	private Room mapRowToRoom(SqlRowSet results) {
		Room newRoom = new Room();

		newRoom.setRoomId(results.getInt("room_id"));
		newRoom.setRoomName(results.getString("room_name"));
		newRoom.setFloorId(results.getInt("floor_id"));
		newRoom.setFloorTypeName(results.getString("floor_type_name"));
		newRoom.setLength(results.getInt("length"));
		newRoom.setWidth(results.getInt("width"));
		newRoom.setxCoordinate(results.getDouble("x_coordinate"));
		newRoom.setyCoordinate(results.getDouble("y_coordinate"));
		newRoom.setWallTypeName(results.getString("wall_type_name"));
		newRoom.setStyleName(results.getString("stylename"));

		return newRoom;
	}

	@Override
	public List<WallType> listWallTypeDetails() {
		List<WallType> list = new ArrayList<>();
		String sql = "SELECT wall_type_name, wall_cost FROM wall_type";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			WallType wt = mapRowToWallType(results);
			list.add(wt);
		}
		return list;
	}

	@Override
	public List<FloorType> listFloorTypeDetails() {
		List<FloorType> list = new ArrayList<>();
		String sql = "SELECT floor_type_name, cost FROM floor_type";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			FloorType ft = mapRowToFloorType(results);
			list.add(ft);
		}
		return list;
	}
	
	private FloorType mapRowToFloorType(SqlRowSet results) {
		FloorType f = new FloorType();
		f.setFloorTypeName(results.getString("floor_type_name"));
		f.setCost(results.getBigDecimal("cost"));
		return f;
	}
	
	private WallType mapRowToWallType(SqlRowSet results) {
		WallType w = new WallType();
		w.setWallTypeName(results.getString("wall_type_name"));
		w.setWallCost(results.getBigDecimal("wall_cost"));
		return w;
	}

}
