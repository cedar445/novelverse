<template>
  <nav class="bottom-nav" :class="store.theme">
    <div
      v-for="item in navs"
      :key="item.path"
      :class="['nav-item', { active: route.path === item.path }]"
      @click="go(item.path)"
    >
      <span class="icon">{{ item.icon }}</span>
      <span class="label">{{ item.label }}</span>
    </div>
  </nav>
</template>
<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useReaderStore } from '@/stores/reader'

const router = useRouter()
const route = useRoute()
const store = useReaderStore()

const navs = [
  { label: '书城', path: '/', icon: '📚' },
  { label: '书架', path: '/shelf', icon: '📖' },
  { label: '我的', path: '/profile', icon: '👤' },
]

const go = (path) => {
  if (route.path !== path) {
    router.push(path)
  }
}
</script>
<style scoped>
/* ===== 底部导航整体 ===== */
.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 12px;
  right: 12px;
  height: 64px;

  display: flex;
  justify-content: space-around;
  align-items: center;

  border-radius: 20px 20px 0 0;
  padding-bottom: env(safe-area-inset-bottom);
  z-index: 100;

  transition:
    background 0.3s,
    box-shadow 0.3s;
}

/* ===== 单个导航项 ===== */
.nav-item {
  position: relative;
  flex: 1;
  height: 100%;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  font-size: 12px;
  cursor: pointer;
  transition: all 0.25s ease;
}

.nav-item:active {
  transform: scale(0.92);
}

.icon {
  font-size: 20px;
  margin-bottom: 4px;
  transition: transform 0.25s;
}

.label {
  font-size: 11px;
}

/* ===== Active 状态 ===== */
.nav-item.active .icon {
  transform: translateY(-2px);
}

.nav-item.active::after {
  content: '';
  position: absolute;
  bottom: 6px;
  width: 24px;
  height: 3px;
  border-radius: 999px;
}

/* ===============================
   日间模式（store.theme === 'light'）
   =============================== */
.bottom-nav.light {
  background: #ffffff;
  box-shadow: 0 -6px 20px rgba(0, 0, 0, 0.06);
}

.bottom-nav.light .nav-item {
  color: #9ca3af;
}

.bottom-nav.light .nav-item.active {
  color: #5f7cff;
}

.bottom-nav.light .nav-item.active::after {
  background: #5f7cff;
}

/* ===============================
   夜间模式（store.theme === 'dark'）
   =============================== */
.bottom-nav.dark {
  background: #121212;
  box-shadow: 0 -6px 20px rgba(0, 0, 0, 0.45);
}

.bottom-nav.dark .nav-item {
  color: #6b7280;
}

.bottom-nav.dark .nav-item.active {
  color: #8fa2ff;
}

.bottom-nav.dark .nav-item.active::after {
  background: #8fa2ff;
}
</style>
