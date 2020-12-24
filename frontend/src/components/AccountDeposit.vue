<template>
  <div>
    <notifications :showSuccessMessage.sync="showSuccessMessage"
                   :showFailMessage.sync="showFailMessage">
    </notifications>
    <h1>Deposit</h1>
    <div class="form__group field">
      <input id='name' type="text" class="form__field" placeholder="Deposit"
             v-model="depositSum" key="depositSum">
      <label for="name" class="form__label">Deposit Sum</label>
    </div>
    <button class="btn-main effect01 deposit-btn" key="depositBtn" @click="deposit(depositSum)">Deposit</button>
  </div>
</template>

<script>
  import Notifications from "./Notifications";
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "AccountDeposit",
    data() {
      return {
        showSuccessMessage: false,
        showFailMessage: false,
        depositSum: 0
      }
    },
    methods: {
      ...mapActions('login', {
        saveUserDeposit: 'saveUserDeposit',
      }),
      async deposit(depositSum) {
        const successfullySaved = await this.saveUserDeposit(depositSum);
        this.showSuccessMessage = successfullySaved;
        this.showFailMessage = !successfullySaved;
      }
    },
    computed: {
      ...mapGetters('login', {
        user: 'user',
      }),
    },
    components: {
      Notifications
    }
  }
</script>

<style scoped>

</style>
