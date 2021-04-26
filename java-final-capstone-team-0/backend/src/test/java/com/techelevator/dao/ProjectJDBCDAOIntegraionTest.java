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

import com.techelevator.model.Project;

public class ProjectJDBCDAOIntegraionTest {

    /* Using this particular implementation of DataSource so that
     * every database interaction is part of the same database
     * session and hence the same database transaction */
    private static SingleConnectionDataSource dataSource;
    private ProjectJDBCDAO dao;

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

    	
    	dao = new ProjectJDBCDAO(jdbcTemplate);
    	
    	jdbcTemplate.update(sqlDummyUser);
    	jdbcTemplate.update(sqlDummyProject);
    	jdbcTemplate.update(sqlDummyProUser);
    	
    }
    @Test
    public void getAllProjectsbyUserId_test() {
    	List<Project> testObj = new ArrayList<>();
    	
    	testObj = dao.getAllProjectsByUserId(420);
    	
    	Assert.assertNotNull(testObj);
    	Assert.assertEquals(1, testObj.size());
    	Assert.assertEquals("dummy", testObj.get(0).getProjectName());
    }
    
    @Test
    public void createNewProject_test() {
    	Project testProj = new Project();
    	testProj.setProjectId(6969);
    	testProj.setFoundationLength(100);
    	testProj.setFoundationWidth(100);
    	testProj.setDescription("Pretty cool");
    	testProj.setProjectName("Dummyhaus");
    }
    
    @Test //this method being tested needs to take into account foreign key constraints
    public void deleteProject_test() {
    	boolean testbool = false;
    	
    	testbool = dao.deleteProject(420);
    	
    	Assert.assertTrue(testbool);
    	
    }
    
    @Test
    public void updateProject_test() {
    	Project testProj = new Project();
    	testProj.setProjectId(420);
    	testProj.setFoundationLength(100);
    	testProj.setFoundationWidth(100);
    	testProj.setDescription("Pretty cool");
    	testProj.setProjectName("Dummyhaus");
    	testProj.setStepNumber(1);
    	testProj.setRegionName("West");
    	testProj.setStyleName("Industrial");
    	
    	boolean testBool = dao.updateProject(testProj);
    	
    	Assert.assertEquals(true, testBool);
		Assert.assertEquals("Dummyhaus", testProj.getProjectName());
    	    	
    
    }
    @Test
    public void listAllAethestics_test() {
    	List testObj = new ArrayList();
    	
    	testObj = dao.listAllAestheticNames();
    	
    	Assert.assertEquals(9, testObj.size());
    	
    }
    @Test
    public void listAllRegions_test() {
    	
    	List testObj = new ArrayList();
    	
    	testObj = dao.listAllRegionNames();
    	
    	Assert.assertEquals(5, testObj.size());
    	
    }
    
    
}





