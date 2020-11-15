<template>
  <div>
    <div class="mb-2">
      <span class="score">{{ dealerSumComputed }}</span>
    </div>
    <div>
      <transition-group   name="custom-classes-transition"
                              enter-active-class="animated zoomIn"
                              leave-active-class="animated zoomOut"mode="out-in">
        <img class="carts" v-for="(cart, index) in game.dealerCards" :src="cart.imageUrl" alt="карта1" :key="index">
      </transition-group >
      <img class="carts" v-show="game.dealerCards.length === 1" src="/backend/storage/files/Gray_back.jpg" alt="карта1"
           key="CB">

    </div>
    <div class="middle-of-field">
      <div class="statusContainer">
        <div v-show="game.gameFinished" class="status"> {{ game.gameStatus }}
        </div>
      </div>
      <router-link
        v-show="game.gameFinished"
        class="button new-game-button"
        tag="button"
        :to="'/menu'"><span>Start new Game</span>
      </router-link>
    </div>
    <div class="mb-5">
      <img class="carts" v-for="(cart, index) in game.playerCards" :src="cart.imageUrl" alt="карта1" :key="index">
    </div>
    <div>
      <span class="score mr-4">{{ playerSumComputed }}</span>
      <button class="button mr-1" :disabled="moreDisabled" @click="addCardToPlayer(game.id)"><span>Hit</span>
      </button>
      <button class="button" :disabled="doneDisabled" @click="doneGame"><span>Stand</span></button>
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
        return this.game.gameStatus === 'PLAYER_BJ'|| this.doneClicked  || this.game.gameFinished || this.game.playerSum === 21 || this.game.playerAltSum === 21
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
<style scoped lang="scss">
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

  .carts {
    max-width: 6%;
    border-radius: 7px;
    overflow: hidden;
    margin: -10px 0 0 10px;
  }

  .statusContainer {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .middle-of-field{
    margin-top: 4%;
    height: 135px;
    margin-bottom: 2%;
  }

  .status {
    flex: 0 0 120px;
    margin-bottom: 2%;
    text-align: center;
    text-shadow: -1px -1px 0px rgba(255, 255, 255, 0.3), 1px 1px 0px rgba(0, 0, 0, 0.8);
    color: #333;
    opacity: 0.4;
    font: 700 40px 'Bitter';
  }

  .score {
    font-size: 32px;
    font-family: "Open Sans", serif;
    color: aqua;
  }

  @import url("https://fonts.googleapis.com/css?family=Montserrat&display=swap");

  .button {
    width: 100px;
    height: 40px;
    background: #f3f0f1;
    position: relative;
    border-radius: 32px;
    text-align: center;
    cursor: pointer;
    transition: all 0.1s ease-in-out;

    span {
      line-height: 30px;
      font-family: "Montserrat", sans-serif;
      font-size: 28px;
      font-weight: bolder;
    }

    & {
      box-shadow: -6px -6px 10px rgba(255, 255, 255, 0.8),
      6px 6px 10px rgba(0, 0, 0, 0.2);
      color: #6f6cde;

      &:active {
        opacity: 1;
        box-shadow: inset -4px -4px 8px rgba(255, 255, 255, 0.5),
        inset 8px 8px 16px rgba(0, 0, 0, 0.1);
        color: #79e3b6;
      }
      &:disabled {
        opacity: 0.5;
      }
    }
  }

  .new-game-button {
    width: 200px;
    height: 50px;
    span {
      line-height: 40px;
      font-family: "Montserrat", sans-serif;
      font-size: 20px;
      font-weight: bolder;
    }
  }

</style>
