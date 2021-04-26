package com.techelevator.dao;

import java.util.ArrayList;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Project;
import com.techelevator.model.Region;

@Service
public class ProjectJDBCDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public ProjectJDBCDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Add project to user table and project-user table

	@Override
	public List<Project> getAllProjectsByUserId(long userId) {
		List<Project> projectList = new ArrayList<>();
		String sql = "SELECT project_id, project_name, foundation_length, foundation_width, region_name, description, stylename, user_id, step_number "
				+ "FROM project JOIN project_user USING(project_id) WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		while (results.next()) {
			Project project = mapRowToProject(results);
			projectList.add(project);
		}
		return projectList;
	}

	@Override
	public Project getProjectById(long projectId){
		Project project = null;
		String sql = "SELECT project_id, project_name, foundation_length, foundation_width, region_name, description, stylename, step_number " +
				"FROM project WHERE project_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
		while(results.next()){
			project = mapRowToProject(results);
		}
		return project;
	}

	@Override
	public Project createNewProject(Project newProject) {
		String sql = "INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_name, description, stylename, step_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		int newProjectId = getNextProjectId();
		newProject.setProjectId(newProjectId);

		jdbcTemplate.update(sql, newProjectId, newProject.getProjectName(), newProject.getFoundationLength(),
				newProject.getFoundationWidth(), newProject.getRegionName(), newProject.getDescription(),
				newProject.getStyleName(), newProject.getStepNumber());

		return newProject;
	}

	@Override
	public boolean addProjectIdToProjectUserTable(long userId, int projectId) {
		String sql = "INSERT INTO project_user (project_id, user_id) " + "VALUES (?,?)";
		return jdbcTemplate.update(sql, projectId, userId) == 1;
	}

	@Override //
	public boolean deleteProject(int projectId) {
		String sql = "DELETE FROM project_user WHERE project_id = ?;\n" + "DELETE FROM project WHERE project_id = ?;";

		return jdbcTemplate.update(sql, projectId, projectId) == 1;
	}

	@Override
	public boolean updateProject(Project project) {
		String sql = "UPDATE project SET project_name = ?, foundation_length = ?, foundation_width = ?, region_name = ?, description = ?, stylename = ?, step_number = ? WHERE project_id = ?";
		return jdbcTemplate.update(sql, project.getProjectName(), project.getFoundationLength(),
				project.getFoundationWidth(), project.getRegionName(), project.getDescription(), project.getStyleName(),
				project.getStepNumber(), project.getProjectId()) == 1;
	}

	@Override
	public List<String> listAllAestheticNames() {
		List<String> aestheticList = new ArrayList<>();
		String sql = "SELECT stylename FROM aesthetic";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			aestheticList.add(results.getString("stylename"));
		}
		return aestheticList;
	}

	@Override
	public List<String> listAllRegionNames() {
		List<String> regionList = new ArrayList<>();
		String sql = "SELECT region_name FROM region";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			String result = results.getString("region_name");
			regionList.add(result);
		}
		return regionList;
	}

	private int getNextProjectId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_project_id'::regclass)");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id");
		}
	}

	private Project mapRowToProject(SqlRowSet results) {
		Project newProject = new Project();
		newProject.setProjectId(results.getInt("project_id"));
		newProject.setProjectName(results.getString("project_name"));
		newProject.setFoundationLength(results.getInt("foundation_length"));
		newProject.setFoundationWidth(results.getInt("foundation_width"));
		newProject.setRegionName(results.getString("region_name"));
		newProject.setDescription(results.getString("description"));
		newProject.setStyleName(results.getString("stylename"));
		newProject.setStepNumber(results.getInt("step_number"));
	

		return newProject;
	}

	@Override
	public List<Region> listAllRegionDetails() {
		
		List<Region> list = new ArrayList<>();
		String sql = "SELECT region_name, region_cost FROM region";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Region reg = mapRowToRegion(results);
			list.add(reg);
		}
		return list;
	}
	
	private Region mapRowToRegion(SqlRowSet results) {
		Region r = new Region();
		r.setRegionName(results.getString("region_name"));
		r.setRegionCost(results.getBigDecimal("region_cost"));
		
	

		return r;
	}

}
