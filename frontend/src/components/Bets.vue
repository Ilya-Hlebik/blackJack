<template>
  <div>
    <error-notification
      :class="notificationClass"
      @closeNotification="showErrorNotification(false)"
      warningTitle="Error."
      warningDescription="You can't place more than deposit. Placed full deposit"></error-notification>
      <div class="bet-container">
        <div class="bets">
          <img class="chips" @click="addChip(image.value)" v-for="image in firstImageRow"
               :src="require(`@/assets/images/${image.name}.png`)" :title="image.title" :key="image.name">
        </div>
        <div class="bets">
          <img class="chips" @click="addChip(image.value)" v-for="image in secondImageRow"
               :src="require(`@/assets/images/${image.name}.png`)" :title="image.title" :key="image.name">
        </div>
      </div>
    </div>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";
  import ErrorNotification from './ErrorNotification';

  export default {
    name: "Bets",
    components: {ErrorNotification},
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
        betSum: 0,
        notificationClass:"visibility-hidden"
      }
    },
    computed: {
      ...mapGetters('login', {
        depositSum: 'depositSum'
      }),

    },
    methods: {
      ...mapActions('bets', {
        calculateSum: 'calculateSum',
      }),
      addChip(value) {
        new Audio(require('@/assets/sounds/chips.wav')).play();
        if (this.depositSum - value < 0) {
          this.calculateSum(this.depositSum);
          this.showErrorNotification(true);
        } else {
          this.calculateSum(value);
        }
      },
      showErrorNotification(data) {
        this.notificationClass = data ? "visibility-visible" : "visibility-hidden";
      }
    }
  }
</script>

<style lang="scss" scoped>

  .bets {
    display: flex;
  }

  .bet-sum {
    position: relative;
    right: 100px;
    top: 2px;
  }

  .chips {
    user-select: none;
    max-width: 70px;
    margin: 2px;
    font-size: 24px;
    text-align: center;
    cursor: pointer;
    outline: none;
    border: none;
    border-radius: 15px;
  }

  .chips:active {
    transform: translateY(4px);
  }
  .bet-container{
    position: relative;
    left: 70px;
  }
</style>
