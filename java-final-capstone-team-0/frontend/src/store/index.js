import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    roomNumber: 0,
    wallTypes: [],
    floorTypes: [],
    token: currentToken || '',
    user: currentUser || {},
    isLoading: false,
    projects: [],
    aesthetics: [],
    regions: [],
    fixtures: [],
    currentProject: {
        name: null,
        foundationLength: 0,
        foundationWidth: 0,
        region: null,
        description: null,
        aesthetic: null,
        stepNumber: 1
    },
    // update to save position
    dummyCoordinates: {
      objectName: null,
      positionX: 0,
      positionY: 0
    }
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_PROJECTS(state, projects){
      state.projects = projects;
    },
    SET_STYLES(state, aesthetics){
      state.aesthetics = aesthetics;
    },
    SET_REGIONS(state, region){
      state.regions = region;
    },
    SET_CURRENT_PROJECT(state, project){
      state.currentProject = project;
    },
    FLIP_ISLOADING(state){
      state.isLoading = !state.isLoading;
      
    },
    SAVE_DUMMY_COORDINATES(state, dummyCoordinates){
      state.dummyCoordinates = dummyCoordinates;
    }, 
    SET_FIXTURES(state, fixtures){
      state.fixtures = fixtures;
    }, 
    SET_WALL_TYPES(state, wallTypes){
      state.wallTypes = wallTypes;
    }, 
    SET_FLOOR_TYPES(state, floorTypes){
      state.floorTypes = floorTypes;
    },
    SET_PROJECT_STEP(state, step){
      state.currentProject.stepNumber = step;
    },
    SET_ROOM_NUMBER(state, number){
      state.roomNumber = number;
    }
  }
})
