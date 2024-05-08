<template>
  <div class="home">
    <BoardList msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import BoardList from '../components/BoardList.vue'

export default {
  name: 'HomeView',
  components: {
    BoardList
  }
}
</script>
