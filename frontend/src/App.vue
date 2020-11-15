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
  @import '../css/animated.css';
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    padding-top:  2%;
    padding-bottom: 5%;
    height: 100%;
    background-image: url("assets/thumb-1920-400217.jpg");
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
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
</style>
