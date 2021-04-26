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

import com.techelevator.model.Floor;

public class FloorJDBCDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private FloorJDBCDAO dao;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/final_capstone");
		dataSource.setUsername("final_capstone_appuser");
		dataSource.setPassword("finalcapstone");

		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sqlDummyProject = "INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_name, description, stylename) VALUES (420, 'dummy', 1, 1, 'West', 'test', 'Mid-Century Modern');";
		String sqlDummyFloor = "INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES (311, 420, 'The Gulag', 0);";

		dao = new FloorJDBCDAO(jdbcTemplate);

		jdbcTemplate.update(sqlDummyProject);
		jdbcTemplate.update(sqlDummyFloor);
	}

	@Test
	public void listAllFloors_test() {
		List<Floor> testObj = new ArrayList<>();

		testObj = dao.listAllFloors();

		Assert.assertEquals(4, testObj.size());
		Assert.assertEquals(311, testObj.get(3).getFloorId());
	}

	@Test
	public void listFloorsPerProject_test() {
		List<Floor> testObj = new ArrayList<>();

		testObj = dao.listFloorsPerProject(420);

		Assert.assertEquals(1, testObj.size());
		Assert.assertEquals("The Gulag", testObj.get(0).getFloorName());
	}

	@Test
	public void updateFloor_test() {
		Floor testFloor = new Floor();
		List<Floor> testObj = new ArrayList<>();

		testFloor.setFloorId(311);
		testFloor.setProjectId(420);
		testFloor.setFloorName("Sex Dungeon");
		testFloor.setFloorOrder(-1);

		boolean testBool = dao.updateFloor(testFloor);
		testObj = dao.listFloorsPerProject(420);

		Assert.assertTrue("Did not update", testBool);
		Assert.assertEquals(1, testObj.size());
		Assert.assertEquals("Sex Dungeon", testObj.get(0).getFloorName());

	}
	
	@Test
	public void createFloor_test() {
		
		Floor testFloor = new Floor();

		testFloor.setFloorId(42069);
		testFloor.setProjectId(420);
		testFloor.setFloorName("Skatepark");
		testFloor.setFloorOrder(2);
		
		Floor testFloor2 = dao.createFloor(testFloor);
		
		Assert.assertEquals("Skatepark", testFloor2.getFloorName());
	}
}
