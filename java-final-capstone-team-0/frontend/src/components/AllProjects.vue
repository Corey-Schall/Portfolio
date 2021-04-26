<template>
  <div id="background">
      <br>
      <br>
    <h1>My Projects</h1>
        <div id="projects">
            <project-tile v-bind:key='project.id' v-for="project in projects" :project = "project"/>
            <add-project/>
        </div>
    </div>
</template>

<script>
import ProjectTile from './ProjectTile.vue';
import AddProject from './AddProject.vue';

export default {
    name: 'AllProjects',
    components: {
        ProjectTile,
        AddProject
    },
    computed: {
        projects () {
            return this.$store.state.projects;
        }
    }

}
</script>

<style>
* {
    font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
}
    #projects{
        width: 70vw;
        margin: auto;
        border-radius: 15px;
        position: absolute;
        top: 50%;
        left: 50%;
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: space-around;
        align-items: center;
        overflow-y: scroll;
        height: 60%;
        color: #32425F;
    }
</style>

<style scoped>

    h1{
        text-align: center;
        padding-top: 50px;
        color: #32425F;
    }
</style>