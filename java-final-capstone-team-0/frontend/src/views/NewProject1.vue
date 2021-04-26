<template>
<div id="background">
   <form id="flexbox-format-container" @submit.prevent="addProject">
        <div id="grid">
            <h1>Step 1:</h1>
            <h1 id = "directions">Create project name, description, aesthetic, region, and size</h1>
            <br>
            <br>
            <input autofocus maxlength="30" required type="text" placeholder="Project Name" v-model="info.projectName"/>
            <br>
            <input maxlength="300" required type="text area" placeholder="Project Description" v-model="info.description"/>
            <br>
            <select name="Aesthetics"  v-model="info.styleName">
                <option v-bind:key = 'aesthetic.value' v-for="aesthetic in $store.state.aesthetics" v-bind:value= 'aesthetic'>{{aesthetic}}</option>
            </select>
            <br>
            <select name="Regions"  v-model="info.regionName">
                <option v-bind:key = 'region.value' v-for="region in $store.state.regions" v-bind:value= 'region.regionName'>{{region.regionName}}</option>
            </select>
            <br>
            <div id='foundationInput'>
                <input required :min="1" type="number" placeholder="Foundation Width" v-model="info.foundationWidth"/>
                <h1>X</h1>
                <input required :min="1" type="number" placeholder="Foundation Length" v-model="info.foundationLength"/>
                <h1>=</h1>
                <h2>{{info.foundationLength*info.foundationWidth}} Sq Ft</h2>
            </div>
            <br>
            <button type="submit">Next</button>
        </div>
    </form>
    </div>
</template>

<script>
import ProjectService from '../services/ProjectService';
export default {

    data() {
       return {
           info: {
                projectName: "",
                description: "",
                styleName: 'Pick an asthetic',
                regionName: "",
                foundationLength: "",
                foundationWidth: "",
                stepNumber: 2,
                error : false
           }
       }
    },
    methods: {
        addProject() {

                ProjectService.addProject(this.info).then((response)=>{
                this.$store.commit('SET_CURRENT_PROJECT', response.data);
            });
                this.$router.push(`/${this.$store.state.currentProject.projectId}/Step2`);
        }
    }
}
</script>

<style scoped>
   
    #flexbox-format-container {
        display: flex;
        flex-direction: column;
        align-content: center;
        justify-content: center;
    }
    #grid {
        width: 70vw;
        height: 86vh;
        background-color: #3563D9;
        background-image: linear-gradient(#5C94FF, transparent .1em), linear-gradient(90deg, #5C94FF, transparent .1em);
        background-size: 2em 2em;

        padding: 30px;
        margin: auto;
        border-radius: 15px;
        position: absolute;
        top: 62%;
        left: 50%;
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);

        display: flex;
        flex-direction: column;
    }

    input, select, h2{
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
        color: #3563D9;
        margin-left: 20px;
        margin-bottom: 20px;
        padding: 10px;
        background-color: #A6c4ff;
        border-radius: 15px;
        border-top-style: hidden;
        border-right-style: hidden;
        border-left-style: hidden;
        border-bottom-style: hidden;
        font-size: 1em;
    }
    ::placeholder{
        color:#3563D9;
    }
    button{
        background-color: #A6c4ff;
        border-radius: 15px;
        border-top-style: hidden;
        border-right-style: hidden;
        border-left-style: hidden;
        border-bottom-style: hidden;
        padding: 10px;
        color:#3563D9;
        font-size: 1.3em;
    }
    h1{
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
        color: #A6c4ff;
        text-shadow: 3px 3px 3px navy;
        text-align: center;
        font-size: 2.5em;
    }
    #foundationInput{
        padding-right: 50px;
        padding-left: 50px;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
    
        
        
    }
    #directions {
        color: lightgray;
        font-size: 1.5em;
    }
    
    
</style>