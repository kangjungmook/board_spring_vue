<template>
  <div class="container">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <div class="row justify-content-center mt-5">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h2 class="card-title text-center">로그인</h2>
            <form @submit.prevent="handleSubmit">
              <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" class="form-control" id="email" v-model="email" required />
              </div>
              <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" class="form-control" id="password" v-model="password" required />
              </div>
              <button type="submit" class="btn btn-primary btn-block">로그인</button>
            </form>
            <p v-if="message" class="mt-3 text-center">{{ message }}</p>
          </div>
        <router-link to="/signup" class="btn btn-secondary">회원가입 </router-link>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      message: ''
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await fetch('/api/board/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        });

        if (response.ok) {
          this.message = '로그인 성공';
          const token = await response.text(); 
          localStorage.setItem('token', token);
          // 로그인 성공 시 이벤트 발생
          this.$emit('loginSuccess', token);
          // 페이지 이동
          this.$router.push('/');
        } else {
          this.message = '로그인 실패';
        }
      } catch (error) {
        this.message = '로그인 실패';
      }
    }
  }
};
</script>

<style scoped>

</style>
