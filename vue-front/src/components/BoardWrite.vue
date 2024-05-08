<template>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">       
    <div class="container">
      <h2>게시물 작성</h2>
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
      <router-link to="/" class="btn btn-secondary">목록으로 돌아가기</router-link>
    </div>

  </template>
  
  <script>
  export default {
    data() {
      return {
        board: {
          title: '', //빈 문자열 프로퍼티
          content: '' //빈 문자열 프로퍼티
        }
      };
    },
    methods: {
      submitForm() {
        fetch('/api/board/write', {
          method: 'POST',   
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.board)
        }).then(response => {
          if (response.ok) {
            console.log('성공');
            this.$router.push('/');
          } else {
            console.error('실패');
          }
        })
      }
    }
  };
  </script>
  
  <style scoped>
  </style>
