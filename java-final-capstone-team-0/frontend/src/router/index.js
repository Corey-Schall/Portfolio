import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import Landing from '../views/LandingPage.vue'
import store from '../store/index'

import NewProjectStep1 from '../views/NewProject1.vue'
import NewProjectStep2 from '../views/NewProject2.vue'
import NewProjectStep3 from '../views/NewProject3.vue'
import NewProjectStep4 from '../views/NewProject4.vue'
import NewProjectStep5 from '../views/NewProject5.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/landing",
      name: "landing",
      component: Landing,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/newproject",
      name: "new-project-test",
      component: NewProjectStep1,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/:projectId/Step2",
      name: 'new-project-step-2',
      component: NewProjectStep2,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/:projectId/Step3",
      name: 'new-project-step-3',
      component: NewProjectStep3,
      meta: {
        requiresAuth: true
      }
    },  
    {
      path:"/:projectId/Step4/:roomId/:roomWidthFt",
      name: 'step-five-room-fixture-layout',
      component: NewProjectStep4,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/:projectId/step5",
      name: 'project-step5',
      component: NewProjectStep5,
      meta: {
        requiresAuth: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/landing");
    // This has been changed once the authentication 
    // fails it will route them to landing
    // Landing will now have to route to register and login
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
