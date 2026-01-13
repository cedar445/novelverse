<template>
  <div class="home" :class="readerStore.theme">
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
    <div class="list-wrapper">
      <div class="scroll-container" :class="readerStore.theme">
        <!-- 小说列表 -->
        <section class="list">
          <NovelCard v-for="n in filteredNovels" :key="n.id" :novel="n" />
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useReaderStore } from '@/stores/reader'
import NovelCard from '@/components/NovelCard.vue'
import { getBooks } from '@/api/book'
import { getUserById } from '@/api/user'

const readerStore = useReaderStore()

const categories = ['全部', '玄幻', '科幻', '都市', '武侠', '悬疑']
const activeCategory = ref('全部')

const novels = ref([
  { id: 1, name: '星渊纪元', author: 'Aether', tags: ['科幻'], description: '' },
  { id: 2, name: '黑月之下', author: 'Noctis', tags: ['玄幻'], description: '' },
  { id: 3, name: '都市回响', author: 'Echo', tags: ['都市'], description: '' },
  { id: 1, name: '星渊纪元', author: 'Aether', tags: ['科幻'], description: '' },
  { id: 2, name: '黑月之下', author: 'Noctis', tags: ['玄幻'], description: '' },
  { id: 3, name: '都市回响', author: 'Echo', tags: ['都市'], description: '' },
  { id: 1, name: '星渊纪元', author: 'Aether', tags: ['科幻'], description: '' },
  { id: 2, name: '黑月之下', author: 'Noctis', tags: ['玄幻'], description: '' },
  { id: 3, name: '都市回响', author: 'Echo', tags: ['都市'], description: '' },
])

const filteredNovels = computed(() => {
  if (activeCategory.value === '全部') return novels.value
  return novels.value.filter((n) => n.tags === activeCategory.value)
})
onMounted(async () => {
  const books = (await getBooks()).data
  console.log(books)
  for (let book of books) {
    novels.value.push({
      id: book.id,
      name: book.name,
      author: (await getUserById(book.author_id)).data.name,
      tags: book.tags,
      description: book.description,
    })
  }
  console.log(novels.value)
})
</script>

<style scoped>
/* ===============================
   页面整体
   =============================== */
.home {
  display: flex;
  flex-direction: column;
  max-width: 960px;
  margin: 0 auto;
  padding: 24px 16px 40px;
  height: 100vh;
  transition:
    background 0.3s,
    color 0.3s;
  /* padding-bottom: calc(64px + env(safe-area-inset-bottom)); */
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
  z-index: 10;
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
  position: sticky;
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
  flex: 1; /* 占剩余空间 */
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 18px;
  justify-content: center;
  align-items: center;
  justify-items: center; /* 水平居中 */
  margin: 20px 0;
}

.list-wrapper {
  display: flex;
  flex: 1;
  overflow-y: auto;
  padding-right: 8px; /* 滚动条不遮挡内容 */
  background-color: inherit;
  padding-bottom: 20px;
  justify-content: center;
}

@media (max-width: 640px) {
  .list {
    grid-template-columns: 1fr; /* 手机屏幕一行一个 */
  }
}
</style>

<style scoped>
/* ================= 1. 容器基础样式 ================= */
.scroll-container {
  width: 90%;
  height: 95%;
  overflow-y: auto;
  overflow-x: hidden;
  padding-right: 8px;
  box-sizing: content-box;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
  transition: background-color 0.3s;
}

/* ================= 2. 日间模式滚动条 ================= */
.scroll-container.light::-webkit-scrollbar-thumb {
  background-color: rgba(100, 100, 100, 0.5);
}
.scroll-container.light::-webkit-scrollbar-thumb:hover {
  background-color: rgba(100, 100, 100, 0.8);
}
.scroll-container.light::-webkit-scrollbar-track {
  background-color: rgba(200, 200, 200, 0.2);
}

/* Firefox */
.scroll-container.light {
  background-color: #f9f9f9;
  scrollbar-color: rgba(100, 100, 100, 0.5) rgba(200, 200, 200, 0.2);
}

/* ================= 3. 夜间模式滚动条 ================= */
.scroll-container.dark::-webkit-scrollbar-thumb {
  background-color: rgba(200, 200, 200, 0.3);
}
.scroll-container.dark::-webkit-scrollbar-thumb:hover {
  background-color: rgba(200, 200, 200, 0.7);
}
.scroll-container.dark::-webkit-scrollbar-track {
  background-color: rgba(100, 100, 100, 0.2);
}

/* Firefox */
.scroll-container.dark {
  background-color: #0f172a;
  scrollbar-color: rgba(200, 200, 200, 0.3) rgba(100, 100, 100, 0.2);
}
</style>
