<template>
  <div>
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
  <div class="container-fluid">
    <router-link to="/" class="navbar-brand">게시판</router-link>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <router-link to="/" class="nav-link">홈</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/write" class="nav-link">게시물 작성</router-link>
        </li>
      </ul>
      <ul class="navbar-nav ms-auto">
        <li class="nav-item" v-if="isLoggedIn">
          <router-link to="#" class="nav-link text-primary">{{ loggedInUser.name }}</router-link>
        </li>
        <li class="nav-item" v-if="isLoggedIn">
          <button @click="handleLogout" class="btn btn-link text-danger">로그아웃</button>
        </li>
        <li class="nav-item" v-else>
          <router-link to="/login" class="nav-link text-dark">로그인</router-link>
          <router-link to="/signup" class="nav-link text-dark">회원가입</router-link>
        </li>
      </ul>
    </div>
  </div>
</nav>
    
    <h2 class="mt-5 pt-5">게시물 작성</h2>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-6" v-if="isLoggedIn">
          <form @submit.prevent="submitForm">
            <div class="mb-3">
              <label for="title" class="form-label">제목:</label>
              <input type="text" id="title" v-model="board.title" class="form-control">
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용:</label>
              <textarea id="content" v-model="board.content" class="form-control"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">작성</button>
          </form>
          <p v-if="successMessage" class="text-success mt-3">{{ successMessage }}</p>
          <p v-if="errorMessage" class="text-danger mt-3">{{ errorMessage }}</p>
        </div>
        <div class="col-md-6" v-else>
          <p class="mt-5 pt-3">게시글을 작성하려면 로그인이 필요합니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      board: {
        title: '',
        content: ''
      },
      successMessage: '',
      errorMessage: ''
    };
  },
  computed: {
    isLoggedIn() {
      const token = localStorage.getItem('token');
      return !!token;
    },
    loggedInUser() {
      const name = localStorage.getItem('name');
      return name ? { name } : null;
    }
  },
  methods: {
    async submitForm() {
      const token = localStorage.getItem('token');
      const email = localStorage.getItem('email'); 
      
      if (!token || !email) { 
        this.errorMessage = '로그인이 필요합니다.';
        return;
      }

      try {
        const createdAt = new Date().toISOString(); // 작성 시간을 설정합니다.
        await axios.post('/api/board/write', {
          email: email,
          title: this.board.title,
          content: this.board.content,
          createdAt: createdAt // 작성 시간 추가
        }, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        this.successMessage = '게시글 작성 성공';
        this.errorMessage = ''; 
        this.$router.push('/');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.error) {
          this.errorMessage = `게시글 작성 실패: ${error.response.data.error}`;
        } else {
          this.errorMessage = '게시글 작성 실패';
        }
        this.successMessage = ''; 
      }
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('name');
    }
  }
};
</script>

<style scoped>
/* 추가적인 스타일링을 원하는대로 작성하세요 */
body {
  background-color: #f8f9fa;
}
</style>
