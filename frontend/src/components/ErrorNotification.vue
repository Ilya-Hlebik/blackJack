<template>
  <div
    class="alert fade alert-simple alert-danger alert-dismissible text-left font__family-montserrat font__size-16 font__weight-light brk-library-rendered rendered show"
    role="alert" data-brk-library="component__alert">
    <button type="button" class="close font__size-18" @click="closeNotification"
            data-dismiss="alert">
    </button>
    <i class="start-icon far fa-times-circle faa-pulse animated"></i>
    <strong class="font__weight-semibold">{{ warningTitle }}</strong> {{ warningDescription }}
  </div>
</template>

<script>
  export default {
    name: "ErrorNotification.vue",
    props: {
      warningTitle: {
        type: String,
        default: 'Oh snap!'
      },
      warningDescription: {
        type: String,
        default: 'Something went wrong.'
      },
    },
    methods: {
      closeNotification() {
        this.$emit("closeNotification")
      }
    }
  }
</script>

<style scoped>

</style>
