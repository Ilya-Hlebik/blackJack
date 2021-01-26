<template>
  <section>
    <button class="close" @click="needShowRegistrationForm(false)"></button>
    <form key="registration" class="login form-wrapper" @submit.prevent="registration">
      <div class="d-flex">
        <div class="main-section">
          <div class="form-group-lg mb-1">
        <span class="fa">
        </span>
            <input placeholder="Email" type="email" v-model="email" class="form-control">
          </div>
          <div class="form-group-lg mb-1">
        <span class="fa" enter-leave-class="animated tada">
        </span>
            <input placeholder="UserName" type="text" v-model="username" class="form-control">
          </div>
          <div class="form-group-lg mb-1">
        <span class="fa" enter-leave-class="animated tada">
        </span>
            <input type="password" placeholder="Password" v-model="password" class="form-control" autocomplete="on">
          </div>
          <div class="form-group-lg mb-1">
        <span class="fa" enter-leave-class="animated tada">
        </span>
            <input placeholder="Phone" type="number" v-model="phone" class="form-control">
          </div>
          <div class="form-group-lg mb-1">
        <span class="fa" enter-leave-class="animated tada">
        </span>
            <input placeholder="City" type="text" v-model="city" class="form-control">
          </div>
          <div class="form-group-lg mb-1">
        <span class="fa" enter-leave-class="animated tada">
        </span>
            <input placeholder="Street Address" type="text" v-model="streetAddress" class="form-control">
          </div>
        </div>
        <div class="form-group-lg mb-1">
          <label class="btn btn-info" for="image-upload">Choose Avatar</label>
          <input id="image-upload" class="registration-input" type="file" accept="image/*" @change="Image_onFileChanged"
                 ref="file" name="Choose file">
          <button type="button" class="btn btn-danger delete-button" @click="deleteImage">X</button>
          <div>
            <img :class="previewImageClass" class="anonymous-icon" :src="previewImage">
          </div>
        </div>
      </div>
      <button   class="btn btn-success glow-on-hover" :disabled="!enableRegistration">Registration</button>
      <div style="color:red;" v-show="showError">
        User Already exists
      </div>
    </form>
  </section>
</template>

<script>
  import {mapActions, mapMutations} from 'vuex';

  export default {
    data() {
      return {
        username: '',
        password: '',
        email: '',
        showError: false,
        phone: '',
        city: '',
        streetAddress: '',
        image: null,
        previewImage: null,
        formData: new FormData()
      }
    },
    methods: {
      async registration() {
        let success = await this.signUp(
          {
            username: this.username,
            password: this.password,
            email: this.email,
            phone: this.phone,
            city: this.city,
            streetAddress: this.streetAddress,
            formData: this.formData
          });
        this.needShowRegistrationForm(!success);
        this.showError = !success;
      },
      ...mapMutations('login', {
        needShowRegistrationForm: 'needShowRegistrationForm',
      }),
      ...mapActions('login', {
        signUp: 'signUp',
      }),
      Image_onFileChanged(event) {
        this.formData = new FormData();
        this.image = event.target.files[0];
        const reader = new FileReader();
        this.formData.append("image", this.image, this.image.name)
        reader.readAsDataURL(this.image);
        reader.onload = e => {
          this.previewImage = e.target.result;
        };
      },
      deleteImage(){
        this.formData = new FormData();
        this.image = null;
        this. previewImage = null;
      }
    },
    computed: {
      enableRegistration() {
        return this.username.trim() !== '' && this.password.trim() !== '' && this.email.trim() !== ''
          && this.phone.trim() !== '' && this.city.trim() !== '' && this.streetAddress.trim() !== ''
      },
      previewImageClass() {
        return this.previewImage !== null ? "visibility-visible" : "visibility-hidden";
      }
    }
  };
</script>

<style scoped>
  #image-upload {
    opacity: 0;
    position: absolute;
    z-index: -1;
  }

  .delete-button {
    margin-top: -10px;
  }
  .main-section{
    padding-right: 10px;
    margin-right: 10px;
    border-right: 1px solid #DFE1E6;
  }
</style>
