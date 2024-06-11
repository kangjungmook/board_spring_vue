<template>
  <div class="container">
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
          const data = await response.json();
          const token = data.token;
          const userName = data.name; // name을 받아옴
          const userEmail = this.email; 

          localStorage.setItem('token', token);
          localStorage.setItem('name', userName); 
          localStorage.setItem('email', userEmail);

          this.message = '로그인 성공';
          this.$emit('loginSuccess', token);
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
/* 스타일 정의 */
</style>
