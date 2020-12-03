<template>
    <div>
      <div>Username: {{ user.username}}</div>
      <div>Email: {{ user.email}}</div>
      <div v-if="!needAdd">
        <div>Name : {{ user.userInfo.name }}</div>
        <div>Phone : {{ user.userInfo.phone }}</div>
        <div>City : {{ user.userInfo.city }}</div>
        <div>StreetAddress : {{ user.userInfo.streetAddress }}</div>
        <div>DepositSum : {{ user.userInfo.depositSum }}</div>
      </div>
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

</style>
