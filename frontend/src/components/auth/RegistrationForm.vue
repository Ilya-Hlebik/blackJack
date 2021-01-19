<template>
  <section>
    <button class="close" @click="needShowRegistrationForm(false)"></button>
    <form key="registration" class="login form-wrapper" @submit.prevent="registration" >
      <img class="anonymous-icon" src="@/assets/images/registration.png">
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
      <div class="form-group-lg">
        <span class="fa" enter-leave-class="animated tada">
        </span>
        <input type="password" placeholder="Password" v-model="password" class="form-control" autocomplete="on">
      </div>
      <div class="form-group-lg">
           <span class="fa" enter-leave-class="animated tada">
        </span>
        <input placeholder="Phone" type="number" v-model="phone" class="form-control">
      </div>
      <div class="form-group-lg">
              <span class="fa" enter-leave-class="animated tada">
        </span>
        <input placeholder="City" type="text" v-model="city" class="form-control">
      </div>
      <div class="form-group-lg">
               <span class="fa" enter-leave-class="animated tada">
        </span>
        <input placeholder="Street Address" type="text" v-model="streetAddress" class="form-control">
      </div>
      <button   class="btn btn-success glow-on-hover" :disabled="!enableRegistration">Registration</button>
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
        showError: false,
        phone: '',
        city: '',
        streetAddress: ''
      }
        },
        methods: {
          async registration() {
            let success = await this.signUp(
              {
                username: this.username,
                password: this.password,
                email: this.email,
                phone: this.phone,
                city: this.city,
                streetAddress: this.streetAddress
              });
            this.needShowRegistrationForm(!success);
                this.showError = !success;
            },
            ...mapMutations('login', {
                needShowRegistrationForm: 'needShowRegistrationForm',
            }),
            ...mapActions('login', {
                signUp: 'signUp',
            })
        },
    computed: {
      enableRegistration() {
        return this.username.trim() !== '' && this.password.trim() !== '' && this.email.trim() !== ''
          && this.phone.trim() !== '' && this.city.trim() !== '' && this.streetAddress.trim() !== ''
      }
    }
  };
</script>

<style scoped>

</style>
