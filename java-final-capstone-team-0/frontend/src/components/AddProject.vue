<template>
  <div id="addBox" @click='click'>
      <h1>Start a new Project</h1>
      <h2>+</h2>
    </div>
</template>

<script>
export default {
    methods: {
        click(){
            this.$router.push("/newproject")
        }
    }
}
</script>

<style scoped>
    #addBox{
        height: 200px;
        width: 200px;
        text-align: center;
        background-color: #a6c4ff;
        border-radius: 15px;
        padding: 10px;
        box-shadow: 5px 5px 5px grey;
        margin-top: 10px;
    }

    #addBox:hover{
    background-color: #b4cdff;
    transition: all .5s;
    transform : translateY(-10px);
    box-shadow: 5px 10px 10px grey;
    }

    h1, h2{
        color: #32425f;
        font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande';
    }
    h1{
        margin-top:16.75px;
        font-size: 25px;
    }

    h2{
        margin: 0px;
        font-size: 140px;
        transform : translateY(-40px);
    }
</style>