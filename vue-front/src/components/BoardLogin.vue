<template>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <div class="col-md-6">
        <div class="card shadow-lg">
          <div class="card-body">
            <h2 class="card-title text-center mb-4">로그인</h2>
            <form @submit.prevent="handleSubmit">
              <div class="form-group mb-3">
                <label for="email" class="form-label">이메일:</label>
                <input
                  type="email"
                  class="form-control"
                  id="email"
                  v-model="email"
                  required
                  placeholder="이메일을 입력하세요"
                />
              </div>
              <div class="form-group mb-4">
                <label for="password" class="form-label">비밀번호:</label>
                <input
                  type="password"
                  class="form-control"
                  id="password"
                  v-model="password"
                  required
                  placeholder="비밀번호를 입력하세요"
                />
              </div>
              <button type="submit" class="btn btn-primary btn-block w-100">
                로그인
              </button>
            </form>
            <p v-if="message" class="mt-3 text-center text-danger">{{ message }}</p>
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
      email: "", // 사용자 입력 이메일
      password: "", // 사용자 입력 비밀번호
      message: "", // 로그인 성공 실패 메시지
    };
  },
  methods: {
    async handleSubmit() {
      try {
        // 로그인 API 호출
        const response = await fetch("/api/board/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password,
          }),
        });

        if (response.ok) {
          // 성공 시 토큰 및 사용자 정보 저장
          const data = await response.json();
          const token = data.token;
          const userName = data.name;
          const userEmail = this.email;

          localStorage.setItem("token", token);
          localStorage.setItem("name", userName);
          localStorage.setItem("email", userEmail);

          this.message = "로그인 성공";
          this.$emit("loginSuccess", token);
          this.$router.push("/"); // 로그인 성공 후 홈으로 이동
        } else {
          this.message = "로그인 실패";
        }
      } catch (error) {
        this.message = "로그인 실패";
      }
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 100px;
}

.card {
  border-radius: 15px;
  padding: 20px;
  background-color: #ffffff;
}

.card-title {
  font-weight: bold;
}

.btn {
  border-radius: 30px;
}

input {
  border-radius: 10px;
  padding: 10px;
  border: 1px solid #ced4da;
}

input:focus {
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
}
</style>
