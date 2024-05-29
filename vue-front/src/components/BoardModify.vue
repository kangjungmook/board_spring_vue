<template>
  <div class="container">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <h2>게시물 수정</h2>
    <form @submit.prevent="updateBoard">
      <div class="mb-3">
        <label for="title" class="form-label">제목:</label>
        <input type="text" id="title" v-model="board.title" class="form-control">
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용:</label>
        <textarea id="content" v-model="board.content" class="form-control"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">수정 완료</button>
      
    </form>
    
  </div>
</template>

<script>
export default {
  data() {
    return {
      board: {
        title: '',
        content: ''
      }
    };
  },
  mounted() {
    this.fetchBoardDetail();
  },
  methods: {
    fetchBoardDetail() {
      const boardId = this.$route.params.id;
      fetch(`/api/board/view/${boardId}`)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.board = data;
        })
    },
    updateBoard() {
      const boardId = this.$route.params.id;
      fetch(`/api/board/modify/${boardId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.board)
      })
      .then(response => {
        if (response.ok) {
          this.$router.push('/');
        } 
      })
    }
  }
};
</script>

<style scoped>
</style>
