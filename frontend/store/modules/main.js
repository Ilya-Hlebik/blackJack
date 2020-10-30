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
  },
  getters: {
    dealerCards(state) {
      return state.dealerCards;
    },
    playerCards(state) {
      return state.playerCards;
    },
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
    }
  },
  actions: {
    async loadDealerCards(store) {
      const response = await axios.get('/backend/product/all');
      store.commit('loadDealerCards', response.data);
    },
    async loadPlayerCards(store) {
      const response = await axios.get('/backend/product/all');
      store.commit('loadPlayerCards', response.data);
    },
  }
};
