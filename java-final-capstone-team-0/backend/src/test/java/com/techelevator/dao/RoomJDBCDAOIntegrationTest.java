package com.techelevator.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Room;

public class RoomJDBCDAOIntegrationTest {
	

	    /* Using this particular implementation of DataSource so that
	     * every database interaction is part of the same database
	     * session and hence the same database transaction */
	    private static SingleConnectionDataSource dataSource;
	    private RoomJDBCDAO dao;

	    /* Before any tests are run, this method initializes the datasource for testing. */
	    @BeforeClass
	    public static void setupDataSource() {
	        dataSource = new SingleConnectionDataSource();
	        dataSource.setUrl("jdbc:postgresql://localhost:5432/final_capstone");
	        dataSource.setUsername("final_capstone_appuser");
	        dataSource.setPassword("finalcapstone");
	        /* The following line disables autocommit for connections
	         * returned by this DataSource. This allows us to rollback
	         * any changes after each test */
	        dataSource.setAutoCommit(false);
	    }

	    /* After all tests have finished running, this method will close the DataSource */
	    @AfterClass
	    public static void closeDataSource() throws SQLException {
	        dataSource.destroy();
	    }

	    /* After each test, we rollback any changes that were made to the database so that
	     * everything is clean for the next test */
	    @After
	    public void rollback() throws SQLException {
	        dataSource.getConnection().rollback();
	    }

	    /* This method provides access to the DataSource for subclasses so that
	     * they can instantiate a DAO for testing */
	    protected DataSource getDataSource() {
	        return dataSource;
	    }
	    @Before
	    public void setup() {
	    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	    	
	    	String sqlDummyUser = "INSERT INTO users (user_id, username, password_hash, role) VALUES (420, 'test', 'test', 'ROLE_USER');";
	    	String sqlDummyProject = "INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_name, description, stylename) VALUES (420, 'dummy', 1, 1, 'West', 'test', 'Mid-Century Modern');";
	        String sqlDummyProUser = "INSERT INTO project_user (project_id, user_id) VALUES ((SELECT project_id FROM project WHERE project_id = 420), (SELECT user_id FROM users WHERE user_id = 420));";
	        String sqlDummyFloor = "INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES (420420, 420, 'Twentieth Floor', 3)";
	        String sqlDummyRoom = "INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename)"
	        		+ "VALUES (8888, 'Cardboard box', 420420, 'Tile', 10, 18, 0, 0, 'Drywall', 'Industrial')";
	    	
	    	dao = new RoomJDBCDAO(jdbcTemplate);
	    	
	    	jdbcTemplate.update(sqlDummyUser);
	    	jdbcTemplate.update(sqlDummyProject);
	    	jdbcTemplate.update(sqlDummyProUser);
	    	jdbcTemplate.update(sqlDummyFloor);
	    	jdbcTemplate.update(sqlDummyRoom);
	    	
	    }
	    
	    @Test
	    public void list_all_rooms_with_floor_id_test() {
	    	List<Room> test = new ArrayList<>();
	    	
	    	test = dao.listAllRoomsWithFloorId(420420);
	    	
	    	Assert.assertNotNull(test);
	    	Assert.assertEquals(1, test.size());
	    	Assert.assertEquals("Tile", test.get(0).getFloorTypeName());
	    }
	    
	    @Test
	    public void create_room_test() {
	    	Room room = new Room();
	    	room.setFloorId(3);
	    	room.setFloorTypeName("Carpet");
	    	room.setLength(123456789);
	    	room.setRoomId(18);
	    	room.setRoomName("My library");
	    	room.setStyleName("Industrial");
	    	room.setWallTypeName("Drywall");
	    	room.setWidth(123);
	    	room.setxCoordinate(0);
	    	room.setyCoordinate(0);
	    	
	    	Room test = dao.createRoom(room);
	    	
	    	Assert.assertEquals("My library", test.getRoomName());
	    }

	    @Test
	    public void update_room_test() {
	    	Room room = new Room();
	    	room.setFloorId(420420);
	    	room.setFloorTypeName("Carpet");
	    	room.setLength(123456789);
	    	room.setRoomId(8888);
	    	room.setRoomName("My library");
	    	room.setStyleName("Industrial");
	    	room.setWallTypeName("Drywall");
	    	room.setWidth(123);
	    	room.setxCoordinate(33);
	    	room.setyCoordinate(33);
	    	
	    	boolean test = dao.updateRoom(room);
	    	Assert.assertEquals(true, test);
	    
	    }
	    @Test
	    public void list_wall_type_names_test() {
	    	List<String> test = new ArrayList();
	    	
	    	test = dao.listWallTypeNames();
	    	
	    	Assert.assertEquals(2, test.size());
	    	
	    }
	    
	    @Test
	    public void list_floor_type_names_test() {
	    	List<String> test = new ArrayList();
	    	
	    	test = dao.listFloorTypeNames();
	    	
	    	Assert.assertEquals(4, test.size());
	    	
	    }
	    
	    @Test
	    public void list_fixture_names_test() {
	    	List<String> test = new ArrayList();
	    	
	    	test = dao.listFixtureNames();
	    	
	    	Assert.assertEquals(15, test.size());
	    	
	    }

	    
	    
	

}
