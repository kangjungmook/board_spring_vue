<template>
  <div>
    <h2>로그인</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>이메일:</label>
        <input type="email" v-model="email" required />
      </div>
      <div>
        <label>비밀번호:</label>
        <input type="password" v-model="password" required />
      </div>
      <button type="submit">로그인</button>
    </form>
    <p v-if="message">{{ message }}</p>
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
        if (response.status === 200) {
          this.message = '로그인 성공';
          const token = response.headers.get('Authorization');
          // 옵션: 토큰 저장
          localStorage.setItem('token', token);
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
