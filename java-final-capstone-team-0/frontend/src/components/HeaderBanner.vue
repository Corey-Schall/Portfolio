<template>
    <div id="banner">
        <div id="banner-box">
            <img id="corner-logo" src="../assets/TE_final_capstone_housebuilder_logo_sized_light_blue.png" @click="$router.push({name: 'home'})"/>
            <h1 id="name">H<span>&auml;</span>usBuildr</h1>
                <span id="spacer"></span>
                <router-link v-bind:to="{name: 'home'}" id="current-projects" class="user-option">My Projects</router-link>
                <router-link v-bind:to="{name: 'new-project-test'}" id="new-project" class="user-option">New Project</router-link>
                
                <div class="dropdown">
                    <h1 class="dropbtn">{{$store.state.user.username}}</h1>
                    <div class="dropdown-content">
                        <router-link v-bind:to="{name: 'logout'}" id="account-info" class="user-option">Logout</router-link>
                    </div>
                </div>
            
        </div>
    </div>
</template>

<script>
export default {
    name: 'header-banner',
}
</script>


<style scoped>

#banner-box {
    height: 80px;
    width: 100vw;
    background-color: #32425F;
    color: white;
    font-weight: bold;
    display: flex;
    align-items: center;
    white-space: nowrap;
    position: fixed;
    width: 100%;
    top: 0px;
    right: 0px;
    left: 0px;
    font-size: 22px;
}

.user-option {
    padding: 0px 30px;
}

#current-projects:hover , #new-project:hover {
    color:#A6C4FF;
}

#corner-logo {
    height: 80%;
    width: 6.66%;
    min-width: 5%;
    object-fit: contain;
    object-position: 8px;
    position: relative ;
    
}

a:link, a:visited {
    text-decoration: none;
    color: inherit;
}

.dropbtn {
  color: white;
  padding: 0px 30px;
  font-size: 16px;
  border: none;
  text-align: center;
  z-index: 1;
  font-size: 22px;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: fixed;
  background-color: #c4c4c4;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: -1;
}

.dropdown-content a{
  color: #32425F;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {background-color: #ddd;}

.dropdown:hover .dropdown-content {display: block;}

#name {
    color: #a6c4ff;
   object-position: 10px;
    margin-left: 2vw;
}

#spacer{
    flex-grow: 2;
    flex-shrink: 1;
}


</style>