<template>
  <div class="modal-background" v-show="needShowLoginFrom || needShowRegistrationForm || needShowForgotPasswordForm">
    <div class="login-form">
      <transition-group name="button-transition"
                        enter-active-class="animated zoomIn"
                        mode="out-in">
        <login-form   v-show="needShowLoginFrom" key="loginForm"></login-form>
        <registration v-show="needShowRegistrationForm" key="registrationForm"></registration>
        <forgot-password-form v-show="needShowForgotPasswordForm"  key="forgotPasswordForm"></forgot-password-form>
      </transition-group>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';
  import LoginForm from './LoginForm.vue';
  import Registration from './RegistrationForm.vue';
  import ForgotPasswordForm from './ForgotPasswordForm.vue';
  import RecoverPasswordForm from './RecoverPasswordForm.vue';


  export default {
        components: {LoginForm, Registration, ForgotPasswordForm,RecoverPasswordForm},
        computed: {
            ...mapGetters('login', {
                needShowLoginFrom: 'needShowLoginFrom',
                showModalWindow: 'showModalWindow',
                needShowRegistrationForm: 'needShowRegistrationForm',
                needShowForgotPasswordForm: 'needShowForgotPasswordForm',
                needShowRecoverPasswordForm: 'needShowRecoverPasswordForm'
            }),

        },
    };
</script>

<style lang="scss">
  .modal-background {
    position: fixed;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 100;
    background-color: rgba(#000, 0.5);
  }

  .login-form {
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .login {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 500px;
    padding: 30px;
    border-radius: 10px;
    background: -webkit-linear-gradient(top, #007bff, #ffffff);
  }

  .close {
    position: absolute;
    right: 15px;
    width: 32px;
    height: 32px;
    opacity: 0.3;
  }

  .close:hover {
    opacity: 1;
  }

  .close:before, .close:after {
    position: absolute;
    left: 15px;
    content: ' ';
    height: 33px;
    width: 2px;
    background-color: #333;
  }

  .close:before {
    transform: rotate(45deg);
  }

  .close:after {
    transform: rotate(-45deg);
  }

  .anonymous-icon {
    height: 120px;
    width: 120px;
    border-radius: 50%;
    margin-bottom: 30px;
  }

</style>
