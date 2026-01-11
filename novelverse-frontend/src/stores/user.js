import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    username: null,
    token: null,
  }),

  actions: {
    login(payload) {
      this.username = payload.name
      this.token = payload.token

      localStorage.setItem('token', payload.token)
      localStorage.setItem('username', payload.name)
    },

    logout() {
      this.username = null
      this.token = null

      localStorage.removeItem('token')
      localStorage.removeItem('username')
    },

    restore() {
      const token = localStorage.getItem('token')
      const username = localStorage.getItem('username')

      if (token && username) {
        this.token = token
        this.username = username
      }
    },
  },
})
