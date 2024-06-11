<template>
  <div>
    <h2>게시물 작성</h2>
    <div v-if="isLoggedIn">
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
      <p v-if="successMessage" class="text-success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="text-danger">{{ errorMessage }}</p>
    </div>
    <div v-else>
      <p>게시글을 작성하려면 로그인이 필요합니다.</p>
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
    }
  }
};
</script>
