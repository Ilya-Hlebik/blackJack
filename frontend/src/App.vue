<template>
  <div id="app">
    <header>
      <div class="hei-container">
        <div class="row">
          <div id="outer" class="alert alert-default">
            <div class="inner">
              <burger></burger>
            </div>
          </div>
        </div>
      </div>
    </header>
    <router-view :key="$route.fullPath"></router-view>
    <authorization></authorization>
  </div>
</template>

<script>
  import Burger from './components/Burger.vue';
  import Authorization from './components/auth/Authorization.vue';
  import {mapActions} from 'vuex';

  export default {

    name: 'App',
    created() {
      this.checkAuthorization();
    },
    methods: {
      ...mapActions('login', {
        checkAuthorization: 'checkAuthorization'
      }),
    },
    components: {Burger, Authorization},
  }
</script>

<style lang="scss">
  @import url('https://fonts.googleapis.com/css?family=Alegreya');

  #app {
    font-family: Alegreya, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    height: 100%;
    overflow-x: hidden;
    box-shadow: inset 0 0 0 100vw rgba(0, 0, 0, 0.25);
    background: url("assets/thumb-1920-400217.png") no-repeat center center;
    background-size: cover;
    color: white;
    min-height: 100vh;
    align-items: center;
    justify-content: center;
    transition: box-shadow .3s ease-out;
  }

  #app:hover {
    box-shadow: inset 0 0 0 100vw rgba(0, 0, 0, 0.1);
  }

  body, html {
    height: 100%;
  }

  .inner {
    display: inline-block;
  }

  #outer {
    width: 100%;
    text-align: left;
  }

  .hei-container {
    padding-right: 100px;
  }

  .score {
    font-size: 32px;
    font-family: "Open Sans", serif;
    color: aqua;
  }

  .btn-main {
    display: inline-block;
    font-weight: 400;
    color: #212529;
    text-align: center;
    vertical-align: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-color: transparent;
    border: 1px solid transparent;
    padding: 0.375rem 0.75rem;
    border-radius: 0.25rem;
    transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
    letter-spacing: 0.1em;
    cursor: pointer;
    font-size: 14px;
    line-height: 25px;
    max-width: 80px;
    position: relative;
    text-decoration: none;
    text-transform: uppercase;
    width: 80%;
    margin: 20px;
  }

  .btn-main:hover {
    text-decoration: none;
  }

  /*btn_background*/
  .effect01 {
    color: #FFF;
    border: 4px solid #000;
    box-shadow: 0px 0px 0px 1px #000 inset;
    overflow: hidden;
    position: relative;
    transition: all 0.3s ease-in-out;
  }

  .effect01:hover {
    border: 4px solid #666;
    background-color: #159957;
    box-shadow: 0px 0px 0px 4px #EEE inset;
  }

  /*btn_text*/
  .effect01 span {
    transition: all 0.2s ease-out;
    z-index: 2;
  }

  .effect01:hover span {
    letter-spacing: 0.13em;
    color: #333;
  }

  /*highlight*/
  .effect01:after {
    background: #FFF;
    border: 0px solid #000;
    content: "";
    height: 155px;
    left: -75px;
    opacity: .8;
    position: absolute;
    top: -50px;
    -webkit-transform: rotate(35deg);
    transform: rotate(35deg);
    width: 50px;
    transition: all 1s cubic-bezier(0.075, 0.82, 0.165, 1); /*easeOutCirc*/
    z-index: 1;
  }

  .effect01:hover:after {
    background: #FFF;
    border: 20px solid #000;
    opacity: 0;
    left: 120%;
    -webkit-transform: rotate(40deg);
    transform: rotate(40deg);
  }
</style>
