<template>
  <div>
    <div v-if="showSuccessMessage">
      <div
        class="alert fade alert-simple alert-success alert-dismissible text-left font__family-montserrat font__size-16 font__weight-light brk-library-rendered rendered show">
        <button type="button" class="close font__size-18" @click="showSuccessMessage=!showSuccessMessage"
                data-dismiss="alert">
									<span aria-hidden="true"><a href="https://www.youtube.com/watch?v=_XiOcsj3oNI&t=50s" target="_blank">
                    <i class="fa fa-times greencross"></i>
                    </a></span>
          <span class="sr-only">Close</span>
        </button>
        <i class="start-icon far fa-check-circle faa-tada animated"></i>
        <strong class="font__weight-semibold">Done!</strong> Info was successfully saved.
      </div>
    </div>
    <div class="col-sm-12" v-if="showFailMessage">
      <div
        class="alert fade alert-simple alert-danger alert-dismissible text-left font__family-montserrat font__size-16 font__weight-light brk-library-rendered rendered show"
        role="alert" data-brk-library="component__alert">
        <button type="button" class="close font__size-18" @click="showFailMessage=!showFailMessage"
                data-dismiss="alert">
									<span aria-hidden="true">
										<i class="fa fa-times danger "></i>
									</span>
          <span class="sr-only">Close</span>
        </button>
        <i class="start-icon far fa-times-circle faa-pulse animated"></i>
        <strong class="font__weight-semibold">Oh snap!</strong> Something went wrong.
      </div>
    </div>
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
      saveUserInfoAndGetResult() {
        debugger
        let successfullySaved = this.saveUserInfo();
        this.showSuccessMessage = successfullySaved;
        this.showFailMessage = !successfullySaved;
      },
      ...mapActions('login', {
        saveUserInfo: 'saveUserInfo',
      }),
    }
  }
</script>

<style scoped lang="scss">
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
