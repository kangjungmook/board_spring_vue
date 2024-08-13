<template>
  <div class="container">
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
    <h2 class="text-center my-4">게시물 수정</h2>
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <form @submit.prevent="updateBoard">
              <div class="mb-3">
                <label for="title" class="form-label">제목:</label>
                <input
                  type="text"
                  id="title"
                  v-model="board.title"
                  class="form-control"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="content" class="form-label">내용:</label>
                <textarea
                  id="content"
                  v-model="board.content"
                  class="form-control"
                  rows="5"
                  required
                ></textarea>
              </div>
              <button type="submit" class="btn btn-primary w-100">수정 완료</button>
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
        title: "",
        content: "",
      },
      loggedInUserEmail: null,
    };
  },
  mounted() {
    this.boardId = this.$route.params.id;
    this.loggedInUserEmail = localStorage.getItem("email")?.trim();

    fetch(`/api/board/view/${this.boardId}`)
      .then((response) => response.json())
      .then((data) => {
        const boardOwnerEmail = data.email?.trim();
        if (this.loggedInUserEmail !== boardOwnerEmail) {
          alert("해당 게시물을 수정할 수 있는 권한이 없습니다.");
          this.$router.push("/");
        } else {
          this.board = data;
        }
      });
  },  
  methods: {
    updateBoard() {
      const boardId = this.$route.params.id;
      fetch(`/api/board/modify/${boardId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Logged-In-User-Email": this.loggedInUserEmail,
        },
        body: JSON.stringify(this.board),
      }).then((response) => {
        if (response.ok) {
          this.$router.push("/");
        } else {
          return response.json().then((data) => {
            alert(data.error);
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 60px;
}
.card {
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.card-body {
  padding: 20px;
}
.btn {
  border-radius: 5px;
}
</style>
