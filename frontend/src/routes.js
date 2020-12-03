import BlackJack from './components/BlackJack';
import Vue from 'vue';
import VueRouter from 'vue-router';
import {store} from './store';
import Menu from './components/Menu';
import Account from './components/Account';

Vue.use(VueRouter);

const routes = [

  {
    name: 'menu',
    path: '/menu',
    async beforeEnter(from, to, next) {
      store.commit('main/setStartNewGameClicked',false);
      store.commit('main/clearPlayerCards');
      store.commit('main/clearDealerCards');
      next();
    },
    component: Menu,
  },
  {
    path: '/game',
    async beforeEnter(from, to, next) {
      let gameId = await store.dispatch('main/loadGame');
      if (gameId !== null) {
        next('/game/' + gameId);
      }
    }
  },
  {
    name: 'accountUpdate',
    path: '/account/update',
    component: Account,
    props: {showEditForm: true}
  },
  {
    name: 'account',
    path: '/account',
    component: Account,
    props: {showEditForm: false}
  },
  {
    path: '/game/:id',
    component: BlackJack,
  },
  {
    path: '*',
    redirect: {name: 'menu'}

  },
];

export const router = new VueRouter({
  routes,
  mode: 'history'
});
