<template>
  <div class="bets-container">
    <div class="chips">
      <div class="bets">
        <img class="bet" @click="addChip(image.value)" v-for="image in firstImageRow"
             :src="require(`@/assets/images/${image.name}.png`)" :title="image.title" :key="image.name">
      </div>
      <div class="bets">
        <img class="bet" @click="addChip(image.value)" v-for="image in secondImageRow"
             :src="require(`@/assets/images/${image.name}.png`)" :title="image.title" :key="image.name">
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions} from "vuex";

  export default {
    name: "Bets",
    data() {
      return {
        firstImageRow: [{
          name: "25c",
          title: "25c",
          value: 0.25
        }, {
          name: "50c",
          title: "50c",
          value: 0.50
        }, {
          name: "1",
          title: "1$",
          value: 1
        }, {
          name: "5",
          title: "5$",
          value: 5
        }],
        secondImageRow: [{
          name: "10",
          title: "10$",
          value: 10
        }, {
          name: "25",
          title: "25$",
          value: 25
        }, {
          name: "50",
          title: "50$",
          value: 50
        }, {
          name: "100",
          title: "100$",
          value: 100
        }],
        betSum: 0
      }
    },
    methods: {
      ...mapActions('bets', {
        calculateSum: 'calculateSum',
      }),
      addChip(value){
        new Audio(require('@/assets/sounds/chips.wav')).play();
        this.calculateSum(value);
      }
    }
  }
</script>

<style scoped>
  .bet {
    user-select: none;
    max-width: 70px;
    margin: 2px;
    cursor: copy;
  }

  .bets, .bets-container{
    display: flex;
  }

  .bet-sum{
    position:relative;
    right:100px;
    top:2px;
  }

  .chips{
  }
</style>
