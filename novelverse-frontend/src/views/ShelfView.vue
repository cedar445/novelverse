<template>
  <div :class="['shelf', readerStore.theme]">
    <!-- 顶部标题 -->
    <header class="shelf-header">
      <h1>我的书架</h1>
      <p class="subtitle">继续你的阅读旅程</p>
    </header>

    <!-- 空状态 -->
    <div v-if="books.length === 0" class="empty">
      <span class="icon">📚</span>
      <p>书架还是空的</p>
    </div>

    <!-- 书架列表 -->
    <section v-else class="grid">
      <div v-for="b in books" :key="b.id" class="book-card" @click="openBook(b)">
        <div class="cover">
          <span class="placeholder">{{ b.name[0] }}</span>
        </div>

        <div class="info">
          <h3 class="title">{{ b.name }}</h3>
          <p class="author">{{ b.author }}</p>
          <p class="description">{{ b.description }}</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useReaderStore } from '@/stores/reader'
// import { useUserStore } from '@/stores/user'
import { getShelf } from '@/api/shelf'
import { getBookById } from '@/api/book'
import { useNovelStore } from '@/stores/currentNovel'
import { useRouter } from 'vue-router'

const readerStore = useReaderStore()
const novelStore = useNovelStore()
// const userStore = useUserStore()
const books = ref([])

const router = useRouter()

const getUserShelf = async () => {
  try {
    // console.log('获取书架中...')

    // 1. 获取书架 id 数组
    const res = await getShelf() // 返回 [1,2,3]
    const shelfIds = res.data
    // console.log('书籍id', shelfIds)

    // 2. 并行查询所有书籍
    const booksData = await Promise.all(shelfIds.map((id) => getBookById(id)))
    // console.log('书籍详情', booksData)

    // ✅ 赋值给 books.value
    books.value = booksData

    return books
  } catch (err) {
    console.error('获取书架或书籍失败', err)
  }
}

const openBook = (book) => {
  novelStore.setNovel(book)
  console.log('打开书籍:', book)
  router.push(`/novel/${book.id}`)
}

onMounted(async () => {
  // 模拟获取书架数据
  await getUserShelf()
})
</script>

<style scoped>
/* =========================
   页面基础
========================= */
.shelf {
  max-width: 960px;
  margin: 0 auto;
  padding: 24px 16px 80px;
  min-height: 100vh;
  transition:
    background 0.3s,
    color 0.3s;
}

/* 日 / 夜主题 */
.shelf.light {
  background: #f7f8fa;
  color: #111;
}

.shelf.dark {
  background: linear-gradient(180deg, #0b1220, #070b14);
  color: #e5e7eb;
}

/* =========================
   Header
========================= */
.shelf-header {
  text-align: center;
  margin-bottom: 32px;
}

.shelf-header h1 {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 6px;
}

.subtitle {
  font-size: 14px;
  opacity: 0.7;
}

/* =========================
   空状态
========================= */
.empty {
  margin-top: 80px;
  text-align: center;
  opacity: 0.7;
}

.empty .icon {
  font-size: 48px;
  display: block;
  margin-bottom: 12px;
}

/* =========================
   书架网格
========================= */
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 20px;
}

/* =========================
   书籍卡片
========================= */
.book-card {
  border-radius: 16px;
  padding: 14px;
  cursor: pointer;
  transition:
    transform 0.2s,
    box-shadow 0.2s;
}

/* 日间卡片 */
.shelf.light .book-card {
  background: #fff;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
}

/* 夜间卡片 */
.shelf.dark .book-card {
  background: rgba(255, 255, 255, 0.06);
  backdrop-filter: blur(6px);
}

/* hover */
.book-card:hover {
  transform: translateY(-4px);
}

/* =========================
   封面
========================= */
.cover {
  height: 180px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
}

.shelf.light .cover {
  background: linear-gradient(135deg, #e0e7ff, #c7d2fe);
}

.shelf.dark .cover {
  background: linear-gradient(135deg, #1e293b, #0f172a);
}

.placeholder {
  font-size: 36px;
  font-weight: 700;
  opacity: 0.8;
}

/* =========================
   信息
========================= */
.info {
  text-align: left;
}

.title {
  font-size: 15px;
  font-weight: 600;
  margin-bottom: 4px;
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 超出隐藏 */
  text-overflow: ellipsis; /* 显示省略号 */
}

.author {
  font-size: 13px;
  opacity: 0.7;
  margin-bottom: 6px;
}

.description {
  font-size: 12px;
  opacity: 0.6;
}
</style>
