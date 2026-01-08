import { defineStore } from 'pinia'

export const useReaderStore = defineStore('reader', {
  state: () => ({
    fontSize: Number(localStorage.getItem('fontSize')) || 18,
    theme: localStorage.getItem('theme') || 'light',
    chapterIndex: 0,
  }),
  actions: {
    increaseFont() {
      this.fontSize += 2
      localStorage.setItem('fontSize', this.fontSize)
    },
    decreaseFont() {
      this.fontSize -= 2
      localStorage.setItem('fontSize', this.fontSize)
    },
    toggleTheme() {
      this.theme = this.theme === 'light' ? 'dark' : 'light'
      localStorage.setItem('theme', this.theme)
    },
  },
})
