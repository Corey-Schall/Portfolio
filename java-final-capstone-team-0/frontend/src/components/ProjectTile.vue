<template>
    <div id="projectBox" @click='click'>
      <h1>{{project.projectName}}</h1>
      <h2>{{project.styleName}}</h2>
      <p>{{project.description}}</p>
      <h2>{{project.regionName}}</h2>
    </div>
</template>

<script>
import ProjectService from '../services/ProjectService';

export default {
    name: 'projectTile',
    props: {
        project: Object
    },
    methods: {
        click() {
            ProjectService.getProjectDeets(this.project.projectId).then((response)=>{
                this.$store.commit("SET_CURRENT_PROJECT", response.data);
                this.$router.push(`${this.$store.state.currentProject.projectId}/Step${this.$store.state.currentProject.stepNumber}`)
            })
            
        }
    }
}
</script>

<style scoped>
    * {
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif, Arial, sans-serif;
    }
    #projectBox{
        height: 200px;
        width: 200px;
        text-align: center;
        background-color: #a6c4ff;
        border-radius: 15px;
        padding: 10px;
        box-shadow: 5px 5px 5px grey;
        margin-top: 10px;
    }
    #projectBox:hover{
    background-color: #b4cdff;
    transition: all .5s;
    transform : translateY(-10px);
    box-shadow: 5px 10px 10px grey;
    }

    h1, h2, p{
        color: #32425f;
    }
    h1{
        font-size: 25px;
    }
    h2{
        font-size: 15px;
        font-style: italic;
    }
</style>