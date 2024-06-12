<template>
  <div class="container">
    <h2 class="text-center my-4">게시물 수정</h2>
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
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
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boardId: null,
      board: {
        title: '',
        content: ''
      },
      loggedInUserEmail: null // 현재 로그인된 사용자의 이메일을 저장할 변수 추가
    };
  },
  mounted() {
    this.boardId = this.$route.params.id;
    // 로컬 스토리지에서 현재 로그인된 사용자의 이메일 가져오기
    this.loggedInUserEmail = localStorage.getItem('email')?.trim();

    // 서버로부터 게시물 정보 가져오기
    fetch(`/api/board/view/${this.boardId}`)
      .then(response => response.json())
      .then(data => {
        // 현재 게시물의 작성자 이메일 가져오기
        const boardOwnerEmail = data.email?.trim();
        // 접근 권한 확인
        if (this.loggedInUserEmail !== boardOwnerEmail) {
          // 현재 로그인된 사용자와 게시물 작성자가 다른 경우 접근 거부
          alert('해당 게시물을 수정할 수 있는 권한이 없습니다.');
          this.$router.push('/'); // 다른 페이지로 이동
        } else {
          // 게시물 정보 설정
          this.board = data;
        }
      });
  },
  methods: {
    updateBoard() {
      // 수정 로직
      const boardId = this.$route.params.id;
      fetch(`/api/board/modify/${boardId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Logged-In-User-Email': this.loggedInUserEmail
        },
        body: JSON.stringify(this.board)
      })
      .then(response => {
        if (response.ok) {
          this.$router.push('/');
        } else {
          return response.json().then(data => {
            alert(data.error);
          });
        }
      });
    }
  }
};
</script>

<style scoped>
</style>
