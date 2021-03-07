<template>
  <section>
    <div class="modal-background" v-show="needShowForm">
      <div class="login-form">
        <button class="close" @click="needShowRecoverPasswordForm(false)">
        </button>
        <form key="login" class="login form-wrapper" @submit.prevent="send">
          <img class="anonymous-icon" src="../../assets/images/work.svg">
          <div class="form-group-lg m-1 flexRow">
            <input type="password" placeholder="Password" v-model="password" class="form-control">
            <input type="password" placeholder="Confirm password" v-model="confirmPassword" class="form-control">
            <button class="btn btn-success glow-on-hover ml-1">Send</button>
          </div>
          <div style="color:red;" v-show="showError">
            Invalid username or Password
          </div>
          {{ passwordDidntCorresponds }}
        </form>
      </div>
    </div>
  </section>
</template>

<script>
  import {mapActions, mapGetters, mapMutations} from 'vuex';

  export default {
        data() {
            return {
                password: '',
                confirmPassword: '',
                showError: false
            }
        },
        methods: {
            async send() {
                const success = await this.createNewPassword({newPassword: this.password, token: this.$route.query.token});
                this.needShowRecoverPasswordForm(!success);
                this.showError = !success
            },
            ...mapActions('login', {
                createNewPassword: 'createNewPassword',
            }),
            ...mapMutations('login', {
                needShowRecoverPasswordForm: 'needShowRecoverPasswordForm'
            }),
        },
        computed: {
            passwordDidntCorresponds() {
                return this.confirmPassword === this.password ? "" : "Passwords didn't correspons";
            },
            ...mapGetters('login', {
                needShowForm: 'needShowRecoverPasswordForm',
            })
        }
    }
</script>


<style scoped>

  .flexRow {
    display: flex;
  }
</style>
