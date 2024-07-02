<template>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <div class="col-md-6">
        <div class="card shadow-lg">
          <div class="card-body">
            <h2 class="card-title text-center mb-4">회원가입</h2>
            <form @submit.prevent="handleSubmit">
              <div class="mb-3">
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
              <div class="mb-3">
                <label for="name" class="form-label">이름:</label>
                <input 
                  type="text" 
                  class="form-control" 
                  id="name" 
                  v-model="name" 
                  required
                  placeholder="이름을 입력하세요"
                />
              </div>
              <div class="mb-3">
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
              <div class="mb-4">
                <label for="confirmPassword" class="form-label">비밀번호 확인:</label>
                <input 
                  type="password" 
                  class="form-control" 
                  id="confirmPassword" 
                  v-model="confirmPassword" 
                  required
                  placeholder="비밀번호를 다시 입력하세요"
                />
              </div>
              <button type="submit" class="btn btn-primary btn-block w-100">회원가입</button>
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
      email: '',
      name: '',
      password: '',
      confirmPassword: '',
      message: ''
    };
  },
  methods: {
    async handleSubmit() {
      if (this.password !== this.confirmPassword) {
        this.message = '비밀번호가 일치하지 않습니다.';
        return;
      }
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
