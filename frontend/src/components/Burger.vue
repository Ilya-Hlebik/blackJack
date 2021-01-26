<template>
  <slide  right   :closeOnNavigation="true">
    <img v-show="isLogged" class="anonymous-icon" :src="user.userInfo.imageUrl">
    <a v-show="!isLogged" href="javaScript:" @click="openAuthorizationForm">
      <span class="bm-item">Login</span>
    </a>
    <a v-show="!isLogged" href="javaScript:" @click="openRegistrationForm">
      <span class="bm-item">Registration</span>
    </a>
    <router-link
      v-show="isLogged"
      key="account"
      to="/account"
      active-class="active"
      tag="a">
      <span class="bm-item">My Account</span>
    </router-link>
    <a v-show="isLogged" href="javaScript:" @click="logOut">
      <span class="bm-item">Logout</span>
    </a>
  </slide>
</template>

<script>
  import {Slide} from 'vue-burger-menu'
  import {mapActions, mapGetters, mapMutations} from "vuex";

  export default {
        methods: {
            ...mapActions('login', {
                logOut: 'logOut'
            }),
            ...mapMutations('login', {
                needShowLoginFrom: 'needShowLoginFrom',
                needShowRegistrationForm: 'needShowRegistrationForm',
                needShowForgotPasswordForm: 'needShowForgotPasswordForm'
            }),
            openAuthorizationForm() {
                this.needShowRegistrationForm(false);
                this.needShowForgotPasswordForm(false);
                this.needShowLoginFrom(true);
            },
            openRegistrationForm() {
                this.needShowLoginFrom(false);
                this.needShowForgotPasswordForm(false);
                this.needShowRegistrationForm(true);
            }
        },
        components: {
            Slide
        },
        computed: {
          ...mapGetters('login', {
            isLogged: 'isLogged',
            user: 'user'
          })
        }
    };
</script>

<style scoped>
  .bm-item {
    color: #b8b7ad;
    font-size: 20px;
  }

</style>
