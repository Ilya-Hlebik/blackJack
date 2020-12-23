<template>
  <div>
    <h1>User Info</h1>
    <div class="userInfo">
      <div class="block">Username: {{ user.username}}</div>
      <div class="block">Email: {{ user.email}}</div>
      <div v-if="!needAdd">
        <div class="block">Name : {{ user.userInfo.name }}</div>
        <div class="block">Phone : {{ user.userInfo.phone }}</div>
        <div class="block">City : {{ user.userInfo.city }}</div>
        <div class="block">StreetAddress : {{ user.userInfo.streetAddress }}</div>
        <div class="block">DepositSum : {{ user.userInfo.depositSum }}</div>
      </div>
    </div>
    <button class="btn-main effect01 deposit-btn">Deposit</button>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    name: "AccountInfo",
    computed: {
      ...mapGetters('login', {
        user: 'user',
      }),
      needAdd() {
        return this.user.userInfo === null;
      }
    },
  }
</script>

<style scoped>
  .userInfo {
    padding-left: 5%;
    text-align: left;
    font-size: 1.3rem;

  }

  .block {
    border-bottom: 2px solid #159957;;
  }

  .deposit-btn {
    max-width: 95px;
  }
</style>
