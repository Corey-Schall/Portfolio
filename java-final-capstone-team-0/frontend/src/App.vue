<template>
  <div id="app">
    <header-banner v-if="$store.state.token != ''" id = 'nav'/>
    <router-view />
  </div>
</template>

<script>
import headerBanner from "./components/HeaderBanner";
import ProjectService from "./services/ProjectService";
import RoomService from "./services/RoomService";

export default({
  components: {
    headerBanner
  },
  created() {
    if(this.$store.state.token != ''){
      if(this.$route.params.projectId){
        ProjectService.getProjectDeets(this.$route.params.projectId).then((response)=>{
          this.$store.commit('SET_CURRENT_PROJECT', response.data);
        });
      }
      ProjectService.getAllProjects().then((response)=>{
        console.log(response);
        this.$store.commit('SET_PROJECTS', response.data);
      });

      ProjectService.getStyles().then((response)=>{
        console.log(response);
        this.$store.commit('SET_STYLES', response.data);
      });

      ProjectService.getRegions().then((response)=>{
        console.log(response);
        this.$store.commit('SET_REGIONS', response.data);
      });

      RoomService.listFixtureTypes().then((response)=>{
        this.$store.commit('SET_FIXTURES', response.data);
      });

      RoomService.listWallTypeDetails().then((response)=>{
        this.$store.commit('SET_WALL_TYPES', response.data);
      });

      RoomService.listFloorTypeDetails().then((response)=>{
        this.$store.commit('SET_FLOOR_TYPES', response.data);
      });
    }
  }
})
</script>


<style>
  body {
        background-color: #f6f1e3;
  }
  #logout{
    color: #f6f1e3;
    text-decoration: none;
  }

  #app{
    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;
    
  }

</style>
