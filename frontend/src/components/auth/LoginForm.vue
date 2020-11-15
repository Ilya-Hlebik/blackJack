<template>
  <section>
    <button class="close" @click="needShowLoginFrom(false)">
    </button>
    <form key="login" class="login form-wrapper" @submit.prevent="signIn">
      <img class="anonymous-icon" src="@/assets/images/work.svg">
      <div class="form-group-lg">
        <span class="fa" enter-leave-class="animated tada">
        </span>
        <input type="text" placeholder="Username" v-model="username" class="form-control">
      </div>
      <div class="form-group">
        <span class="fa" enter-leave-class="animated tada">
        </span>
        <input type="password" placeholder="Password" v-model="password" class="form-control" autocomplete="on">
      </div>
      <a type="button" href="javaScript:" class="btn forgot" @click="showForgotPasswordForm">Forgot password</a>
        <button class="btn btn-success glow-on-hover">Login</button>
      <div style="color:red;" v-show="showError">
        Invalid username or Password
      </div>
    </form>
  </section>
</template>

<script>
  import {mapActions, mapGetters, mapMutations} from 'vuex';

  export default {
        data() {
            return {
                username: '',
                password: '',
                showError: false
            }
        },
        methods: {
            async signIn() {
                await this.login({username: this.username, password: this.password});
                this.needShowLoginFrom(!this.isLogged);
                this.showError = !this.isLogged
            },
            ...mapActions('login', {
                login: 'signIn',
            }),
            ...mapMutations('login', {
                needShowLoginFrom: 'needShowLoginFrom',
                needShowForgotPasswordForm: 'needShowForgotPasswordForm'
            }),
            showForgotPasswordForm(){
                this.needShowLoginFrom(false);
                this.needShowForgotPasswordForm(true);
            }
        },
        computed: {
            ...mapGetters('login', {
                isLogged: 'isLogged',
            })
        }
    };
</script>

<style lang="scss" scoped>


  .glow-on-hover {
    width: 220px;
    height: 50px;
    border: none;
    outline: none;
    color: #fff;
    background: #111;
    cursor: pointer;
    position: relative;
    z-index: 0;
    border-radius: 10px;
  }

  .glow-on-hover:before {
    content: '';
    background: linear-gradient(45deg, #ff0000, #ff7300, #fffb00, #48ff00, #00ffd5, #002bff, #7a00ff, #ff00c8, #ff0000);
    position: absolute;
    top: -2px;
    left:-2px;
    background-size: 400%;
    z-index: -1;
    filter: blur(5px);
    width: calc(100% + 4px);
    height: calc(100% + 4px);
    animation: glowing 20s linear infinite;
    opacity: 0;
    transition: opacity .3s ease-in-out;
    border-radius: 10px;
  }

  .glow-on-hover:active {
    color: #000
  }

  .glow-on-hover:active:after {
    background: transparent;
  }

  .glow-on-hover:hover:before {
    opacity: 1;
  }

  .glow-on-hover:after {
    z-index: -1;
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background: #00ad6e;
    left: 0;
    top: 0;
    border-radius: 10px;
  }

  @keyframes glowing {
    0% { background-position: 0 0; }
    50% { background-position: 400% 0; }
    100% { background-position: 0 0; }
  }

  .forgot {
    color: red;
  }

</style>
