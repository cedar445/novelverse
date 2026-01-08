<template>
  <div class="detail" :class="store.theme">
    <!-- 顶部信息 -->
    <section class="header">
      <!-- 封面 -->
      <img class="cover" src="https://via.placeholder.com/180x240" alt="cover" />

      <!-- 右侧信息 -->
      <div class="info">
        <h1 class="title">绝夜</h1>

        <div class="sub">
          <span class="author">吹之美</span>
          <span class="time">· 2026-01-05 20:47</span>
        </div>

        <!-- 标签 -->
        <div class="tags">
          <span class="tag">玄幻</span>
          <span class="tag">权谋</span>
          <span class="tag">探秘</span>
          <span class="tag">成长</span>
        </div>

        <!-- 统计 -->
        <div class="stats">
          <div class="stat">
            <strong>2</strong>
            <span>章节</span>
          </div>
          <div class="stat">
            <strong>0.4万</strong>
            <span>字数</span>
          </div>
        </div>

        <!-- 操作 -->
        <div class="actions">
          <button class="primary" @click="read">开始阅读</button>
          <button class="secondary">加入书架</button>
        </div>
      </div>
    </section>

    <!-- 简介 -->
    <section class="intro">
      <h2>作品简介</h2>
      <p>
        天才被废，修为尽失；世人讥笑日暮，我独饮月华。 当我从坠落中爬出，这虚假的永恒之夜，该醒了。
      </p>
    </section>

    <!-- 目录 -->
    <section class="catalog">
      <h2>目录</h2>

      <div class="chapter">
        <span class="name">第1章 茶香</span>
        <span class="meta">2.4k字 · 2026-01-04</span>
      </div>

      <div class="chapter">
        <span class="name">第2章 枯冢寒夜</span>
        <span class="meta">1.9k字 · 2026-01-05</span>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useReaderStore } from '@/stores/reader'

const store = useReaderStore()

const route = useRoute()
const router = useRouter()

const read = () => {
  router.push(`/read/${route.params.id}`)
}
</script>

<style scoped>
/* =========================
   页面基础
========================= */

.detail {
  max-width: 1100px;
  margin: 0 auto;
  padding: 32px 16px 64px;
  transition:
    background 0.3s,
    color 0.3s;
}

/* =========================
   顶部信息
========================= */

.header {
  display: flex;
  gap: 32px;
  margin-bottom: 40px;
}

/* 封面 */
.cover {
  width: 180px;
  height: 240px;
  border-radius: 14px;
  object-fit: cover;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.18);
}

/* 信息区 */
.info {
  flex: 1;
}

.title {
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 6px;
}

.sub {
  font-size: 14px;
  opacity: 0.75;
  margin-bottom: 14px;
}

/* 标签 */
.tags {
  display: flex;
  gap: 8px;
  margin-bottom: 18px;
}

.tag {
  padding: 4px 14px;
  border-radius: 999px;
  font-size: 13px;
  font-weight: 500;
}

/* 统计 */
.stats {
  display: flex;
  gap: 24px;
  margin-bottom: 22px;
}

.stat {
  border-radius: 12px;
  padding: 12px 26px;
  text-align: center;
}

.stat strong {
  display: block;
  font-size: 22px;
}

.stat span {
  font-size: 13px;
  opacity: 0.75;
}

/* 操作按钮 */
.actions {
  display: flex;
  gap: 16px;
}

button {
  padding: 12px 36px;
  border-radius: 999px;
  font-size: 15px;
  border: none;
  cursor: pointer;
  transition: all 0.2s;
}

.primary {
  font-weight: 600;
}

.secondary {
  background: transparent;
}

/* =========================
   简介 & 目录
========================= */

.intro,
.catalog {
  margin-top: 40px;
}

.intro h2,
.catalog h2 {
  font-size: 18px;
  margin-bottom: 12px;
}

.intro p {
  font-size: 15px;
  line-height: 1.9;
}

/* 章节 */
.chapter {
  display: flex;
  justify-content: space-between;
  padding: 14px 0;
  font-size: 14px;
  cursor: pointer;
  transition: background 0.2s;
}

.chapter:hover {
  opacity: 0.9;
}

/* =========================
   🌞 日间模式
========================= */

.detail.light {
  background: #ffffff;
  color: #111;
}

.light .tag {
  background: #fff2f0;
  color: #ff4d4f;
}

.light .stat {
  background: #fafafa;
}

.light .stat strong {
  color: #ff4d4f;
}

.light .primary {
  background: #ff4d4f;
  color: #fff;
}

.light .secondary {
  color: #ff4d4f;
  border: 1px solid #ff4d4f;
}

.light .intro p {
  color: #444;
}

.light .chapter {
  border-bottom: 1px solid #eee;
}

.light .chapter .meta {
  color: #999;
}

/* =========================
   🌙 夜间模式
========================= */

.detail.dark {
  background: linear-gradient(180deg, #0b1220, #0a0f1c);
  color: #e5e7eb;
}

.dark .sub {
  color: #9ca3af;
}

.dark .tag {
  background: rgba(96, 165, 250, 0.15);
  color: #93c5fd;
}

.dark .stat {
  background: rgba(255, 255, 255, 0.04);
}

.dark .stat strong {
  color: #93c5fd;
}

.dark .primary {
  background: linear-gradient(135deg, #5f7cff, #7b8dff);
  color: #fff;
}

.dark .secondary {
  color: #93c5fd;
  border: 1px solid rgba(147, 197, 253, 0.4);
}

.dark .intro p {
  color: #cbd5f5;
}

.dark .chapter {
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.dark .chapter .meta {
  color: #9ca3af;
}
</style>
