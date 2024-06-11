<template>
  <div class="container">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">       
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">       
    <div class="row justify-content-center">
      <div class="col-md-10">
        <div class="card my-4">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-4">
              <h2 class="card-title text-center mb-0">게시판 목록</h2>
              <div v-if="isLoggedIn" @click="handleNameClick" class="cursor-pointer">{{ loggedInUser.name }} 님</div>
              <router-link v-else to="/login" class="btn btn-info">로그인</router-link>
            </div>
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>번호</th>
                  <th>이름</th>
                  <th>템플릿명</th>
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
        <router-link v-if="isLoggedIn" to="/write" class="btn btn-success">게시물 작성</router-link>
        <button v-if="isLoggedIn" @click="handleLogout" class="btn btn-danger">로그아웃</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boards: [],
      isLoggedIn: false,
      loggedInUser: {}
    };
  },
  mounted() {
    this.fetchData();
    this.checkLoginStatus();
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
          this.boards = data.map((board, index) => ({
            ...board,
            index: index + 1,
            templateName: board.templateName || 'N/A',
            registrationDate: board.registrationDate || 'N/A'
          }));
        })
        .catch(error => {
          console.error('Error fetching boards:', error);
        });
    },
    checkLoginStatus() {
      const token = localStorage.getItem('token');
      const email = localStorage.getItem('email');
      const name = localStorage.getItem('name');
      this.isLoggedIn = !!token && !!email;
      if (this.isLoggedIn) {
        this.loggedInUser = { email, name };
      }
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('email');
      localStorage.removeItem('name');
      this.isLoggedIn = false;
      this.loggedInUser = {};
    },
    handleNameClick() {
      this.handleLogout();
    }
  }
};
</script>

<style scoped>
/* 추가적인 스타일링을 원하는대로 작성하세요 */
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
