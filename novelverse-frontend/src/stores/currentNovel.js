import { defineStore } from 'pinia'

const NOVEL_STORAGE_KEY = 'current_novel'

export const useNovelStore = defineStore('currentNovel', {
  state: () => ({
    id: null,
    name: '',
    author: '',
    tags: [],
    description: '',
    file_path: '',
  }),

  actions: {
    setNovel(book) {
      const data = {
        id: book.id,
        name: book.name,
        author: book.author,
        tags: book.tags ?? [],
        description: book.description ?? '',
        file_path: book.file_path ?? '',
      }

      Object.assign(this, data)
      localStorage.setItem(NOVEL_STORAGE_KEY, JSON.stringify(data))
    },

    restoreNovel() {
      const raw = localStorage.getItem(NOVEL_STORAGE_KEY)
      if (!raw) return

      try {
        Object.assign(this, JSON.parse(raw))
      } catch (e) {
        console.warn('restoreNovel failed:', e)
        localStorage.removeItem(NOVEL_STORAGE_KEY)
      }
    },

    clearNovel() {
      this.$reset()
      localStorage.removeItem(NOVEL_STORAGE_KEY)
    },
  },
})
