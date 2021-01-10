<template>
  <div>
    <div class="mb-2 mt--3" v-if="!finishBetsShowed">
      <span class="score">{{ dealerSumComputed }}</span>
    </div>
    <div>
      <transition-group name="custom-classes-transition"
                        enter-active-class="animate__animated animate__slideInRight animate__faster"
                        leave-active-class="animate__animated animate__flipInY " mode="out-in">
        <img class="carts" v-for="(cart, index) in game.dealerCards" :src="cart.imageUrl" alt="карта1" :key="index">

      </transition-group>
      <transition name="custom-classes-transition2"
                  leave-active-class="animate__animated animate__flipOutY  animate__faster" mode="out-in">
        <img class="carts" v-show="game.dealerCards.length === 1" src="/backend/storage/files/Gray_back.jpg"
             alt="карта1"
             key="CB">
      </transition>
    </div>
    <div class="middle-of-field">
      <div class="statusContainer">
        <div v-show="game.gameFinished" class="status"> {{ game.gameStatus }}
        </div>
      </div>
      <div @click="startNewGameClick">
        <router-link
          v-show="game.gameFinished"
          class="button new-game-button"
          tag="button"
          :to="'/game'"><span>Start new Game</span>
        </router-link>
      </div>
    </div>
    <div class="statusContainer">
      <div class="player-carts">
        <div class="mb-2" v-if="!finishBetsShowed">
          <div class="score">{{ playerSumComputed }}</div>
        </div>
        <transition-group name="custom-classes-transition"
                          enter-active-class="animate__animated animate__fadeInTopRight animate__faster"
                          leave-active-class="animated zoomOut" mode="out-in">
          <img class="carts" v-for="(cart, index) in game.playerCards" :src="cart.imageUrl" alt="карта1" :key="index">
        </transition-group>
      </div>
      <div class="ml-60">
        <button class="button new-game-button bet-buttons btn-danger" :disabled="betSum === 0" v-show="finishBetsShowed" @click="clearBets"><span>Clear Bet</span></button>
        <button class="button new-game-button bet-buttons" :disabled="betSum === 0" v-show="finishBetsShowed" @click="finishBets"><span>Finish Bets</span></button>
        <div v-show="!finishBetsShowed" class="game-buttons">
          <button class="button" :disabled="moreDisabled" @click="hit(game.id)"><span>Hit</span></button>
          <button class="button" :disabled="doneDisabled" @click="doneGame"><span>Stand</span></button>
        </div>
        <div>
          <bets v-if="finishBetsShowed"></bets>
        </div>
      </div>
    </div>
    <score></score>
  </div>
</template>

<script>
  import {mapActions, mapGetters, mapMutations} from 'vuex';
  import Bets from '../components/Bets';
  import Score from '../components/Score';

  export default {
    name: 'BlackJack',
    data() {
      return {
        doneClicked: false,
      }
    },
    methods: {
      doneGame() {
        this.doneClicked = true;
        new Audio(require('@/assets/sounds/stand.mp3')).play();
        this.dealerTurns(this.game.id);
      },
      hit(gameId) {
        new Audio(require('@/assets/sounds/hit.wav')).play();
        this.addCardToPlayer(gameId);
      },
      finishBets() {
        new Audio(require('@/assets/sounds/click_main.wav')).play();
        let game = this.loadNewGame({gameId:this.gameId, betSum: this.betSum});
        if (game.gameStatus === 'PLAYER_BJ') {
          this.dealerTurns(this.gameId);
        }
      },
      clearBets() {
        new Audio(require('@/assets/sounds/chips.wav')).play();
        this.clearBetsAction();
      },
      ...mapActions('main', {
        dealerTurns: 'dealerTurns',
        addCardToPlayer: 'addCardToPlayer',
        loadExistingGame: 'loadExistingGame',
        loadNewGame: 'loadNewGame',
      }),
      ...mapActions('bets', {
        clearBetsAction: 'clearBets',
      }),
      ...mapMutations('main', {
        clearDealerCards: 'clearDealerCards',
        setStartNewGameClicked: 'setStartNewGameClicked'
      }),
      ...mapMutations('bets', {
        setBetSum: 'setBetSum',
      }),
      loadBoard() {
        this.setBetSum(0);
        let game = this.loadExistingGame(this.gameId);
        if (game !== null) {
          if (!game.gameFinished) {
            if (this.startNewGameClicked) {
              this.clearDealerCards();
              this.setStartNewGameClicked(false);
            }
          }
        }
      },
      startNewGameClick() {
        this.setStartNewGameClicked(true);
      }
    },
    computed: {
      ...mapGetters('main', {
        gameId: 'gameId',
        game: 'game',
        startNewGameClicked: 'startNewGameClicked'
      }),
      ...mapGetters('bets', {
        betSum: 'betSum',
      }),
      finishBetsShowed(){
        return !this.game.gameLoaded;
      },
      moreDisabled() {
        return this.finishBetsShowed || this.game.gameStatus === 'PLAYER_BJ' || this.doneClicked || this.game.gameFinished || this.game.playerSum === 21 || this.game.playerAltSum === 21
      },
      doneDisabled() {
        return this.finishBetsShowed || this.game.gameStatus === 'PLAYER_BJ' || this.doneClicked || this.game.gameFinished || this.game.playerSum === 21 || this.game.playerAltSum === 21;
      },
      playerSumComputed() {
        return this.game.gameStatus === 'PLAYER_BJ' ? 'BJ' : this.game.playerSum !== this.game.playerAltSum ? this.game.playerSum > 21 ? this.game.playerAltSum : (this.game.playerSum + " / " + this.game.playerAltSum) : this.game.playerSum;
      },
      dealerSumComputed() {
        return this.game.gameStatus === 'DEALER_BJ' ? 'BJ' : this.game.dealerSum !== this.game.dealerAltSum ? this.game.dealerSum > 21 ? this.game.dealerAltSum : (this.game.dealerSum + " / " + this.game.dealerAltSum) : this.game.dealerSum;
      }
    },
    watch: {
      game(newVal, oldVal) {
        if ((oldVal.playerSum !== this.game.playerSum || oldVal.playerAltSum !== this.game.playerAltSum) && (this.game.playerSum === 21 || this.game.playerAltSum === 21) && !this.game.gameFinished) {
          this.doneGame();
        }
      },
      '$route': {
        handler: 'loadBoard',
        immediate: true
      }
    },
    components: {
      Bets, Score
    }
  }
</script>

<style scoped lang="scss">
  @import '../../node_modules/animate.css/animate.css';

  a {
    color: #42b983;
  }

  .carts {
    user-select: none;
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
      margin-top: 4%;
      height: 135px;
      margin-bottom: 7%;
    }
  }

  @media screen and(min-width: 1300px) and (max-width: 2000px) {
    .middle-of-field {
      margin-top: 6%;
      height: 250px;
      margin-bottom: 6%;
    }
  }

  @media screen and(min-width: 1700px) and (max-width: 2500px) {
    .middle-of-field {
      margin-top: 8%;
      height: 250px;
      margin-bottom: 10%;
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
/*    position: relative;
    left: 10px;*/
    width: 200px;
    height: 50px;

    span {
      line-height: 40px;
      font-family: "Montserrat", sans-serif;
      font-size: 20px;
      font-weight: bolder;
    }
  }

  .bet-buttons{
    position: relative;
    width: 140px;
    height: 50px;
    right: 25px;
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

  .game-buttons {
    position: relative;
    bottom: 115px;
  }
</style>
