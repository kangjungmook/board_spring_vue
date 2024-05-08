import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BoardDetail from '../components/BoardDetail.vue'
import BoardModify from '../components/BoardModify.vue'
import BoardWrite from '../components/BoardWrite.vue'
import BoardSignup from '../components/BoardSignup.vue'
import BoardLogin from '../components/BoardLogin.vue'


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/BoardDetail/:id',
    name: 'BoardDetail',
    component: BoardDetail
  },
  {
    path: '/BoardModify/:id',
    name: 'BoardModify',
    component: BoardModify
  },
  {
    path: '/write', 
    name: 'BoardWrite',
    component: BoardWrite
  },
  {
    path: '/signup', 
    name: 'BoardSignup', 
    component: BoardSignup
  },
  {
    path: '/login', 
    name: 'BoardLogin',
    component: BoardLogin
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
