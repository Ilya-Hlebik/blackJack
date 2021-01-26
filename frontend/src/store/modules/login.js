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
    roles: [],
    user:null
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
    },
    user(state){
      return state.user;
    },
    depositSum(state){
      return state.user.userInfo.depositSum;
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
    },
    setUser(state, data){
      state.user = data;
    },
    setUserName(state, e){
      state.user.userInfo.name = e.target.value;
    },
    setPhone(state, e){
      state.user.userInfo.phone = e.target.value;
    },
    setCity(state, e){
      state.user.userInfo.city = e.target.value;
    },
    setStreetAddress(state, e){
      state.user.userInfo.streetAddress = e.target.value;
    },
    setUserInfo(state, data){
      state.user.userInfo = data;
    },
    setDepositSum(state,data){
      state.user.userInfo.depositSum = data;
    },
    updateDepositAmount(state,data){
      return state.user.userInfo.depositSum -= data;
    },
  },
  actions: {
    async signIn(store, data) {
      try {
        const response = await axios.post('/backend/users/signin', data);
        if (response.status === 200) {
          store.commit('updateLoginInfo', true);
          store.commit('updateRoles',response.data.roles);
          store.commit('bets/updateDepositSum', response.data.userInfo.depositSum,{ root: true} )
          store.commit('setUser', response.data)
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
      store.commit('setUser', response.data)
    },
    async signUp(store, data) {
      try {
        data.formData.delete("user");
        data.formData.append('user', new Blob([JSON.stringify({
          username: data.username,
          email: data.email,
          password: data.password,
          phone: data.phone,
          city: data.city,
          streetAddress: data.streetAddress,
          roles: ['ROLE_CLIENT']
        })], {
          type: "application/json"
        }))
        const response = await axios.post('/backend/users/signup', data.formData, {
          headers: {
            "Content-Type": undefined
          }
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
    },
    async saveUserInfo(store) {
      return await axios.patch('/backend/userInfo/update', store.state.user.userInfo).then(response => {
        store.commit('setUserInfo', response.data)
        return response.status === 200;
      }).catch(() => false);
    },
    async saveUserDeposit(store, depositSum) {
      return await axios.post('/backend/userInfo/deposit',  depositSum, {headers: {"Content-Type": "application/json"}}).then(response => {
        store.commit('setDepositSum', response.data)
        return response.status === 200;
      }).catch(() => false);
    }
  }
}
