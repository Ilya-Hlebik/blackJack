<template>
  <div
    class="alert fade alert-simple alert-success alert-dismissible text-left font__family-montserrat font__size-16 font__weight-light brk-library-rendered rendered show">
    <button type="button" class="close font__size-18" @click="closeNotification"
            data-dismiss="alert">
    </button>
    <i class="start-icon far fa-check-circle faa-tada animated"></i>
    <strong class="font__weight-semibold">{{ warningTitle }}</strong> {{ warningDescription }}
  </div>
</template>

<script>
  export default {
    name: "SuccessNotification",
    props: {
      warningTitle: {
        type: String,
        default: 'Done!'
      },
      warningDescription: {
        type: String,
        default: 'Info was successfully saved.'
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
