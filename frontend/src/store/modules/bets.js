export default {
  namespaced: true,
  state: {
    betSum: 0,
    depositSum: 0
  },
  getters: {
    betSum(state) {
      return state.betSum;
    },
    depositSum(state) {
      return state.depositSum;
    }
  },
  mutations: {
    calculateSum(state, data) {
      state.betSum += data;
    },
    updateDepositSum(state, data) {
      state.depositSum = data
    }
  }
}
