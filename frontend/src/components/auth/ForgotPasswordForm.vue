<template>
  <section>
    <button class="close" @click="needShowForgotPasswordForm(false)">
    </button>
    <form key="login" class="login form-wrapper " @submit.prevent="send">
      <img class="anonymous-icon" src="@/assets/images/work.svg">
      <div class="form-group-lg m-1 flexRow">
        <input type="text" placeholder="Email" v-model="email" class="form-control">
        <button class="btn btn-success glow-on-hover ml-1">Send</button>
      </div>
      <div style="color:red;" v-show="showError">
        Invalid username or Password
      </div>
    </form>
  </section>
</template>

<script>
  import {mapActions, mapMutations} from 'vuex';

  export default {
        data() {
            return {
                email: '',
                showError: false
            }
        },
        methods: {
            async send() {
                const success = await this.sendVerifyLink(this.email);
                this.needShowForgotPasswordForm(!success);
                this.showError = !success
            },
            ...mapActions('login', {
                sendVerifyLink: 'sendVerifyLink',
            }),
            ...mapMutations('login', {
                needShowForgotPasswordForm: 'needShowForgotPasswordForm'
            }),
        }
    }
</script>


<style scoped>

  .flexRow {
    display: flex;
  }
</style>
