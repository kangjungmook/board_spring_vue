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
    component: HomeView,
    meta: { title: '게시판' }
  },
  {
    path: '/BoardDetail/:id',
    name: 'BoardDetail',
    component: BoardDetail,
    meta: { title: '게사판 상세보기' }
  },
  {
    path: '/BoardModify/:id',
    name: 'BoardModify',
    component: BoardModify,
    meta: { title: '게사판 수정하기' }
  },
  {
    path: '/write', 
    name: 'BoardWrite',
    component: BoardWrite,
    meta: { title: '게사판 등록하기' }
  },
  {
    path: '/signup', 
    name: 'BoardSignup', 
    component: BoardSignup,
    meta: { title: '게사판 회원가입' }
  },
  {
    path: '/login', 
    name: 'BoardLogin',
    component: BoardLogin,
    meta: { title: '게사판 로그인' }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  document.title = to.meta.title || '아무거나';
  next();
});

export default router
