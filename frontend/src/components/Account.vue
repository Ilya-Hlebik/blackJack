<template>
  <div class="modal-background">
    <div class="content" v-on-clickaway="away">
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
  import {directive as onClickaway} from 'vue-clickaway';

  export default {
    directives: {
      onClickaway: onClickaway,
    },
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
    methods: {
      away: function() {
        this.$router.push('menu')
      }
    },

    components: {
      AccountUpdate, AccountInfo
    }
  }
</script>

<style scoped>

  .content {
    background-color: #159957;
    background-image: linear-gradient(120deg, #155799, #159957);
    width: 50%;
    min-height: content-box;
    border: 3px solid green;
    padding: 10px;
    margin: 100px auto auto;
    top: 30%;
    left: 50%;
    position: absolute;
    transform: translate(-50%, -50%)
  }

  .edit-button {
    position: absolute;
    right: 2%;
    cursor: pointer;
    color: black;
  }
</style>
