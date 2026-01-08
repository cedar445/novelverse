<template>
  <div class="mask" :class="store.theme" @click.self="close">
    <div class="modal">
      <h2 class="title">登录 NovelVerse</h2>

      <input v-model="username" class="input" placeholder="请输入用户名" />

      <input v-model="password" class="input" type="password" placeholder="请输入密码" />

      <button class="submit" @click="submit">登录</button>

      <div class="hint">首次登录将自动注册</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useReaderStore } from '@/stores/reader'

const store = useReaderStore()

const emit = defineEmits(['success', 'close'])

const username = ref('')
const password = ref('')

const submit = () => {
  if (!username.value) return

  const user = {
    id: Date.now(),
    nickname: username.value,
  }

  emit('success', user)
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

/* ===============================
   提交按钮
   =============================== */
.submit {
  width: 100%;
  padding: 11px;
  border-radius: 999px;
  border: none;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition:
    transform 0.15s,
    opacity 0.15s;
}

.submit:active {
  transform: scale(0.96);
}

/* 日间按钮 */
.mask.light .submit {
  background: linear-gradient(135deg, #5f7cff, #7b8dff);
  color: #ffffff;
}

/* 夜间按钮 */
.mask.dark .submit {
  background: linear-gradient(135deg, #6366f1, #818cf8);
  color: #ffffff;
}

/* ===============================
   提示文本
   =============================== */
.hint {
  font-size: 12px;
  margin-top: 14px;
  opacity: 0.8;
}
</style>
