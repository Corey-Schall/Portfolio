<template>
    <div id="landing-page">
        
            <img class="logo" src="../assets/TE_final_capstone_housebuilder_logo_sized_light_blue.png"/>
            <img class="sidebar-banner" src="https://images.pexels.com/photos/1109541/pexels-photo-1109541.jpeg?auto=compress&cs=tinysrgb&h=650&w=940"/>
      
        <div class="landing-menu">
            <div class="landing-menu-header"></div>
            <div class="landing-menu-text">
                <h1>Welcome to Haüsbuildr!</h1>
                <h3>
                    Start creating your dream home today!
                </h3>
                <p>
                    Haüsbuildr is a program that allows you to choose your house footprint, 
                    add floors, choose your rooms, customize options, and more. Put yourself
                    in charge of making the home of your dreams!
                </p>
                <div class="landing-page-options">
                    <div class="options-box">
                        <h3 style="text-align: center;">Login or sign up now!</h3>
                        <button class="login" @click="$router.push({name: 'login'})">Login</button>
                        <button class="register" @click="$router.push({name: 'register'})">Create an Account</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>

</script>

<style scoped>
#landing-page {
    color: #32425F;
    display: flex;
    flex-direction: row;
}
.sidebar-banner {
    width: 40vw;
    object-fit: cover;
    object-position: 60% 20%;
}
.logo{
    height: auto;
    width: 38vw;
    position: absolute;
    align-self: auto;
    bottom: 20%;
}
.landing-menu {
    min-width: 250px;
    height: 100vh;
    flex-basis: 100%;
    background-color: #F6F1E3; 
    display: flex;
    flex-direction: column;
}
.landing-menu-header {
    height: 40px;
    background-color:#32425F;
}
.landing-menu-text {
    display: flex;
    flex-direction: column;
    padding: 0px 30px;
    margin: 0px 0px 0px 15px;
}
.landing-menu-text > p {
    padding: 0px 50px;
}
.landing-page-options {
    align-self: center;
}
.options-box {
    background-color: #DADADA;
    width: 270px;
    justify-content: center;
    display: flex;
    flex-direction: column;
    padding: 15px 25px;
    margin: 30px 0px 0px 0px;
    border-radius: 10px;
}
button {
    text-align: center;
    padding: 10px;
    margin: 10px;
    border-radius: 15px;
    border-width: 0px;
    max-width: 250px;
    cursor: pointer;
    font-weight: bold;
}
button a {
    text-decoration: none;
}
a:link, a:visited {
    color: inherit;
}
.login {
    background-color: #A6C4FF;
    color: #3563D9;
}
.register {
    background-color: #3563D9;
    color: #A6C4FF;
}
</style>
