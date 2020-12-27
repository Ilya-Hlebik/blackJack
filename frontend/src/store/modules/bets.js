export default {
  namespaced: true,
  state: {
    betSum: 0,
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
    clearBets(state) {
      state.betSum = 0;
    }
  },
  actions: {
    calculateSum(store, data) {
      store.commit('calculateSum', data)
      store.commit('login/updateDepositAmount', data, {root:true});
    },
  }
}
