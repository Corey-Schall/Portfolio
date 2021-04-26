import axios from 'axios';

export default {

  getAllFixturesWithRoomId(roomId) {
    return axios.get(`${roomId}/listAllFixtures`);
  },

  addFixtures(fixture) {
    return axios.post('/addFixture', fixture);
  },
  
  saveFixturePosition(fixture){
    return axios.put('/updateFixture', fixture);
  }, 

  deleteFixture(fixtureId){
      return axios.delete('/deleteFixture/'+fixtureId);
  },

  listFixtureTypes(){
    return axios.get('/listAllFixtureTypeDetails');
  },

  getWallTypes(){
    return axios.get('/wallNames');
  },

  getFloorTypes(){
    return axios.get('/floorTypes');
  },

  listWallTypeDetails(){
    return axios.get('/listWallTypeDetails');
  },

  listFloorTypeDetails(){
    return axios.get('/listFloorTypeDetails');
  }
}