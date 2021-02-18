<template>
  <section class="log-section">
    <header class="log-header">
      Logs
    </header>
    <div class="log-wrapper">
      <div v-for="log in game.logEntities">
        <div class="time"> {{ new Date(log.date).toISOString() }}</div>
        <div class="message">{{ log.message }}</div>
      </div>
    </div>
  </section>

</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    name: "LogWindow",
    computed: {
      ...mapGetters('main', {
        game: 'game',
      }),
    }
  }
</script>

<style scoped>
  .log-wrapper {
    display: block;
    overflow: hidden;
    overflow-y: auto;
    background-color: #f7f8fa;
    /*    */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    height: 200px;
    width: 400px;
    color: black;
  }

  .log-header {

    background-color: #3f3f41;
    color: #b8b7ad;
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 0 8px;
    min-height: 48px;
    position: relative;
    border-bottom: 1px solid rgba(0, 0, 0, .15);
    border-radius: 10px 10px 0 0;
    cursor: pointer;
  }

  .message {
    padding: 4px 12px 4px 8px;
    position: relative;
    font-size: 14px;
    background-color: white;
    border: 1px solid lightsteelblue;
    border-radius: 6px;
    text-align: left;
    margin: 2px;
  }

  .time {
    text-align: right;
    font-size: 11px;
  }

  .log-section {
    margin-top: -150px;
  }
</style>
