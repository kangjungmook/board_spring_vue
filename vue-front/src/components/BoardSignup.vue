<template>
  <div class="container">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">  

    <div class="row justify-content-center mt-5">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h2 class="card-title text-center">회원가입</h2>
            <form @submit.prevent="handleSubmit">
              <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" class="form-control" id="email" v-model="email" required />
              </div>
              <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" class="form-control" id="name" v-model="name" required />
              </div>
              <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" class="form-control" id="password" v-model="password" required />
              </div>
              <div class="form-group">
                <label for="confirmPassword">비밀번호 확인:</label>
                <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword" required />
              </div>
              <button type="submit" class="btn btn-primary btn-block">회원가입</button>
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
      name: '',
      password: '',
      confirmPassword: '',
      message: ''
    };
  },
  methods: {
async handleSubmit() {
  try {
    const response = await fetch('/api/board/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: this.email,
        name: this.name,
        password: this.password,
        confirmPassword: this.confirmPassword
      })
    });
    const data = await response.json();
    if (response.ok) {
      this.message = '회원가입 성공';
      this.$router.push('/');
    } else {
      this.message = data.message;
    }
  } catch (error) {
    this.message = '회원가입 실패';
  }
}

  }
};
</script>

<style scoped>
</style>
