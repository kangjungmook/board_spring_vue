<template>
    <div>
      <div>
        <div>
          <div>회원가입</div>
          <div>
            <form @submit.prevent="registerUser">
              <div>
                <label for="username">아이디</label>
                <input type="text" id="username" v-model="user.userId" required>
              </div>
              <div>
                <label for="password">비밀번호</label>
                <input type="password" id="password" v-model="user.userPw" required>
              </div>
              <div> 
                <label for="name">이름</label>
                <input type="text" id="name" v-model="user.userName" required>
              </div>
              <button type="submit">가입하기</button>
            </form>
            <router-link to="/">목록으로 돌아가기</router-link>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        user: {
        userId: '',
        userPw: '',
        userName: ''
        }
      };
    },
    methods: {
      registerUser() {
        fetch('/api/board/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.user)
        })
        .then(response => {
          if (response.ok) {
            alert('회원가입이 완료되었습니다.');
          } else {
            throw new Error('회원가입에 실패했습니다.');
          }
        })
        .catch(error => {
          console.error(error);
          alert('회원가입에 실패했습니다.');
        });
      }
    }
  };
  </script>
  
  <style scoped>
  
  </style>
