import axios from "axios";
import {router} from "../../routes";

export default {

  namespaced: true,
  state: {
    dealerCards: [],
    startNewGameClicked:false,
    playerCards: [],
    gameId: '',
    needShowStartGameButton: true,
    game: {}
  },
  getters: {
    dealerCards(state) {
      return state.dealerCards;
    },
    playerCards(state) {
      return state.playerCards;
    },
    needShowStartGameButton(state) {
      return state.needShowStartGameButton
    },
    game(state) {
      return state.game
    },
    gameId(state){
      return state.gameId
    },
    startNewGameClicked(state){
      return state.startNewGameClicked;
    }
  },
  mutations: {
    clearDealerCards(state) {
      state.game.dealerCards = [];
    },
    clearPlayerCards(state) {
      state.game.playerCards = [];
    },
    loadDealerCards(state, data) {
      state.dealerCards = data;
    },
    loadPlayerCards(state, data) {
      state.playerCards = data;
    },
    setGameId(state, data) {
      state.gameId = data;
    },
    needShowStartGameButton(state, data) {
      return state.needShowStartGameButton = data;
    },
    setStateGame(state, data){
      state.game = data;
    },
    addCardToDealerCards(state,data){
      state.game.dealerCards.push(data);
    },
    setDealerSum(state,data){
      state.game.dealerSum = data.dealerSum;
      state.game.dealerAltSum = data.dealerAltSum;
    },
    setGameStatus(state,data){
      state.game.gameStatus = data;
    }
    ,
    setGameFinished(state,data){
      state.game.gameFinished = data;
    },
    setStartNewGameClicked(state,data){
      return state.startNewGameClicked = data;
    }
  },
  actions: {
    async loadDealerCards(store, data) {
      const response = await axios.post('/backend/cart/dealer', data, {headers: {"Content-Type": "text/plain"}});
      store.commit('loadDealerCards', response.data);
    },
    async loadPlayerCards(store, data) {
      const response = await axios.post('/backend/cart/player', data, {headers: {"Content-Type": "text/plain"}});
      store.commit('loadPlayerCards', response.data);
    },
    async loadGame(store) {
      const response = await axios.get('/backend/game/new');
      store.commit('setGameId', response.data);
      return response.data;
    },
    async loadExistingGame(store, data) {
      return await axios.get('/backend/game/' + data)
          .then(response => {
            store.commit('setStateGame', response.data)
            return response.data;
          })
          .catch(reason => {router.push('/menu');
          return null;
          });
    },
    async loadNewGame(store, data) {
      const betResponse = await axios.post('/backend/bet/place/', data);
      if (betResponse.status === 200) {
        const response = await axios.get('/backend/game/get/' + data.gameId);
        store.commit('setStateGame', response.data);
        return response.data;
      }
    },
    async dealerTurns(store, data) {
      const response = await axios.post('/backend/game/dealerTurns/' + data);
      store.dispatch("applyDealerCardsWithTimeout",response.data)
      return response.data;
    },
    applyDealerCardsWithTimeout(store, data){
      let temp = [];
      let gameStatus="";
      data.dealerCards.shift();
      data.dealerCards.forEach(card => temp.push(card));
      data.dealerCards = store.state.game.dealerCards;
      data.dealerSum = store.state.game.dealerSum;
      data.dealerAltSum = store.state.game.dealerAltSum;
      gameStatus = data.gameStatus;
      data.gameStatus = '';
      data.gameFinished = false;
      store.commit('setStateGame', data);
      const timeoutPromise = (timeout) => new Promise((resolve) => setTimeout(resolve, timeout));
      const randForTen = async () => {
        for (let i = 0; i < temp.length; i++) {
          await timeoutPromise(700);
          store.commit('addCardToDealerCards', temp[i]);
          store.commit('setDealerSum', {dealerSum: data.gameSteps[i + data.playerCards.length -1 ].dealerSum,dealerAltSum: data.gameSteps[i + data.playerCards.length -1 ].dealerAltSum});
        }
        store.commit('setGameStatus', gameStatus);
        store.commit('setGameFinished', true);
        store.dispatch("login/checkAuthorization", null, {root: true})
      }
      randForTen();
    },
    async addCardToPlayer(store, data) {
      const response = await axios.post('/backend/game/addCardToPlayer/' + data);
      store.commit('setStateGame', response.data);
      return response.data;
    },
  }
};
