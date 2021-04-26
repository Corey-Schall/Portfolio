import axios from 'axios';

export default {

  getAllProjects() {
    return axios.get('/projects');
  },

  addProject(project) {
    console.log(project);
    return axios.post('/newProject', project);
  },
  
  getStyles(){
    return axios.get('/aestheticNames');
  },

  getRegions(){
    return axios.get('/listAllRegionDetails');
  },

  addFloor(floor){
    return axios.post('/floor', floor);
  },

  addRoom(room){
    return axios.post('/createRoom', room);
  }, 
  
  saveRoomPosition(room){
    return axios.put('/updateRoom', room);
  },

  getProjectDeets(id){
    return axios.get(`/project/${id}`);
  }, 

  updateRoom(room){
    return axios.put(`/updateRoom`, room);
  },

  updateProject(project){
    return axios.put('/updateProject', project);
  }

}