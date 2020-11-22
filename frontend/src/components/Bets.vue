<template>
  <div class="bets-container">
    <div class="bets">
    <img class="bet" src="../assets/images/25c.png">
    <img class="bet" src="../assets/images/50c.png">
    <img class="bet" src="../assets/images/1.png">
    <img class="bet" src="../assets/images/5.png">
    </div>
    <div class="bets">
    <img class="bet" src="../assets/images/10.png">
    <img class="bet" src="../assets/images/25.png">
    <img class="bet" src="../assets/images/50.png">
    <img class="bet" src="../assets/images/100.png">
    </div>
  </div>
</template>

<script>
    export default {
        name: "Bets"
    }
</script>

<style scoped>
.bet{
  max-width: 70px;
  margin: 2px;
}
  .bets {
    display: flex;
  }
  .bets-container{
  }

</style>
