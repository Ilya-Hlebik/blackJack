<template>
  <div class="hello">
    <div class="mb-2">
      <span class="text-danger">{{ dealerSumComputed }}</span>
    </div>
    <div>
      <img class="carts" v-for="(cart, index) in game.dealerCards" :src="cart.imageUrl" alt="карта1" :key="index">
    </div>
    <div class="statusContainer">
      <div  v-show="game.gameFinished" class="status text-success text-capitalize"> {{ game.gameStatus }} </div>
    </div>
    <div class="mb-5">
      <img class="carts" v-for="(cart, index) in game.playerCards" :src="cart.imageUrl" alt="карта1" :key="index">
    </div>
    <div>
      <span class="text-danger mr-4">{{ playerSumComputed }}</span>
      <button class="btn btn-info" :disabled="moreDisabled" @click="addCardToPlayer(game.id)">More cards</button>
      <button class="btn btn-danger" :disabled="doneDisabled" @click="doneGame">Done</button>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  export default {
    name: 'BlackJack',
    data() {
      return {
        doneClicked: false
      }
    },
    methods: {
      doneGame() {
        this.doneClicked = true;
        this.dealerTurns(this.game.id);
      },
      ...mapActions('main', {
        dealerTurns: 'dealerTurns',
        addCardToPlayer : 'addCardToPlayer'
      })
    },
    computed: {
      ...mapGetters('main', {
        game: 'game',
      }),
      moreDisabled() {
        return this.game.gameStatus === 'PLAYER_BJ' || this.game.gameFinished
      },
      doneDisabled() {
        return this.game.gameStatus === 'PLAYER_BJ' || this.doneClicked || this.game.gameFinished;
      },
      playerSumComputed() {
        return this.game.gameStatus === 'PLAYER_BJ' ? 'BJ' : this.game.playerSum;
      },
      dealerSumComputed() {
        return this.game.gameStatus === 'DEALER_BJ' ? 'BJ' : this.game.dealerSum;
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h3 {
    margin: 40px 0 0;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }

  .dealer {
    margin-bottom: 20rem;
  }

  .carts {
    max-width: 6%;
  }
  .statusContainer {
    height: 260px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .status {
    flex: 0 0 120px;
  }
</style>
