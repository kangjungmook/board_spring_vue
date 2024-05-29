<template>
  <div class="container">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">       

    <h2>게시물 작성</h2>
    <form @submit.prevent="submitForm" v-if="isLoggedIn">
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
    <div v-else>
      <p>게시글을 작성하려면 로그인이 필요합니다.</p>
      <router-link to="/login" class="btn btn-primary">로그인</router-link>
    </div>
    <router-link to="/" class="btn btn-secondary mt-3">목록으로 돌아가기</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      board: {
        title: '',
        content: ''
      },
      isLoggedIn: false
    };
  },
  created() {
    this.isLoggedIn = !!localStorage.getItem('token');
  },
  methods: {
    submitForm() {
      const token = localStorage.getItem('token');
      fetch('/api/board/write', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token ? `Bearer ${token}` : ''
        },
        body: JSON.stringify(this.board)
      }).then(response => {
        if (response.ok) {
          console.log('게시글 작성 성공');
          this.$router.push('/');
        } else {
          console.error('게시글 작성 실패');
        }
      })
    }
  }
};

</script>

<style scoped>
</style>
