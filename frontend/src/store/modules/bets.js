export default {
  namespaced: true,
  state: {
    betSum: 0,
    fullDepositSum: 0
  },
  getters: {
    betSum(state) {
      return state.betSum;
    },
  },
  mutations: {
    calculateSum(state, data) {
      state.betSum += data;
    },
    setBetSum(state, data) {
      state.betSum = data;
    },
    setFullDepositSum(state, data) {
      state.fullDepositSum = data;
    }
  },
  actions: {
    async  calculateSum(store, data) {
      if (store.state.betSum === 0) {
        store.commit("setFullDepositSum", store.rootGetters['login/depositSum']);
      }
      store.commit('calculateSum', data)
      store.commit('login/updateDepositAmount', data, {root: true});
    },
    async clearBets(store) {
      store.commit('setBetSum', 0)
      store.commit('login/setDepositSum', store.state.fullDepositSum, {root: true});
    }
  }
}
