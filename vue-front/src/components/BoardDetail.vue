<template>
  <div class="container my-4">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <div class="card">
      <div class="card-header">
        <h2 class="card-title">{{ board.title }}</h2>
      </div>
      <div class="card-body">
        <p class="card-text">{{ board.content }}</p>
      </div>
      <div class="card-footer">
        <button @click="deleteBoard" class="btn btn-danger me-2">삭제</button>
        <router-link :to="{ name: 'BoardModify', params: { id: board.id } }" class="btn btn-primary me-2">
          수정
        </router-link>
        <router-link to="/" class="btn btn-secondary">목록으로 돌아가기</router-link>
      </div>
    </div>
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
        .then(response => response.json())
        .then(data => {
          this.board = data;
        });
    },
    deleteBoard() {
      if (confirm("게시물을 삭제하시겠습니까?"))  {
        const boardId = this.$route.params.id;
        fetch(`/api/board/delete/${boardId}`, {
          method: 'DELETE'
        })
        .then(response => {
          if (response.ok) {
            this.$router.push('/');
          }
        });
      }
    }
  }
};
</script>

<style scoped>
.card {
  width: 80%;
  margin: 0 auto;
}
</style>
