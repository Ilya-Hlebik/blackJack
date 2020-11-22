<template>
  <div>
    <div class="mb-2 mt--3">
      <span class="score">{{ dealerSumComputed }}</span>
    </div>
    <div>
      <transition-group   name="custom-classes-transition"
                              enter-active-class="animate__animated animate__slideInRight animate__faster"
                              leave-active-class="animate__animated animate__flipInY " mode="out-in">
        <img class="carts" v-for="(cart, index) in game.dealerCards" :src="cart.imageUrl" alt="карта1" :key="index">

      </transition-group >
     <transition  name="custom-classes-transition2" leave-active-class="animate__animated animate__flipOutY  animate__faster" mode="out-in">
        <img class="carts" v-show="game.dealerCards.length === 1" src="/backend/storage/files/Gray_back.jpg" alt="карта1"
             key="CB">
      </transition>
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
    <div class="score">{{ playerSumComputed }}</div>
    <div class="statusContainer">
      <div class="player-carts">
        <transition-group name="custom-classes-transition"
                          enter-active-class="animate__animated animate__fadeInTopRight animate__faster"
                          leave-active-class="animated zoomOut" mode="out-in">
          <img class="carts" v-for="(cart, index) in game.playerCards" :src="cart.imageUrl" alt="карта1" :key="index">
        </transition-group>
      </div>
      <div class="ml-60">
        <button class="button" :disabled="moreDisabled" @click="addCardToPlayer(game.id)"><span>Hit</span>
        </button>
        <button class="button" :disabled="doneDisabled" @click="doneGame"><span>Stand</span></button>
        <div>
          <bets></bets>
        </div>
      </div>
    </div>
    <score></score>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  import Bets from '../components/Bets';
  import Score from '../components/Score';

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
      game(newVal,oldVal) {
        if ((oldVal.playerSum !==this.game.playerSum || oldVal.playerAltSum !==this.game.playerAltSum)&& (this.game.playerSum === 21 || this.game.playerAltSum === 21) && !this.game.gameFinished) {
          this.doneGame();
        }
      }
    },
    components: {
      Bets, Score
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import '../../node_modules/animate.css/animate.css';
  a {
    color: #42b983;
  }

  .carts {
    max-height: 125px;
    border-radius: 7px;
    overflow: hidden;
    margin: -10px 0 0 -40px;
  }

  .statusContainer {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  @media screen and (max-width: 1300px) {
    .middle-of-field {
      margin-top: 1%;
      height: 135px;
      margin-bottom: 1%;
    }
  }

  @media screen and(min-width: 1300px) and (max-width: 2000px) {
    .middle-of-field {
      margin-top: 2%;
      height: 250px;
      margin-bottom: 2%;
    }
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

  .mt--3 {
    margin-top: -3%;
  }

  .ml-60 {
    margin-left: 60%;
  }

  .player-carts {
    position: fixed;
    padding-bottom: 50px;
  }
</style>
