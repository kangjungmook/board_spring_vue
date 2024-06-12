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
    <div class="container mt-5 pt-5">
      <div class="my-4 p-4 bg-white shadow-sm rounded">
        <table class="table table-bordered">
          <tbody>
            <tr>
              <th class="col-2">제목</th>
              <td colspan="3">{{ board.title }}</td>
            </tr>
            <tr>
              <th class="col-2">내용</th>
              <td colspan="3" class="content-td">{{ board.content }}</td>
            </tr>
            <tr>
              <th class="col-2">등록일</th>
              <td colspan="3">{{ board.created_at }}</td>
            </tr> 
          </tbody>
        </table>
        <div class="text-center mt-4">
          <router-link v-if="isAuthor" :to="{ name: 'BoardModify', params: { id: board.id } }" class="btn btn-primary me-2">수정</router-link>
          <button v-if="isAuthor" @click="deleteBoard" class="btn btn-danger me-2">삭제</button>
          <router-link to="/" class="btn btn-secondary">뒤로</router-link>
        </div>
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
        content: '',
        email: '',
        created_at: '',
        name: '' 
      },
      currentUserEmail: localStorage.getItem('email') 
    };
  },
  computed: {
    isAuthor() {
      return this.board.email === this.currentUserEmail;
    },
    isLoggedIn() {
      return !!this.currentUserEmail;
    },
    loggedInUser() {
      return {
        name: localStorage.getItem('name')
      };
    }
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
      if (confirm("게시물을 삭제하시겠습니까?")) {
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
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('email');
      
      this.currentUserEmail = null;
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
th, td {
  padding: 15px;
}
.content-td {
  height: 200px; /* 원하는 높이로 조정 */
  white-space: pre-wrap; /* 줄바꿈을 유지합니다. */
}
</style>
