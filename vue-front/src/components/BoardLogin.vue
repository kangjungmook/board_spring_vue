<template>
  <div>
    <div>
      <h2>로그인</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="w3-input" name="uid" placeholder="ID" v-model="userId"><br>
          </p>
          <p>
            <input name="password" class="w3-input" placeholder="password" v-model="userPw" type="password">
          </p>
          <p>
            <button type="submit" class="w3-button w3-green w3-round">Login</button>
          </p>
        </form>
        <router-link to="signup">회원가입</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId: '',
      userPw: ''
    }
  },
  methods: {
    async fnLogin() {
      if (this.userId === '') {
        alert('ID를 입력하세요.')
        return
      }

      if (this.userPw === '') {
        alert('비밀번호를 입력하세요.')
        return
      }

      try {
        const response = await fetch('/api/board/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            userId: this.userId,
            userPw: this.userPw
          })
        });
        
        if (response.ok) {
          alert('로그인 되었습니다.');
          // 로그인에 성공한 경우, 홈페이지로 리다이렉트할 수 있습니다.
          // this.$router.push('/home');
        } else {
          alert('로그인 실패. 아이디와 비밀번호를 확인하세요.');
        }
      } catch (error) {
        console.error('로그인 중 오류가 발생했습니다:', error);
        alert('로그인 중 오류가 발생했습니다.');
      }
    }
  }
}
</script>

<style>
#loginForm {
  width: 500px;
  margin: auto;
}
</style>
