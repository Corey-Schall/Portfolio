<template>
<div class="grid">
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 id="title" class="h3 mb-3 font-weight-normal">Create a new account!</h1>
      <p>Please complete all forms before hitting create account.</p>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
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
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      <p><router-link :to="{ name: 'login' }">Have an account?</router-link></p>
    </form>
  </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
body{
  color: #32425f;
  margin: 0;
  padding: 0;
  
  width: 100%;
  height: 100vh;
  font-family: Arial, Helvetica, sans-serif;
}
.grid{
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
#register{
  background-color: #f4f0e5;
  padding: 10px;
  margin: auto;
  width: 40%;
  height: 60%;
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
