<template>
  <div>
    <div class="mb-2">
      <span class="text-danger">{{ dealerSumComputed }}</span>
    </div>
    <div>
      <img class="carts" v-for="(cart, index) in game.dealerCards" :src="cart.imageUrl" alt="карта1" :key="index">
      <img class="carts" v-show="game.dealerCards.length === 1" src="/backend/storage/files/Gray_back.jpg" alt="карта1"
           key="CB">
    </div>
    <div class="statusContainer">
      <div v-show="game.gameFinished" class="status text-success text-capitalize"> {{ game.gameStatus }}
      </div>
      <router-link
        v-show="game.gameFinished"
        class="btn btn-info"
        tag="button"
        :to="'/menu'">Start new Game
      </router-link>
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
        addCardToPlayer: 'addCardToPlayer'
      }),
    },
    computed: {
      ...mapGetters('main', {
        gameId: 'gameId',
        game: 'game',
      }),
      moreDisabled() {
        return this.game.gameStatus === 'PLAYER_BJ' || this.game.gameFinished || this.game.playerSum === 21 || this.game.playerAltSum === 21
      },
      doneDisabled() {
        return this.game.gameStatus === 'PLAYER_BJ' || this.doneClicked || this.game.gameFinished || this.game.playerSum === 21 || this.game.playerAltSum === 21;
      },
      playerSumComputed() {
        return this.game.gameStatus === 'PLAYER_BJ' ? 'BJ' : this.game.playerSum !== this.game.playerAltSum ? (this.game.playerSum + " / " + this.game.playerAltSum) : this.game.playerSum;
      },
      dealerSumComputed() {
        return this.game.gameStatus === 'DEALER_BJ' ? 'BJ' : this.game.dealerSum !== this.game.dealerAltSum ? (this.game.dealerSum + " / " + this.game.dealerAltSum) : this.game.dealerSum;
      }
    },
    watch: {
      game(oldVal) {
        if ((this.game.playerSum === 21 || this.game.playerAltSum === 21) && !this.game.gameFinished) {
          this.doneGame();
        }
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
    margin-top: 100px;
    height: 100px;
    display: block;
    justify-content: center;
    align-items: center;
  }

  .status {
    flex: 0 0 120px;
  }
</style>
