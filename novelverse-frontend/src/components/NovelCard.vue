<template>
  <div class="card" @click="openBook(props.novel)">
    <!-- <div class="cover">
      <span class="placeholder">Novel</span>
    </div> -->

    <div class="info">
      <h3 class="title">{{ props.novel.name }}</h3>
      <p class="author">{{ props.novel.author }}</p>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useNovelStore } from '@/stores/currentNovel'
const props = defineProps({
  novel: Object,
})

const router = useRouter()
const novelStore = useNovelStore()

const openBook = (book) => {
  novelStore.setNovel(book)
  console.log('打开书籍:', book)
  router.push(`/novel/${book.id}`)
}
</script>

<style scoped>
.card {
  width: calc(40% - 8px);
  max-width: 300px;
  width: 100%;
  border-radius: 12px;
  background: #55afd9;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  cursor: pointer;
  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease;
}

.card:hover {
  transform: translateY(-6px);
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.12);
}

/* 封面区域 */
.cover {
  height: 240px;
  background: linear-gradient(135deg, #5f7cff, #8fa4ff);
  display: flex;
  align-items: center;
  justify-content: center;
}

.placeholder {
  color: rgba(255, 255, 255, 0.7);
  font-size: 20px;
  letter-spacing: 2px;
}

/* 信息区 */
.info {
  padding: 14px;
}

.title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
  line-height: 1.4;

  /* 超出两行省略 */
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.author {
  font-size: 13px;
  color: #777;
}
</style>
