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

import com.techelevator.model.Fixture;
import com.techelevator.model.Room;

public class FixtureJDBCDAOIntegrationTest {
    /* Using this particular implementation of DataSource so that
     * every database interaction is part of the same database
     * session and hence the same database transaction */
    private static SingleConnectionDataSource dataSource;
    private FixtureJDBCDAO dao;

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
        String sqlDummyFixture = "INSERT INTO fixture (fixture_id, room_id, fixture_type, x_coordinate, y_coordinate) VALUES (9999, 8888, 'Refrigerator', 0, 0)";
    	
    	dao = new FixtureJDBCDAO(jdbcTemplate);
    	
    	jdbcTemplate.update(sqlDummyUser);
    	jdbcTemplate.update(sqlDummyProject);
    	jdbcTemplate.update(sqlDummyProUser);
    	jdbcTemplate.update(sqlDummyFloor);
    	jdbcTemplate.update(sqlDummyRoom);
    	jdbcTemplate.update(sqlDummyFixture);
    	
    }
    
    @Test
    public void list_all_fixtures_with_room_id_test() {
    	List<Fixture> test = new ArrayList<>();
    	
    	test = dao.listAllFixturesWithRoomId(8888);
    	
    	Assert.assertNotNull(test);
    	Assert.assertEquals(1, test.size());
    	Assert.assertEquals("Refrigerator", test.get(0).getFixtureType());
    }
    
    @Test
    public void add_fixture_to_room_test() {
    	Fixture fix = new Fixture();
    	fix.setFixtureId(123456789);
    	fix.setRoomId(8888);
    	fix.setFixtureType("Window");
    	fix.setxCoordinate(10);
    	fix.setyCoordinate(10);
    	
    	Fixture test = dao.addFixtureToRoom(fix);
    	
    	Assert.assertEquals("Window", test.getFixtureType());
    }

    @Test
    public void update_fixture_test() {
    	Fixture fix = new Fixture();
    	fix.setFixtureId(9999);
    	fix.setFixtureType("Window");
    	fix.setxCoordinate(20);
    	fix.setyCoordinate(20);
    	fix.setRoomId(8888);
    	
    	boolean test = dao.updateFixture(fix);
    	Assert.assertEquals(true, test);
    
    }
    @Test
    public void delete_fixture_test() {
    	Fixture fix = new Fixture();
    	fix.setFixtureId(9999);
    	fix.setFixtureType("Window");
    	fix.setxCoordinate(20);
    	fix.setyCoordinate(20);
    	
    	Assert.assertTrue(dao.deleteFixtureWithFixtureId(9999));
    	
    }
    

}
