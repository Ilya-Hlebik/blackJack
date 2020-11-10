<template>
  <div>
    <router-link class="btn effect01"
                 tag="button"
                 :to="'/game'">Start Game
    </router-link>
  </div>
</template>

<script>
  export default {}
</script>

<style scoped lang="scss">
  .btn {
    letter-spacing: 0.1em;
    cursor: pointer;
    font-size: 14px;
    font-weight: 400;
    line-height: 45px;
    max-width: 160px;
    position: fixed;
    text-decoration: none;
    text-transform: uppercase;
    width: 100%;

  }
  .btn:hover {
    text-decoration: none;
  }

  .effect01 {
    color: #FFF;
    border: 4px solid #000;
    box-shadow:0px 0px 0px 1px #000 inset;
    overflow: hidden;
    transition: all 0.3s ease-in-out;
    position: fixed;
    margin: auto;
    left: 0;
    right: 0;
    top: 40%;
  }
  .effect01:hover {
    border: 4px solid #666;
    background-color: #FFF;
    box-shadow:0px 0px 0px 4px #EEE inset;
  }

  .effect01 span {
    transition: all 0.2s ease-out;
    z-index: 2;
  }
  .effect01:hover span{
    letter-spacing: 0.13em;
    color: #333;
  }
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
    transition: all 1s cubic-bezier(0.075, 0.82, 0.165, 1);/*easeOutCirc*/
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
