<template>
  <div id="app">
    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
  }
</script>

<style>
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
</style>
