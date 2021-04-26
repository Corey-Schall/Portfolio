<template>
  <div class="home">
    <all-projects/>
  </div>
</template>

<script>
import ProjectService from "../services/ProjectService";
import AllProjects from '../components/AllProjects';

export default {
  name: "home",
  components:{
    AllProjects
  },
  created() {
    ProjectService.getAllProjects().then((response)=>{
      console.log(response);
      this.$store.commit('SET_PROJECTS', response.data);
    });
  }
};
</script>

<style scoped>
* {
    font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif, Arial, sans-serif;
}

body{
  margin: 0;
  padding: 0;
  
  width: 100%;
  height: 100vh;
  background-color: #f6f1e3;

    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;

}
</style>
