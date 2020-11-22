<template>
  <div class="score-panel">
    {{'Deposit: ' + depositSum +'$' }}<span class="vl"></span>   {{'Your bet: ' + betSum + '$'}}
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    name: "Score",
    computed: {
      ...mapGetters('bets', {
        betSum: 'betSum',
        depositSum: 'depositSum'
      }),
    }
  }
</script>

<style scoped>
  .score-panel {
    background-color: #3b4045;
    z-index: 4000;
    position: fixed;
    left: 0;
    bottom: 0;
    right: 0;
    padding: 8px;
  }
  .vl {
    margin-left: 10px;
    margin-right: 10px;
    border-left: 3px solid rgba(0, 0, 0, 0.97);
    height: 15px;
  }
</style>
