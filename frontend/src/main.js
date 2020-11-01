import Vue from 'vue';
import VueResource from 'vue-resource';
import BootstrapVue from 'bootstrap-vue/dist/bootstrap-vue.esm';
import App from './App.vue';
import {store} from './store';
import {router} from './routes.js';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import VueFlashMessage from 'vue-flash-message/src';

Vue.use(BootstrapVue);
Vue.use(VueResource);
Vue.use(VueFlashMessage);

require('vue-flash-message/dist/vue-flash-message.min.css');

const vm = new Vue({
  el: '#app',
  store,
  router,
  render: (h) => h(App)
});
export {vm};
