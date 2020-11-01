import axios from "axios";

export default {

  namespaced: true,
  state: {
    dealerCards: [],
    /*
    cart{
    id;
    img;
    val;
    }
     */
    playerCards: [],
    gameId: '',
    needShowStartGameButton: true,
    game: null
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
    }
  },
  mutations: {
    clearDealerCards(state) {
      state.dealerCards = [];
    },
    clearPlayerCards(state) {
      state.dealerCards = [];
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
    loadExistingGame(state, data) {
      return state.game = data;
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
      const response = await axios.get('/backend/game/get/' + data);
      store.commit('loadExistingGame', response.data);
      return response.data;
    },
    async dealerTurns(store, data) {
      const response = await axios.post('/backend/game/dealerTurns/' + data);
      store.commit('loadExistingGame', response.data);
      return response.data;
    },
    async addCardToPlayer(store, data) {
      const response = await axios.post('/backend/game/addCardToPlayer/' + data);
      store.commit('loadExistingGame', response.data);
      return response.data;
    },
  }
};
