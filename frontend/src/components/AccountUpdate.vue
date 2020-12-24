<template>
  <div>
    <notifications :showSuccessMessage.sync="showSuccessMessage"
                   :showFailMessage.sync="showFailMessage">
    </notifications>
    <h1>Edit user Info</h1>
    <div class="form__group field">
      <input id='name' type="text" class="form__field" placeholder="Name" :value="user.userInfo.name"
             @input="setUserName" key="name">
      <label for="name" class="form__label">Name</label>
    </div>
    <div class="form__group field">
      <input id='phone' type="text" class="form__field" placeholder="Phone" :value="user.userInfo.phone"
             @input="setPhone" key="phone">
      <label for="phone" class="form__label">Phone</label>
    </div>
    <div class="form__group field">
      <input id='city' type="text" class="form__field" placeholder="City" :value="user.userInfo.city"
             @input="setCity" key="city">
      <label for="city" class="form__label">City</label>
    </div>
    <div class="form__group field">
      <input id='streetAddress' type="text" class="form__field" placeholder="Street Address"
             :value="user.userInfo.streetAddress"
             @input="setStreetAddress" key="streetAddress">
      <label for="streetAddress" class="form__label">Phone</label>
    </div>
    <button class="btn-main   effect01" @click="saveUserInfoAndGetResult">Save</button>
  </div>
</template>

<script>
  import {mapActions, mapGetters, mapMutations} from "vuex";
  import Notifications from './Notifications';

  export default {
    name: "AccountUpdate",
    data() {
      return {
        showSuccessMessage: false,
        showFailMessage: false
      }
    },
    computed: {
      ...mapGetters('login', {
        user: 'user',
      })
    },
    methods: {
      ...mapMutations('login', {
        setUserName: 'setUserName',
        setPhone: 'setPhone',
        setCity: 'setCity',
        setStreetAddress: 'setStreetAddress',
      }),
      async saveUserInfoAndGetResult() {
        const successfullySaved = await this.saveUserInfo();
        this.showSuccessMessage = successfullySaved;
        this.showFailMessage = !successfullySaved;
      },
      ...mapActions('login', {
        saveUserInfo: 'saveUserInfo',
      }),
    },
    components: {
      Notifications
    }
  }
</script>

<style   lang="scss">
  $primary: #11998e;
  $secondary: #38ef7d;
  $white: #fff;
  $gray: #9b9b9b;
  .form__group {
    margin: 10px 20px;
    position: relative;
    padding: 15px 0 0;
    width: 50%;
  }

  .form__field {

    font-family: inherit;
    width: 100%;
    border: 0;
    border-bottom: 2px solid $gray;
    outline: 0;
    font-size: 1.3rem;
    color: $white;
    padding: 7px 0;
    background: transparent;
    transition: border-color 0.2s;

    &::placeholder {
      color: transparent;
    }

    &:placeholder-shown ~ .form__label {
      font-size: 1.3rem;
      cursor: text;
      top: 20px;
    }
  }

  .form__label {
    position: absolute;
    top: 0;
    display: block;
    transition: 0.2s;
    font-size: 1rem;
    color: $gray;
  }

  .form__field:focus {
    ~ .form__label {
      position: absolute;
      top: 0;
      display: block;
      transition: 0.2s;
      font-size: 1rem;
      color: $primary;
      font-weight: 700;
    }

    padding-bottom: 6px;
    font-weight: 700;
    border-width: 3px;
    border-image: linear-gradient(to right, $primary, $secondary);
    border-image-slice: 1;
  }

  /* reset input */
  .form__field {
    &:required, &:invalid {
      box-shadow: none;
    }
  }

  .fa-times {
    -webkit-animation: blink-1 2s infinite both;
    animation: blink-1 2s infinite both;
  }

  /**
   * ----------------------------------------
   * animation blink-1
   * ----------------------------------------
   */
  @-webkit-keyframes blink-1 {
    0%,
    50%,
    100% {
      opacity: 1;
    }
    25%,
    75% {
      opacity: 0;
    }
  }

  @keyframes blink-1 {
    0%,
    50%,
    100% {
      opacity: 1;
    }
    25%,
    75% {
      opacity: 0;
    }
  }

</style>
