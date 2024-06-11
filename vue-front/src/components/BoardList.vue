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
            <li class="nav-item d-flex" v-else>
              <router-link to="/login" class="nav-link text-dark">로그인</router-link>
              <router-link to="/signup" class="nav-link text-dark ms-3">회원가입</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <div class="container mt-5 pt-5">
      <div class="row justify-content-center">
        <div class="col-md-10">
          <div class="card">
            <div class="card-body">
              <h2 class="card-title text-center">게시판 목록</h2>
              <table class="table table-striped mt-4">
                <thead>
                  <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>제목</th>
                    <th>등록일자</th>
                    <th>선택</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(board, index) in boards" :key="board.id">
                    <td>{{ index + 1 }}</td>
                    <td>{{ board.name }}</td>
                    <td>{{ board.title }}</td>
                    <td>{{ board.created_at }}</td>
                    <td>
                      <router-link :to="{ name: 'BoardDetail', params: { id: board.id } }" class="btn btn-primary btn-sm">상세보기</router-link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: false,
      loggedInUser: {},
      boards: [] // 게시물 목록 데이터
    };
  },
  mounted() {
    this.checkLoginStatus();
    this.fetchData(); // 게시물 목록 데이터 로드
  },
  methods: {
    checkLoginStatus() {
      const token = localStorage.getItem('token');
      const name = localStorage.getItem('name');
      this.isLoggedIn = !!token && !!name;
      if (this.isLoggedIn) {
        this.loggedInUser.name = name;
      }
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('name');
      this.isLoggedIn = false;
      this.loggedInUser = {};
    },
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
          console.error('Error fetching boards:', error);
        });
    }
  }
};
</script>

<style scoped>
/* 추가적인 스타일링을 원하는대로 작성하세요 */
body {
  background-color: #f8f9fa;
}
.card {
  border-radius: 10px;
}
.btn {
  border-radius: 5px;
}
.cursor-pointer {
  cursor: pointer;
}
</style>
