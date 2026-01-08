<template>
  <div class="settings" :class="store.theme">
    <!-- 用户登录区域 -->
    <div class="profile-card">
      <template v-if="user">
        <img class="avatar" src="https://via.placeholder.com/96" />
        <div class="nickname">{{ user.nickname }}</div>
        <button class="logout" @click="logout">退出登录</button>
      </template>

      <template v-else>
        <div class="avatar placeholder">👤</div>
        <div class="tip">登录后同步阅读进度与书架</div>
        <button class="login" @click="openLogin">登录 / 注册</button>
      </template>
    </div>
    <!-- 设置列表 -->
    <div class="setting-list">
      <div class="setting-item">
        <div class="setting-left">
          <span class="setting-title">主题模式</span>
        </div>
        <button class="theme-btn small" @click="toggleTheme">
          <span class="icon">
            {{ store.theme === 'light' ? '🌙' : '☀️' }}
          </span>
          <span class="label">
            {{ store.theme === 'light' ? '夜间' : '日间' }}
          </span>
        </button>
      </div>
      <div class="setting-item">
        <div class="setting-left">
          <span class="setting-title">其他</span>
        </div>
        <button class="theme-btn small" @click="getUser">
          <span class="icon">
            {{ store.theme === 'light' ? '🌙' : '☀️' }}
          </span>
          <span class="label">
            {{ store.theme === 'light' ? '夜间' : '日间' }}
          </span>
        </button>
      </div>
    </div>
  </div>

  <LoginModal v-if="showLogin" @close="showLogin = false" @success="handleLogin" />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useReaderStore } from '@/stores/reader'
import LoginModal from '@/components/LoginModal.vue'
import { test } from '@/api/user'

const store = useReaderStore()

const user = ref(null)
const showLogin = ref(false)

onMounted(() => {
  const saved = localStorage.getItem('user')
  if (saved) {
    user.value = JSON.parse(saved)
  }
})

const openLogin = () => {
  showLogin.value = true
}

const handleLogin = (u) => {
  user.value = u
  localStorage.setItem('user', JSON.stringify(u))
  showLogin.value = false
}

const logout = () => {
  localStorage.removeItem('user')
  user.value = null
}

const toggleTheme = () => {
  const next = store.theme === 'light' ? 'dark' : 'light'
  store.theme = next
  localStorage.setItem('theme', next)
}
//api测试
const getUser = () => {
  test()
}

onMounted(() => {
  // 用户信息
  const savedUser = localStorage.getItem('user')
  if (savedUser) {
    user.value = JSON.parse(savedUser)
  }

  // 主题兜底
  const savedTheme = localStorage.getItem('theme')
  if (savedTheme) {
    store.theme = savedTheme
  }
})
</script>

<style scoped>
/* ===== 页面整体 ===== */
.settings {
  max-width: 960px;
  min-height: 100vh;
  padding: 0 16px 24px;
  transition: background 0.3s;
  margin: 0 auto;
}

/* ===============================
   日间模式
   =============================== */
.settings.light {
  background: #f7f8fa;
}

/* ===============================
   夜间模式
   =============================== */
.settings.dark {
  background: #0f0f0f;
}

/* ===== 顶部登录区域 ===== */
.profile-card {
  border-radius: 0 0 24px 24px;
  padding: 40px 16px 32px;
  text-align: center;
  margin-bottom: 24px;
  transition:
    background 0.3s,
    color 0.3s;
}

/* 日间头部 */
.settings.light .profile-card {
  background: linear-gradient(180deg, #5f7cff, #7b8dff);
  color: #ffffff;
}

/* 夜间头部 */
.settings.dark .profile-card {
  background: linear-gradient(180deg, #1f2937, #111827);
  color: #e5e7eb;
}

/* ===== 头像 ===== */
.avatar {
  width: 96px;
  height: 96px;
  border-radius: 50%;
  margin: 0 auto 12px;
  object-fit: cover;
  background: #ffffff;
}

.avatar.placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 36px;
  background: rgba(255, 255, 255, 0.85);
  color: #9ca3af;
}

/* ===== 文本 ===== */
.nickname {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 12px;
}

.tip {
  font-size: 13px;
  opacity: 0.9;
  margin-bottom: 16px;
}

/* ===== 按钮 ===== */
.login,
.logout {
  padding: 8px 26px;
  border-radius: 999px;
  font-size: 14px;
  border: none;
  cursor: pointer;
  transition:
    transform 0.15s,
    opacity 0.15s;
}

.login:active,
.logout:active {
  transform: scale(0.94);
}

/* 日间按钮 */
.settings.light .login {
  background: #ffffff;
  color: #5f7cff;
  font-weight: 600;
}

.settings.light .logout {
  background: rgba(255, 255, 255, 0.25);
  color: #ffffff;
}

/* 夜间按钮 */
.settings.dark .login {
  background: #1f2937;
  color: #c7d2fe;
}

.settings.dark .logout {
  background: rgba(255, 255, 255, 0.12);
  color: #e5e7eb;
}

/* ===== 主题切换 ===== */
.theme-switch {
  display: flex;
  justify-content: center;
  margin-top: 12px;
}

.theme-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 22px;
  border-radius: 999px;
  font-size: 14px;
  border: none;
  cursor: pointer;
  transition:
    background 0.25s,
    color 0.25s,
    transform 0.15s;
}

.theme-btn:active {
  transform: scale(0.95);
}

/* 日间 */
.settings.light .theme-btn {
  background: #ffffff;
  color: #4f46e5;
  box-shadow: 0 6px 20px rgba(79, 70, 229, 0.15);
}

/* 夜间 */
.settings.dark .theme-btn {
  background: #1f2937;
  color: #e0e7ff;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.5);
}

.icon {
  font-size: 16px;
}

.label {
  font-weight: 500;
}

/* ===== 设置列表 ===== */
.setting-list {
  display: flex;
  border-radius: 14px;
  flex-direction: column;
  overflow: hidden;
  margin: 0 8px;
  transition: background 0.3s;
  gap: 12px; /* 项之间的间距 */
}

.setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 16px;
  border-radius: 14px;
  transition:
    background 0.25s,
    box-shadow 0.25s,
    transform 0.15s;
}

/* 日间 */
.settings.light .setting-list {
  background: #ffffff;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.06);
}

/* 夜间 */
.settings.dark .setting-list {
  background: #111827;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.6);
}

/* 日间模式 */
.settings.light .setting-item {
  background: #ffffff;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
}

/* 夜间模式 */
.settings.dark .setting-item {
  background: #111827;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.6);
}

.setting-title {
  font-size: 15px;
  font-weight: 500;
}

/* 日间文字 */
.settings.light .setting-title {
  color: #111827;
}

/* 夜间文字 */
.settings.dark .setting-title {
  color: #e5e7eb;
}

.theme-btn.small {
  padding: 6px 14px;
  font-size: 13px;
  gap: 6px;
}

/* 日间 */
.settings.light .theme-btn.small {
  background: #eef2ff;
  color: #4f46e5;
}

/* 夜间 */
.settings.dark .theme-btn.small {
  background: #1f2937;
  color: #c7d2fe;
}
</style>
