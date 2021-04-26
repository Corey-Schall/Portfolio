package com.techelevator.controller;


import javax.validation.Valid;

import com.techelevator.dao.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.model.Project;
import com.techelevator.model.Region;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class ProjectController {
	
	private ProjectDAO projectDAO;
	private UserDAO userDAO;
	private FloorDAO floorDAO;
	private RoomDAO roomDAO;
	private FixtureDAO fixtureDAO;
	
	public ProjectController(ProjectDAO projectDAO, UserDAO userDAO, FloorDAO floorDAO, RoomDAO roomDAO, FixtureDAO fixtureDAO) {
		this.projectDAO = projectDAO;
		this.userDAO = userDAO;
		this.floorDAO = floorDAO;
		this.roomDAO = roomDAO;
		this.fixtureDAO = fixtureDAO;
	}
	
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public List<Project> getAllProjectsByUserId(Principal principal){
		System.out.println(principal);
		return projectDAO.getAllProjectsByUserId(getCurrentUserId(principal));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/newProject", method = RequestMethod.POST)
	public Project createNewProject(@RequestBody Project project, Principal principal) {
		projectDAO.createNewProject(project);
		projectDAO.addProjectIdToProjectUserTable(getCurrentUserId(principal),project.getProjectId());
		return project;
	}
	
	@RequestMapping(value = "/deleteProject", method = RequestMethod.DELETE)
	public boolean deleteProject(int projectId, Principal principal) {
	return projectDAO.deleteProject(projectId);
	}

	@RequestMapping(value = "/aestheticNames", method = RequestMethod.GET)
	public List<String> listAllAestheticNames(Principal principal){
		return projectDAO.listAllAestheticNames();
	};
	
	@RequestMapping(value = "/regionNames", method = RequestMethod.GET)
	public List<String> listAllRegionNames(Principal principal){
		return projectDAO.listAllRegionNames();	
	};
	
	@RequestMapping(value = "/addProjectId", method = RequestMethod.POST)
	public boolean addProjectIdToProjectUserTable(long userId, int projectId, Principal principal) {
		return projectDAO.addProjectIdToProjectUserTable(userId, projectId);
	};
	
	@RequestMapping(value = "/updateProject", method = RequestMethod.PUT)
	public boolean updateProject(@RequestBody Project project, Principal principal) {
		return projectDAO.updateProject(project);
	};

	@RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
	public Project getProjectById(@PathVariable long projectId, Principal principal){
		Project project = projectDAO.getProjectById(projectId);
		project = floorDAO.populateProject(project);
		project = roomDAO.populateFloor(project);
		project = fixtureDAO.populateProject(project);
		return project;
	}
	
	@RequestMapping(value = "/listAllRegionDetails", method = RequestMethod.GET)
	public List<Region> listAllRegionDetails(Principal principal){
		return projectDAO.listAllRegionDetails();
	}
	
//  * Finds the user by username and returns the id
//  * @param principal the current authenticated user
//  * @return Long user_id
	private long getCurrentUserId(Principal principal) {
        return userDAO.findByUsername(principal.getName()).getId();
    }
}
