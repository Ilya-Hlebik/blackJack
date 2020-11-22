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
  import Burger from './components/Burger';
  import Authorization from './components/auth/Authorization';

  export default {
    name: 'App',
    components: {Burger,Authorization},
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
</style>
