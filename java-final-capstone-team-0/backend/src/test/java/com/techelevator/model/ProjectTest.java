package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.techelevator.model.Project;
import com.techelevator.model.Floor;

public class ProjectTest {
	@Test
	public void Project_get_floors_out() {
		 Project testProject = new Project();
		 List<Floor>testObj = new ArrayList<>();
		 
		 
		 testProject.setProjectId(1);
		 testProject.setFoundationLength(100);
		 testProject.setFoundationWidth(100);
		 testProject.setProjectName("who cares");
		 testProject.setRegionName("A");
		 testProject.setStyleName("B");
		 
		 Floor testFloor = new Floor();
		 
		 testFloor.setFloorId(1);
		 testFloor.setFloorName("floor");
		 testFloor.setFloorOrder(0);
		 testFloor.setProjectId(1);
		 
		 
		testProject.addFloor(testFloor);
		
		testObj = testProject.getFloors();
		
		Assert.assertEquals("floor", testObj.get(0).getFloorName());
		
		 
	}

}
