<template>
  <div class="mask" @click.self="close">
    <div class="modal" :class="readerStore.theme">
      <!-- 标题 -->
      <h2 class="title">上传本地小说</h2>

      <!-- 简介 -->
      <textarea v-model="description" class="input textarea" placeholder="请输入小说简介" />

      <!-- 标签输入 -->
      <div class="tag-input">
        <input
          v-model="tagInput"
          class="input"
          placeholder="输入标签，回车添加（如：玄幻）"
          @keydown.enter.prevent="addTag"
        />
        <span class="hint">Enter</span>
      </div>

      <!-- 标签列表 -->
      <transition-group name="tag" tag="div" class="tags">
        <span class="tag" v-for="(t, i) in tags" :key="t">
          {{ t }}
          <button @click="removeTag(i)">×</button>
        </span>
      </transition-group>

      <!-- 文件选择 -->
      <label class="file-box" :class="{ active: file }">
        <input type="file" accept=".txt" @change="handleFile" />
        <span v-if="!file">选择小说 txt 文件</span>
        <span v-else>{{ file.name }}</span>
      </label>

      <!-- 错误 -->
      <p v-if="error" class="error">{{ error }}</p>

      <!-- 操作 -->
      <div class="actions">
        <button class="primary" :disabled="loading" @click="submit">
          {{ loading ? '上传中...' : '确认上传' }}
        </button>
        <button class="secondary" @click="close">取消</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useReaderStore } from '@/stores/reader'
import { uploadLocalTxt } from '@/api/book'
import { getUserByName, updateUser } from '@/api/user'
import { useUserStore } from '@/stores/user'

const emit = defineEmits(['close', 'success'])
const readerStore = useReaderStore()
const userStore = useUserStore()

const description = ref('')
const tagInput = ref('')
const tags = ref([])
const file = ref(null)
const loading = ref(false)
const error = ref('')

const close = () => emit('close')

const addTag = () => {
  const val = tagInput.value.trim()
  if (!val) return
  if (tags.value.includes(val)) {
    error.value = '标签已存在'
    return
  }
  tags.value.push(val)
  tagInput.value = ''
  error.value = ''
}

const removeTag = (i) => tags.value.splice(i, 1)

const handleFile = (e) => {
  file.value = e.target.files[0]
  error.value = ''
}

const submit = async () => {
  if (!file.value) {
    error.value = '请选择 txt 文件'
    return
  }

  const form = new FormData()
  form.append('file', file.value)
  form.append('description', description.value)
  tags.value.forEach((t) => form.append('tags', t))

  try {
    loading.value = true

    const newBookId = (await uploadLocalTxt(form)).data
    //加入书架
    const user = (await getUserByName(userStore.username)).data
    console.log(user)
    user.shelf.push(newBookId)
    await updateUser(user)

    emit('success')
    close()
  } catch {
    error.value = '上传失败，请重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
/* ===============================
   遮罩
   =============================== */
.mask {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

/* ===============================
   Modal 容器
   =============================== */
.modal {
  width: 420px;
  padding: 24px;
  border-radius: 20px;
  animation: pop 0.25s ease;
}

/* 日间 */
.modal.light {
  background: #ffffff;
  color: #111827;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

/* 夜间 */
.modal.dark {
  background: #111827;
  color: #e5e7eb;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.6);
}

@keyframes pop {
  from {
    transform: scale(0.95);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

/* ===============================
   标题
   =============================== */
.title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 16px;
}

/* ===============================
   输入框
   =============================== */
.input {
  width: 100%;
  padding: 10px 12px;
  border-radius: 10px;
  font-size: 14px;
  margin-bottom: 12px;
  outline: none;
  transition:
    border-color 0.2s,
    box-shadow 0.2s;
}

.modal.light .input {
  border: 1px solid #d1d5db;
  background: #ffffff;
  color: #111827;
}

.modal.dark .input {
  border: 1px solid #374151;
  background: #1f2937;
  color: #e5e7eb;
}

.input:focus {
  border-color: #6366f1;
  box-shadow: 0 0 0 2px rgba(99, 102, 241, 0.3);
}

.textarea {
  min-height: 90px;
  resize: vertical;
}

/* ===============================
   标签
   =============================== */
.tag-input {
  position: relative;
}

.hint {
  position: absolute;
  right: 10px;
  top: 12px;
  font-size: 12px;
  opacity: 0.5;
}

.tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  margin-bottom: 12px;
}

.tag {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 13px;
}

.modal.light .tag {
  background: #eef2ff;
  color: #4f46e5;
}

.modal.dark .tag {
  background: #1f2937;
  color: #c7d2fe;
}

.tag button {
  background: none;
  border: none;
  cursor: pointer;
  opacity: 0.6;
}

.tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  margin-bottom: 24px; /* 原来 12px → 拉开与文件选择的距离 */
}

/* 动画 */
.tag-enter-active,
.tag-leave-active {
  transition: all 0.2s;
}
.tag-enter-from,
.tag-leave-to {
  transform: scale(0.85);
  opacity: 0;
}

/* ===============================
   文件选择
   =============================== */
.file-box {
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */

  height: 56px;

  padding: 12px;
  border-radius: 14px;
  border: 2px dashed;
  text-align: center;
  cursor: pointer;
  margin-bottom: 20px;
  transition: all 0.2s;
  /* 关键：限制为单行 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-box span {
  display: inline-block;
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.modal.dark .file-box {
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.35);
}

.modal.light .file-box {
  border-color: #d1d5db;
  color: #374151;
}

.modal.dark .file-box {
  border-color: #4b5563;
  color: #d1d5db;
}

.file-box.active {
  border-color: #6366f1;
}

.file-box input {
  display: none;
}

/* ===============================
   错误
   =============================== */
.error {
  font-size: 13px;
  color: #ef4444;
  margin-bottom: 8px;
}

/* ===============================
   操作按钮
   =============================== */
.actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.primary,
.secondary {
  padding: 8px 18px;
  border-radius: 999px;
  font-size: 14px;
  cursor: pointer;
  border: none;
}

.primary {
  background: #6366f1;
  color: #ffffff;
}

.primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.secondary {
  background: transparent;
  color: inherit;
}

.primary {
  background: linear-gradient(180deg, #6366f1, #4f46e5);
  color: #ffffff;
  box-shadow: 0 6px 20px rgba(79, 70, 229, 0.35);
  transition:
    transform 0.15s ease,
    box-shadow 0.15s ease,
    filter 0.15s ease;
}

.primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 10px 28px rgba(79, 70, 229, 0.45);
  filter: brightness(1.05);
}

.primary:active {
  transform: translateY(0);
  box-shadow: 0 4px 14px rgba(79, 70, 229, 0.35);
}

.primary:disabled {
  opacity: 0.55;
  box-shadow: none;
  transform: none;
}

.secondary {
  background: transparent;
  color: inherit;
  border: 1px solid;
  transition:
    background 0.15s ease,
    transform 0.15s ease,
    opacity 0.15s ease;
}

.modal.light .secondary {
  border-color: #d1d5db;
}

.modal.dark .secondary {
  border-color: #374151;
}

.secondary:hover {
  background: rgba(99, 102, 241, 0.08);
}

.secondary:active {
  transform: scale(0.96);
  opacity: 0.85;
}
</style>
