<template>
  <div>
    <div v-if="showSuccessMessage">
      <success-notification @closeNotification="closeNotification"></success-notification>
    </div>
    <div v-if="showFailMessage">
      <error-notification @closeNotification="closeNotification"></error-notification>
    </div>
  </div>
</template>

<script>
  import SuccessNotification from './SuccessNotification.vue';
  import ErrorNotification from './ErrorNotification.vue';

  export default {
    name: "Notifications.vue",
    props: ['showSuccessMessage', 'showFailMessage'],
    methods: {
      closeNotification() {
        this.$emit('update:showSuccessMessage', false)
        this.$emit('update:showFailMessage', false)
      }
    },
    components: {
      SuccessNotification, ErrorNotification
    }
  }
</script>

<style>
  .alert > .start-icon {
    margin-right: 0;
    min-width: 20px;
    text-align: center;
  }

  .alert > .start-icon {
    margin-right: 5px;
  }

  .greencross {
    font-size: 18px;
    color: #25ff0b;
    text-shadow: none;
  }

  .alert-simple.alert-success {
    border: 1px solid rgba(36, 241, 6, 0.46);
    background-color: rgba(7, 149, 66, 0.12156862745098039);
    box-shadow: 0px 0px 2px #259c08;
    color: #0ad406;
    text-shadow: 2px 1px #00040a;
    transition: 0.5s;
    cursor: pointer;
  }

  .alert-success:hover {
    background-color: rgba(7, 149, 66, 0.35);
    transition: 0.5s;
  }

  .alert-simple.alert-info {
    border: 1px solid rgba(6, 44, 241, 0.46);
    background-color: rgba(7, 73, 149, 0.12156862745098039);
    box-shadow: 0px 0px 2px #0396ff;
    color: #0396ff;
    text-shadow: 2px 1px #00040a;
    transition: 0.5s;
    cursor: pointer;
  }

  .alert-info:hover {
    background-color: rgba(7, 73, 149, 0.35);
    transition: 0.5s;
  }

  .alert-simple.alert-danger {
    border: 1px solid rgba(241, 6, 6, 0.81);
    background-color: rgba(220, 17, 1, 0.16);
    box-shadow: 0px 0px 2px #ff0303;
    color: #ff0303;
    text-shadow: 2px 1px #00040a;
    transition: 0.5s;
    cursor: pointer;
    margin-bottom: 0;
  }

  .alert-danger:hover {
    background-color: rgba(220, 17, 1, 0.33);
    transition: 0.5s;
  }

  .danger {
    font-size: 18px;
    color: #ff0303;
    text-shadow: none;
  }

  .alert:before {
    content: '';
    position: absolute;
    width: 0;
    height: calc(100% - 44px);
    border-left: 1px solid;
    border-right: 2px solid;
    border-bottom-right-radius: 3px;
    border-top-right-radius: 3px;
    left: 0;
    top: 50%;
    transform: translate(0, -50%);
    height: 20px;
  }
</style>
