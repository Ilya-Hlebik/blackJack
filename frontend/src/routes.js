import BlackJack from './components/BlackJack';
import Vue from 'vue';
import VueRouter from 'vue-router';
import {store} from './store';
import Menu from './components/Menu';

Vue.use(VueRouter);

const routes = [
  {
    path: '',
    redirect: {name: 'menu'}

  },
  {
    name: 'menu',
    path: '/menu',
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
    path: '/game/:id',
    component: BlackJack,
    async beforeEnter(from, to, next) {
      if (from.params.id !== "") {
        let game = await store.dispatch("main/loadExistingGame", from.params.id);
        if (!game.gameFinished){
          game = await store.dispatch("main/loadNewGame", from.params.id);
        }
        if (game.gameStatus === 'PLAYER_BJ'){
          await store.dispatch('main/dealerTurns', from.params.id);
        }
      }
      next();
    }
  }
];

export const router = new VueRouter({
  routes,
  mode: 'history'
});
