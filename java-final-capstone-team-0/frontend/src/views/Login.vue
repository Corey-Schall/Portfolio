<template>
<div id='grid'>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <button type="submit">Sign in</button>
      <br/>
      <router-link :to="{ name: 'register' }">Need an account?</router-link>
    </form>
  </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
<style scoped>
* {
    font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif, Arial, sans-serif;
}
body{
  background-color: #3563D9;
  color: #32425f;
  margin: 0;
  padding: 0;
  
  width: 100%;
  height: 100vh;
  font-family: Arial, Helvetica, sans-serif;
}
#grid{
  top: 0px;
  right: 0px;
  bottom: 0px;
  left: 0px;
  background-color: #3563D9;
  width: 100%;
  height: 100vh;
  background-image: linear-gradient(#5C94FF, transparent .1em), linear-gradient(90deg, #5C94FF, transparent .1em);
  background-size: 3em 3em;
}
#login{
  background-color: #f4f0e5;
  padding: 30px;
  margin: auto;
  border-radius: 15px;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);

  display:flex;
  align-content: center;
  justify-content: center;
  align-items: center;

  box-shadow: 2px 2px 5px navy;

}
input{
  margin-left: 20px;
  margin-bottom: 20px;
  padding: 10px;
  padding-right: 200px;
  background-color: #A6c4ff;
  border-radius: 15px;
  border-top-style: hidden;
  border-right-style: hidden;
  border-left-style: hidden;
  border-bottom-style: hidden;

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
}

form{
  display: flex;
  flex-direction: column;
  align-content: center;
  justify-content: center;
  align-items: center;
}

::placeholder{
  color:#3563D9;
}

h1, p{
  color: #32425f;
}
</style>