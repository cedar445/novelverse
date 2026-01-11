<template>
  <div class="mask" :class="readerStore.theme" @click.self="close">
    <div class="modal">
      <h2 class="title">NovelVerse</h2>

      <input v-model="username" class="input" placeholder="请输入用户名" />

      <input v-model="password" class="input" type="password" placeholder="请输入密码" />
      <div class="actions">
        <button class="submit primary" @click="submit1">登录</button>
        <button class="submit secondary" @click="submit2">注册</button>
      </div>
      <!-- <div class="hint">首次登录将自动注册</div> -->
      <div v-if="errorMsg" class="error">
        {{ errorMsg }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useReaderStore } from '@/stores/reader'
import { login, register } from '@/api/auth'

const readerStore = useReaderStore()

const emit = defineEmits(['success', 'close'])

const username = ref('')
const password = ref('')
const errorMsg = ref('')

const submit1 = async () => {
  errorMsg.value = ''

  // 前端校验
  if (!username.value) {
    errorMsg.value = '用户名不能为空'
    return
  }

  if (!password.value) {
    errorMsg.value = '密码不能为空'
    return
  }

  // 调用后端
  try {
    const res = await login({
      name: username.value,
      password: password.value,
    })
    // 登录成功
    emit('success', {
      token: res.data,
      name: username.value,
    })
  } catch (e) {
    errorMsg.value = e.data
    console.log(e)
  }
}
const submit2 = async () => {
  errorMsg.value = ''
  // 前端校验
  if (!username.value) {
    errorMsg.value = '用户名不能为空'
    return
  }

  if (!password.value) {
    errorMsg.value = '密码不能为空'
    return
  }

  // 调用后端
  try {
    const res = await register({
      name: username.value,
      password: password.value,
    })
    // 登录成功
    emit('success', {
      token: res.data,
      name: username.value,
    })
  } catch (e) {
    errorMsg.value = e.data
    console.log(e)
  }
}
const close = () => {
  emit('close')
}
</script>

<style scoped>
/* ===============================
   遮罩层
   =============================== */
.mask {
  position: fixed;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  transition: background 0.3s;
}

/* 日间遮罩 */
.mask.light {
  background: rgba(0, 0, 0, 0.35);
}

/* 夜间遮罩 */
.mask.dark {
  background: rgba(0, 0, 0, 0.65);
}

/* ===============================
   弹窗主体
   =============================== */
.modal {
  width: 82%;
  max-width: 340px;
  border-radius: 20px;
  padding: 28px 22px 24px;
  text-align: center;
  animation: popup 0.25s ease;
  transition:
    background 0.3s,
    color 0.3s;
  display: flex;
  flex-direction: column;
}

/* 日间弹窗 */
.mask.light .modal {
  background: #ffffff;
  color: #111827;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

/* 夜间弹窗 */
.mask.dark .modal {
  background: #111827;
  color: #e5e7eb;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.6);
}

@keyframes popup {
  from {
    transform: scale(0.95) translateY(10px);
    opacity: 0;
  }
  to {
    transform: scale(1) translateY(0);
    opacity: 1;
  }
}

/* ===============================
   标题
   =============================== */
.title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 20px;
}

/* ===============================
   输入框
   =============================== */
.input {
  width: 100%;
  padding: 11px 14px;
  border-radius: 10px;
  font-size: 14px;
  margin-bottom: 14px;
  outline: none;
  transition:
    border 0.2s,
    background 0.2s;
}

/* 日间输入框 */
.mask.light .input {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  color: #111827;
}

.mask.light .input:focus {
  border-color: #5f7cff;
}

/* 夜间输入框 */
.mask.dark .input {
  background: #1f2937;
  border: 1px solid #374151;
  color: #e5e7eb;
}

.mask.dark .input:focus {
  border-color: #818cf8;
}

/* =====================================================================
                          提交按钮
   ===================================================================== */

/* ===============================
   按钮行（登录 / 注册）
   =============================== */
.actions {
  display: flex;
  gap: 12px;
  margin-top: 6px;
}

/* 水平排列时按钮等宽 */
.actions .submit {
  flex: 1;
  margin: 0; /* 覆盖原本的上下 margin */
}

.submit {
  width: 100%;
  padding: 11px;
  border-radius: 999px;
  border: none;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;

  /* 交互动画 */
  transition:
    background-color 0.2s ease,
    color 0.2s ease,
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    transform 0.15s ease;

  margin: 10px 0 12px;
}

/* =====================
   基础状态
===================== */
.primary,
.secondary {
  background: transparent;
  border: 1px solid;
}

/* =====================
   Hover：浮起 + 轻填充
===================== */
.submit:hover {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
  transform: translateY(-1px);
}

/* =====================
   Active：按压感
===================== */
.submit:active {
  transform: scale(0.96);
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.12) inset;
}

/* =====================
   Focus：键盘友好
===================== */
.submit:focus-visible {
  outline: none;
  box-shadow: 0 0 0 3px rgba(95, 124, 255, 0.35);
}

/* =====================
   日间模式
===================== */
.mask.light .submit.primary,
.mask.light .submit.secondary {
  border-color: #5f7cff;
  color: #5f7cff;
}

.mask.light .submit:hover {
  background: rgba(95, 124, 255, 0.08);
}

.mask.light .submit:active {
  background: rgba(95, 124, 255, 0.15);
}

/* =====================
   夜间模式
===================== */
.mask.dark .submit.primary,
.mask.dark .submit.secondary {
  border-color: #818cf8;
  color: #818cf8;
}

.mask.dark .submit:hover {
  background: rgba(129, 140, 248, 0.12);
}

.mask.dark .submit:active {
  background: rgba(129, 140, 248, 0.22);
}

/* =====================
   Disabled（如果你后面会用）
===================== */
.submit:disabled {
  opacity: 0.45;
  cursor: not-allowed;
  box-shadow: none;
  transform: none;
}

/* ===============================
   提示文本
   =============================== */
.hint {
  font-size: 12px;
  margin-top: 14px;
  opacity: 0.8;
}

.error {
  margin-top: 12px;
  font-size: 13px;
  color: #ef4444;
}
</style>
