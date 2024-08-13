<template>
  <div class="home">
    <BoardList msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
import BoardList from '../components/BoardList.vue'

export default {
  name: 'HomeView',
  components: {
    BoardList
  }
}
</script>
