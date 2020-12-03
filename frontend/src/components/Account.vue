<template>
  <div>
    <div class="content">
      <router-link v-show="!showEditForm"
        class="edit-button"
        key="accounupdate"
        to="/account/update"
        active-class="active"
        tag="span">
        <font-awesome-icon icon="cog"/>
      </router-link>
      <account-update v-if="showEditForm"></account-update>
      <account-info v-else></account-info>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import AccountUpdate from './AccountUpdate';
  import AccountInfo from './AccountInfo';

  export default {
    props: ['showEditForm'],
    name: "Account.vue",
    computed: {
      ...mapGetters('login', {
        user: 'user',
      }),
      needAdd() {
        return this.user.userInfo === null;
      }
    },
    components: {
      AccountUpdate,AccountInfo
    }
  }
</script>

<style scoped>

  .content {
    background-color: #159957;
    background-image: linear-gradient(120deg,#155799,#159957);
    width: 70%;
    min-height: content-box;
    margin: 0 auto;
  }

  .edit-button {
    position: absolute;
    right: 15%;
    cursor: pointer;
    color: black;
  }
</style>
