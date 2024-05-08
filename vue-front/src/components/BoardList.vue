<template>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card my-4">
          <div class="card-body">
            <h2 class="card-title text-center mb-4">게시판 목록</h2>
            <div class="list-group">
              <div v-for="board in boards" :key="board.id" class="list-group-item list-group-item-action">
                <div class="d-flex justify-content-between align-items-center">
                  <h5 class="mb-0">{{ board.title }}</h5>
                  <router-link :to="{ name: 'BoardDetail', params: { id: board.id } }" class="btn btn-primary">자세히 보기</router-link>
                </div>
                <p class="mb-1">{{ board.content }}</p>
              </div>
            </div>
          </div>
        </div>
        <router-link to="/write" class="btn btn-primary w-100 mt-4">게시물 작성</router-link>
        <router-link to="/login" clas  s="btn btn-primary w-100 mt-4" >로그인</router-link>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boards: []
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      fetch('/api/board/list')
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('게시물을 불러오는 데 실패했습니다.');
          }
        })
        .then(data => {
          this.boards = data;
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>

<style scoped>
</style>
