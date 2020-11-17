import axios from 'axios';
import {router} from "../../routes";

export default {
  namespaced: true,
  state: {
    isLogged: false,
    needShowLoginFrom: false,
    needShowRegistrationForm: false,
    needShowForgotPasswordForm: false,
    needShowRecoverPasswordForm: false,
    roles: []
  },
  getters: {
    isLogged(state) {
      return state.isLogged;
    },
    needShowLoginFrom(state) {
      return state.needShowLoginFrom;
    },
    needShowRegistrationForm(state) {
      return state.needShowRegistrationForm;
    },
    needShowForgotPasswordForm(state) {
      return state.needShowForgotPasswordForm;
    },
    needShowRecoverPasswordForm(state) {
      return state.needShowRecoverPasswordForm;
    },
    roles(state){
      return state.roles;
    }
  },
  mutations: {
    updateLoginInfo(state, data) {
      state.isLogged = data;
    },
    needShowLoginFrom(state, data) {
      state.needShowLoginFrom = data;
    },
    needShowRegistrationForm(state, data) {
      state.needShowRegistrationForm = data;
    },
    needShowForgotPasswordForm(state, data) {
      state.needShowForgotPasswordForm = data;
    },
    needShowRecoverPasswordForm(state, data) {
      state.needShowRecoverPasswordForm = data;
    },
    updateRoles(state, data){
      state.roles = data;
    }
  },
  actions: {
    async signIn(store, data) {
      try {
        const response = await axios.post('/backend/users/signin', data);
        if (response.status === 200) {
          store.commit('updateLoginInfo', true);
          store.commit('updateRoles',response.data.roles);
        } else if (response.status === 405) {
          alert('You are already logged in');
        } else {
          store.commit('updateLoginInfo', false);
        }
      } catch (error) {
        store.commit('updateLoginInfo', false);
      }
    },
    async logOut(store) {
      try {
        const response = await axios.get('/backend/users/logout');
        if (response.status === 200) {
          store.commit('updateLoginInfo', false);
          store.commit('updateRoles', []);
         router.push('/menu');
        }
      } catch (error) {
        store.commit('updateLoginInfo', false);
        store.commit('updateRoles', []);
      }
    }
    ,
    async checkAuthorization(store) {
      const response = await axios.get('/backend/users/me');
      const responseStatus = response.status === 200;
      store.commit('updateLoginInfo', responseStatus);
    },
    async signUp(store, data) {
      try {
        const response = await axios.post('/backend/users/signup', {
          username: data.username,
          email: data.email,
          password: data.password,
          roles: ['ROLE_CLIENT']
        });
        return response.status === 200;
      } catch (error) {
        return false;
      }
    },
    async sendVerifyLink(store, email) {
      try {
        const response = await axios.get('/backend/mail/forgot_password', {
          params: {
            email: email
          }
        });
        return response.status === 200;
      } catch (error) {
        return false;
      }
    },
    async verifyToken(store, token) {
      try {
        const response = await axios.post('/backend/mail/verify_token', token,
          {headers: {"Content-Type": "text/plain"}});
        return response.data;
      } catch (error) {
        return false;
      }
    },
    async createNewPassword(store, data) {
      try {
        const response = await axios.put('/backend/users/update_pass/without_previous',{
          token: data.token,
          newPassword: data.newPassword,
        });
        return response.data;
      } catch (error) {
        return false;
      }
    }
  }
}
