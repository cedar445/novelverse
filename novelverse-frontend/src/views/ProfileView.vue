<template>
  <div class="settings" :class="readerStore.theme">
    <!-- 用户登录区域 -->
    <div class="profile-card">
      <template v-if="userStore.username">
        <div class="avatar placeholder">👤</div>
        <div class="name">{{ userStore.username }}</div>
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
        <button class="normal-btn" :class="readerStore.theme" @click="toggleTheme">
          <span class="icon">
            {{ readerStore.theme === 'light' ? ' 🌙 ' : ' ☀️ ' }}
          </span>
          <span class="label">
            {{ readerStore.theme === 'light' ? ' 夜间模式 ' : ' 日间模式 ' }}
          </span>
        </button>
      </div>
      <div class="setting-item">
        <div class="setting-left">
          <span class="setting-title">api调试</span>
        </div>
        <button class="normal-btn" :class="readerStore.theme" @click="testBooks">
          <span class="icon"> ☆ </span>
          <span class="label"> 操作按钮 </span>
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
// import { getUsers } from '@/api/user'
// import { login } from '@/api/auth'
import { useUserStore } from '@/stores/user'
import { getBookById } from '@/api/book'

const userStore = useUserStore()
const readerStore = useReaderStore()

const showLogin = ref(false)

const openLogin = () => {
  showLogin.value = true
}

const handleLogin = (payload) => {
  userStore.login(payload)
  showLogin.value = false
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  userStore.logout()
}

const toggleTheme = () => {
  const next = readerStore.theme === 'light' ? 'dark' : 'light'
  readerStore.theme = next
  localStorage.setItem('theme', next)
}

//api测试

// const getMyUsers = async () => {
//   try {
//     const res = await getUsers()
//     console.log(res)
//   } catch (err) {
//     console.log(err)
//   }
// }
const testBooks = async () => {
  try {
    const res = await getBookById(11)
    console.log(res)
  } catch (err) {
    console.error(err)
  }
}

/* ===============================
   mount
   =============================== */
onMounted(() => {
  // 用户信息
  // const savedUser = localStorage.getItem('username')
  // if (savedUser) {
  //   user.value = JSON.parse(savedUser)
  // }

  // 主题兜底
  const savedTheme = localStorage.getItem('theme')
  if (savedTheme) {
    readerStore.theme = savedTheme
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
/* =============================== */
/* ===== 头像 ===== */
/* =============================== */
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
.name {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 12px;
}

.tip {
  font-size: 13px;
  opacity: 0.9;
  margin-bottom: 16px;
}
/* =============================== */
/* ===== login按钮 ===== */
/* =============================== */
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

/* 日间login */
.settings.light .login {
  background: #ffffff;
  color: #5f7cff;
  font-weight: 600;
}

.settings.light .logout {
  background: rgba(255, 255, 255, 0.25);
  color: #ffffff;
}
/* 夜间login */
.settings.dark .login {
  background: #1f2937;
  color: #c7d2fe;
}

.settings.dark .logout {
  background: rgba(255, 255, 255, 0.12);
  color: #e5e7eb;
}
/* =============================== */
/* ===== 主题切换 ===== */
/* =============================== */
.theme-switch {
  display: flex;
  justify-content: center;
  margin-top: 12px;
}
/*
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
} */

/* 日间 */
/* .settings.light .theme-btn {
  background: #ffffff;
  color: #4f46e5;
  box-shadow: 0 6px 20px rgba(79, 70, 229, 0.15);
}

/* 夜间 */
/* .settings.dark .theme-btn {
  background: #1f2937;
  color: #e0e7ff;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.5);
}
*/
/* 日间 */
/* .settings.light .theme-btn.small {
  background: #eef2ff;
  color: #4f46e5;
} */

/* 夜间 */
/* .settings.dark .theme-btn.small {
  background: #1f2937;
  color: #c7d2fe;
} */

/* .theme-btn.small {
  padding: 6px 14px;
  font-size: 13px;
  gap: 6px;
} */

/* .icon {
  font-size: 16px;
}

.label {
  font-weight: 500;
} */

/* =============================== */
/* ===== 设置列表 ===== */
/* =============================== */
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
/* =============================== */
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
/* =============================== */
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

/* =============================== */
/* 日间文字 */
.settings.light .setting-title {
  color: #111827;
}

/* 夜间文字 */
.settings.dark .setting-title {
  color: #e5e7eb;
}

.setting-title {
  font-size: 15px;
  font-weight: 500;
}

/* =============================== */
/* 按钮 */
/* =============================== */
.normal-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;

  padding: 10px 14px;
  border-radius: 10px;

  font-size: 14px;
  font-weight: 500;

  cursor: pointer;
  user-select: none;

  border: 1px solid transparent;
  background-clip: padding-box;

  transition:
    background-color 0.2s ease,
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    transform 0.15s ease;

  min-width: 125px;
}

/* ---------- icon ---------- */
.normal-btn .icon {
  font-size: 16px;
  line-height: 1;
  opacity: 0.85;
}

/* ---------- label ---------- */
.normal-btn .label {
  white-space: nowrap;
}

/* ================= 日间模式 ================= */
.normal-btn.light {
  color: #1f2937;
  background: linear-gradient(180deg, #ffffff, #f9fafb);
  border-color: #e5e7eb;

  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.04);
}

.normal-btn.light:hover {
  background: #ffffff;
  border-color: #d1d5db;

  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);

  transform: translateY(-1px);
}

.normal-btn.light:active {
  transform: translateY(0);
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
}

.normal-btn.light:focus-visible {
  outline: none;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.4);
}

/* ================= 夜间模式 ================= */
.normal-btn.dark {
  color: #e5e7eb;
  background: linear-gradient(180deg, #1f2937, #111827);
  border-color: #374151;

  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
}

.normal-btn.dark:hover {
  background: #1f2937;
  border-color: #4b5563;

  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.55);

  transform: translateY(-1px);
}

.normal-btn.dark:active {
  transform: translateY(0);
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.45);
}

.normal-btn.dark:focus-visible {
  outline: none;
  box-shadow: 0 0 0 2px rgba(96, 165, 250, 0.5);
}
/* 禁用态 */
.normal-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}
</style>
