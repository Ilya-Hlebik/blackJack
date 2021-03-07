<template>
  <section>
    <button class="close" @click="needShowRegistrationForm(false)"></button>
    <form key="registration" class="login form-wrapper" @submit.prevent="registration" >
      <img class="anonymous-icon" src="../../assets/images/registration.png">
      <div class="form-group-lg">
        <span class="fa" >
        </span>
        <input placeholder="Email" type="email" v-model="email" class="form-control">
      </div>
      <div class="form-group-lg">
        <span class="fa" enter-leave-class="animated tada">
        </span>
        <input placeholder="UserName" type="text" v-model="username" class="form-control">
      </div>
      <div class="form-group">
        <span class="fa" enter-leave-class="animated tada">
        </span>
        <input type="password" placeholder="Password" v-model="password" class="form-control" autocomplete="on">
      </div>
      <button class="btn btn-success glow-on-hover">Registration</button>
      <div style="color:red;" v-show="showError">
        User Already exists
      </div>
    </form>
  </section>
</template>

<script>
  import {mapActions, mapMutations} from 'vuex';

  export default {
        data() {
            return {
                username: '',
                password: '',
                email: '',
                showError: false
            }
        },
        methods: {
            async registration() {
                let success = await this.signUp({username: this.username, password: this.password, email: this.email});
                this.needShowRegistrationForm(!success);
                this.showError = !success;
            },
            ...mapMutations('login', {
                needShowRegistrationForm: 'needShowRegistrationForm',
            }),
            ...mapActions('login', {
                signUp: 'signUp',
            })
        }
    };
</script>

<style scoped>

</style>
