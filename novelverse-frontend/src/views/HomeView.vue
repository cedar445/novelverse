<template>
  <div class="home" :class="store.theme">
    <!-- 顶部 -->
    <header class="header">
      <h1 class="title">NovelVerse 书城</h1>
      <input class="search" type="text" placeholder="搜索小说 / 作者" />
    </header>

    <!-- 分类导航 -->
    <nav class="categories">
      <span
        v-for="c in categories"
        :key="c"
        :class="['category', { active: c === activeCategory }]"
        @click="activeCategory = c"
      >
        {{ c }}
      </span>
    </nav>

    <!-- 小说列表 -->
    <section class="list">
      <NovelCard v-for="n in filteredNovels" :key="n.id" :novel="n" />
    </section>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useReaderStore } from '@/stores/reader'
import NovelCard from '@/components/NovelCard.vue'

const store = useReaderStore()

const categories = ['全部', '玄幻', '科幻', '都市', '武侠', '悬疑']
const activeCategory = ref('全部')

const novels = [
  { id: 1, title: '星渊纪元', author: 'Aether', category: '科幻' },
  { id: 2, title: '黑月之下', author: 'Noctis', category: '玄幻' },
  { id: 3, title: '都市回响', author: 'Echo', category: '都市' },
]

const filteredNovels = computed(() => {
  if (activeCategory.value === '全部') return novels
  return novels.filter((n) => n.category === activeCategory.value)
})
</script>

<style scoped>
/* ===============================
   页面整体
   =============================== */
.home {
  max-width: 960px;
  margin: 0 auto;
  padding: 24px 16px 40px;
  min-height: 100vh;
  transition:
    background 0.3s,
    color 0.3s;
}

/* 日 / 夜背景 */
.home.light {
  background: #f9fafb;
  color: #111827;
}

.home.dark {
  background: #0f172a;
  color: #e5e7eb;
}

/* ===============================
   顶部区域
   =============================== */
.header {
  margin-bottom: 28px;
}

.title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 14px;
}

/* 搜索框 */
.search {
  width: 100%;
  padding: 12px 16px;
  border-radius: 12px;
  font-size: 14px;
  outline: none;
  transition:
    border 0.2s,
    background 0.2s;
}

/* 日间搜索框 */
.home.light .search {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  color: #111827;
}

.home.light .search:focus {
  border-color: #5f7cff;
}

/* 夜间搜索框 */
.home.dark .search {
  background: #1e293b;
  border: 1px solid #334155;
  color: #e5e7eb;
}

.home.dark .search:focus {
  border-color: #818cf8;
}

/* ===============================
   分类导航
   =============================== */
.categories {
  display: flex;
  gap: 12px;
  margin-bottom: 28px;
  overflow-x: auto;
  padding-bottom: 4px;
  display: flex;
  justify-content: center; /* 水平 */
  align-items: center;
}

.category {
  padding: 7px 16px;
  border-radius: 999px;
  font-size: 14px;
  cursor: pointer;
  white-space: nowrap;
  transition: all 0.2s ease;
}

/* 日间分类 */
.home.light .category {
  background: #eef2ff;
  color: #4338ca;
}

.home.light .category:hover {
  background: #e0e7ff;
}

/* 夜间分类 */
.home.dark .category {
  background: #1e293b;
  color: #c7d2fe;
}

.home.dark .category:hover {
  background: #334155;
}

/* 选中状态 */
.category.active {
  background: linear-gradient(135deg, #5f7cff, #7b8dff) !important;
  color: #ffffff !important;
}

/* ===============================
   小说列表
   =============================== */
.list {
  display: flex;
  flex-direction: column;
  gap: 18px;
  display: flex;
  justify-content: center; /* 水平 */
  align-items: center;
}
</style>
