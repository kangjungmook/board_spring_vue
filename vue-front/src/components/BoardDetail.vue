<template>
  <div>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand">게시판</router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
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
              <router-link to="#" class="nav-link text-primary">{{
                loggedInUser.name
              }}</router-link>
            </li>
            <li class="nav-item" v-if="isLoggedIn">
              <button @click="handleLogout" class="btn btn-link text-danger">
                로그아웃
              </button>
            </li>
            <li class="nav-item d-flex" v-else>
              <router-link to="/login" class="nav-link text-dark">로그인</router-link>
              <router-link to="/signup" class="nav-link text-dark ms-3"
                >회원가입</router-link
              >
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
          <router-link
            v-if="isAuthor"
            :to="{ name: 'BoardModify', params: { id: board.id } }"
            class="btn btn-primary me-2"
            >수정</router-link
          >
          <button v-if="isAuthor" @click="deleteBoard" class="btn btn-danger me-2">
            삭제
          </button>
          <router-link to="/" class="btn btn-secondary">뒤로</router-link>
        </div>

        <!-- 댓글 -->
        <div class="mt-5">
          <h5>댓글</h5>
          <ul class="list-group">
            <li class="list-group-item" v-for="comment in comments" :key="comment.id">
              <strong>{{ comment.email }}</strong> {{ comment.createdAt }}
              <p>{{ comment.content }}</p>
              <button
                v-if="isCommentAuthor(comment.email)"
                @click="deleteComment(comment.id)"
                class="btn btn-link text-danger btn-sm"
              >
                삭제
              </button>
            </li>
          </ul>
          <div v-if="isLoggedIn" class="mt-3">
            <textarea
              v-model="newCommentContent"
              class="form-control mb-2"
              rows="3"
              placeholder="댓글을 입력하세요"
            ></textarea>
            <button @click="addComment" class="btn btn-primary">댓글 작성</button>
          </div>
          <div v-else>
            <p>댓글을 작성하려면 <router-link to="/login">로그인</router-link>하세요.</p>
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
      board: {
        title: "",
        content: "",
        email: "",
        created_at: "",
        name: "",
      },
      currentUserEmail: localStorage.getItem("email"),
      comments: [],
      newCommentContent: "",
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
        name: localStorage.getItem("name"),
      };
    },
  },
  mounted() {
    this.fetchBoardDetail();
    this.fetchComments();
  },
  methods: {
    fetchBoardDetail() {
      const boardId = this.$route.params.id;
      fetch(`/api/board/view/${boardId}`)
        .then((response) => response.json())
        .then((data) => {
          this.board = data;
        });
    },
    fetchComments() {
      const boardId = this.$route.params.id;
      fetch(`/api/comments/board/${boardId}`)
        .then((response) => response.json())
        .then((data) => {
          this.comments = data;
        });
    },
    addComment() {
      if (this.newCommentContent.trim() === "") {
        alert("댓글 내용을 입력하세요.");
        return;
      }

      const boardId = this.$route.params.id;
      const comment = {
        boardId,
        email: this.currentUserEmail,
        content: this.newCommentContent,
      };

      fetch("/api/comments/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(comment),
      }).then((response) => {
        if (response.ok) {
          this.fetchComments();
          this.newCommentContent = "";
        }
      });
    },
    deleteComment(commentId) {
      if (confirm("댓글을 삭제하시겠습니까?")) {
        fetch(`/api/comments/delete/${commentId}`, {
          method: "DELETE",
        }).then((response) => {
          if (response.ok) {
            this.fetchComments();
          }
        });
      }
    },
    isCommentAuthor(email) {
      return email === this.currentUserEmail;
    },
    deleteBoard() {
      if (confirm("게시물을 삭제하시겠습니까?")) {
        const boardId = this.$route.params.id;
        fetch(`/api/board/delete/${boardId}`, {
          method: "DELETE",
        }).then((response) => {
          if (response.ok) {
            this.$router.push("/");
          }
        });
      }
    },
    handleLogout() {
      localStorage.removeItem("token");
      localStorage.removeItem("email");
      this.currentUserEmail = null;
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
th,
td {
  padding: 15px;
}
.content-td {
  height: 200px;
  white-space: pre-wrap;
}
</style>
