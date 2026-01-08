import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import NovelDetail from '@/views/NovelDetail.vue'
import ReaderView from '@/views/ReaderView.vue'
import ShelfView from '@/views/ShelfView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: HomeView },
    { path: '/novel/:id', component: NovelDetail },
    { path: '/read/:id', component: ReaderView },
    { path: '/shelf', component: ShelfView },
    { path: '/profile', component: () => import('@/views/ProfileView.vue') },
    // { path: '/settings', component: () => import('@/views/SettingsView.vue') },
  ],
})

export default router
