<template>
  <div>
    <h2>회원가입</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>이메일:</label>
        <input type="email" v-model="email" required />
      </div>
      <div>
        <label>이름:</label>
        <input type="text" v-model="name" required />
      </div>
      <div>
        <label>비밀번호:</label>
        <input type="password" v-model="password" required />
      </div>
      <div>
        <label>비밀번호 확인:</label>
        <input type="password" v-model="confirmPassword" required />
      </div>
      <button type="submit">회원가입</button>
    </form>
    <p v-if="message">{{ message }}</p>
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
        this.message = data.message;
      } catch (error) {
        this.message = '회원가입 실패';
      }
    }
  }
};
</script>
