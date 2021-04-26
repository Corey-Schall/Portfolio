package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Floor;
import com.techelevator.model.Project;
import com.techelevator.model.Region;

public interface ProjectDAO {
	List<Project> getAllProjectsByUserId(long userId);

	Project getProjectById(long projectId);

	Project createNewProject(Project newProject);

	boolean deleteProject(int projectId);

	List<String> listAllAestheticNames();

	List<String> listAllRegionNames();

	boolean addProjectIdToProjectUserTable(long userId, int projectId);

	boolean updateProject(Project project);
	
	List<Region> listAllRegionDetails();

}
