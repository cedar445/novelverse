<template>
  <div class="reader-page" :class="readerStore.theme">
    <!-- 顶部状态栏 -->
    <header class="reader-header">
      <span class="icon-btn back" @click="goBack">←</span>
      <span class="status">{{ currentChapter.title }}</span>
      <span class="icon-btn more">⋮</span>
    </header>

    <!-- 阅读正文 -->
    <main class="reader" :style="{ fontSize: readerStore.fontSize + 'px' }">
      <p v-for="(p, i) in currentChapter.content" :key="i" class="paragraph">
        {{ p }}
      </p>
    </main>

    <!-- 底部控制栏 -->
    <footer class="reader-footer">
      <!-- 翻章 -->
      <div class="nav-row">
        <button
          class="nav-btn"
          :class="{ disabled: currentChapterIndex === 0 }"
          @click="prevChapter"
        >
          上一章
        </button>
        <button
          class="nav-btn"
          :class="{ disabled: currentChapterIndex === chapters.length - 1 }"
          @click="nextChapter"
        >
          下一章
        </button>
      </div>

      <!-- 功能 -->
      <div class="tool-row">
        <button class="tool-btn left-btn" @click="toggleCatalog">目录</button>
        <button class="tool-btn right-btn" @click="showSetting = true">设置</button>
      </div>
    </footer>

    <!-- 目录 -->
    <transition name="catalog-slide">
      <aside v-if="showCatalog" class="catalog" @click.self="showCatalog = false">
        <div class="catalog-panel">
          <div class="catalog-header">
            <h3>目录</h3>
            <button class="close-btn" @click="showCatalog = false">×</button>
          </div>
          <div
            v-for="chapter in chapters"
            :key="chapter.id"
            class="catalog-item"
            :class="{ active: currentChapter.id === chapter.id }"
            @click="selectChapter(chapter)"
          >
            <span class="chapter-title">{{ chapter.title }}</span>
            <!-- <span v-if="currentChapter.id === chapter.id" class="current-label">当前</span> -->
          </div>
        </div>
      </aside>
    </transition>
    <!-- 设置面板 -->
    <transition name="setting-slide">
      <div v-if="showSetting" class="setting-mask" @click.self="showSetting = false">
        <div class="setting-panel">
          <div class="setting-header">
            <span>阅读设置</span>
            <span class="close" @click="showSetting = false">×</span>
          </div>

          <!-- 主题切换 -->
          <div class="setting-item">
            <span>夜间模式</span>
            <div class="switch">
              <button
                :class="{ active: readerStore.theme === 'light' }"
                @click="readerStore.theme = 'light'"
              >
                日间
              </button>
              <button
                :class="{ active: readerStore.theme === 'dark' }"
                @click="readerStore.theme = 'dark'"
              >
                夜间
              </button>
            </div>
          </div>

          <!-- 字号调整 -->
          <div class="setting-item">
            <span>字号大小</span>
            <div class="font-control">
              <button @click="readerStore.decreaseFont" :disabled="readerStore.fontSize <= 14">
                A−
              </button>

              <span>{{ readerStore.fontSize }}px</span>

              <button @click="readerStore.increaseFont" :disabled="readerStore.fontSize >= 28">
                A+
              </button>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref, computed } from 'vue'
import { useReaderStore } from '@/stores/reader'

const readerStore = useReaderStore()
const router = useRouter()

const goBack = () => {
  router.back()
}
const showSetting = ref(false)

const showCatalog = ref(false)

const chapters = ref([
  {
    id: 1,
    title: '第一章 风暴龙血',
    content: [
      '远处强撑着观战的贝妮雅特心中一颤，那是纯种风暴龙特有的血液。',
      '不同于波菲里奥的血，那血液光芒灿灿，是永戮的血。',
      '难道这一次对决就要落下帷幕了吗？',
    ],
  },
  {
    id: 2,
    title: '第二章 龙之觉醒',
    content: [
      '风暴龙的血滴落在大地上，瞬间引发了巨大的能量波动。',
      '周围的空气开始旋转，形成了一道道可见的气流。',
      '贝妮雅特感到自己的力量正在被这血液唤醒。',
    ],
  },
  {
    id: 3,
    title: '第三章 古老契约',
    content: [
      '在风暴龙血液的刺激下，古老的契约开始显现。',
      '贝妮雅特的手背上浮现出金色的龙纹。',
      '她意识到自己与风暴龙之间有着深刻的联系。',
    ],
  },
  {
    id: 4,
    title: '第四章 最终对决',
    content: [
      '波菲里奥感受到威胁，开始全力攻击。',
      '但贝妮雅特已经获得了风暴龙的力量。',
      '这场对决即将迎来最终的结局。',
    ],
  },
])

const currentChapter = ref(chapters.value[0])

const currentChapterIndex = computed(() => {
  return chapters.value.findIndex((chapter) => chapter.id === currentChapter.value.id)
})

const toggleCatalog = () => {
  showCatalog.value = !showCatalog.value
}

const selectChapter = (chapter) => {
  currentChapter.value = chapter
  showCatalog.value = false
}

const prevChapter = () => {
  if (currentChapterIndex.value > 0) {
    currentChapter.value = chapters.value[currentChapterIndex.value - 1]
  }
}

const nextChapter = () => {
  if (currentChapterIndex.value < chapters.value.length - 1) {
    currentChapter.value = chapters.value[currentChapterIndex.value + 1]
  }
}
</script>

<style scoped>
.reader-page {
  min-height: 100vh;
  background: #0f0f0f;
  color: #cfcfcf;
  padding-top: 56px;
  padding-bottom: 96px;
}

/* 顶部栏 */
.reader-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  font-size: 14px;
  color: #aaa;
  background: rgba(15, 15, 15, 0.9);
  z-index: 30;
}
/* 顶部图标按钮通用样式 */
.icon-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  color: #d0d0d0;
  cursor: pointer;
  user-select: none;
  transition: all 0.2s ease;
}

/* hover */
.icon-btn:hover {
  background: rgba(255, 255, 255, 0.12);
  color: #ffffff;
}

/* active（按下） */
.icon-btn:active {
  transform: scale(0.92);
  background: rgba(255, 255, 255, 0.18);
}

/* 返回按钮微调（更像 App） */
.back {
  font-size: 20px;
}

/* 更多按钮微调 */
.more {
  font-size: 22px;
  letter-spacing: 2px;
}

/* 正文 */
.reader {
  padding: 0 18px;
  line-height: 2.1;
  font-size: 18px;
}

.paragraph {
  margin-bottom: 24px;
  text-indent: 2em;
}

/* 底部栏 */
.reader-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 12px 16px 18px;
  background: linear-gradient(to top, rgba(15, 15, 15, 0.98), rgba(15, 15, 15, 0.85));
  backdrop-filter: blur(8px);
  z-index: 20;
  box-shadow: 0 -1px 10px rgba(0, 0, 0, 0.3);
}

/* 上一章 / 下一章 */
.nav-row {
  display: flex;
  gap: 12px;
  margin-bottom: 12px;
  justify-content: space-between;
}

.nav-btn {
  flex: 0 0 auto; /* 修改：不自动伸展 */
  min-width: 100px; /* 修改：设置最小宽度 */
  height: 44px;
  border-radius: 20px;
  background: #1f1f1f;
  color: #d0d0d0;
  border: none;
  font-size: 15px;
  letter-spacing: 0.5px;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
  overflow: hidden;
  padding: 10px 20px;
  margin: 7px;
}

.nav-btn::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.1);
  opacity: 0;
  transition: opacity 0.2s;
}

.nav-btn:hover::after {
  opacity: 1;
}

/* .nav-btn.primary {
  background: #2a2a2a;
  color: #ffffff;
} */

.nav-btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.nav-btn.disabled::after {
  display: none;
}

/* hover / active */
.nav-btn:not(.disabled):hover {
  background: #2c2c2c;
  transform: translateY(-1px);
}

.nav-btn:not(.disabled):active {
  transform: translateY(0) scale(0.96);
}

/* 底部工具按钮 */
.tool-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 8px;
}

.tool-btn {
  background: rgba(255, 255, 255, 0.08);
  border: none;
  color: #9a9a9a;
  font-size: 15px;
  padding: 10px 20px;
  cursor: pointer;
  transition: all 0.2s ease;
  border-radius: 20px;
  min-width: 100px;
  position: relative;
  overflow: hidden;
}

.tool-btn.left-btn {
  margin-right: auto;
}

.tool-btn.right-btn {
  margin-left: auto;
}

.tool-btn:hover {
  background: rgba(255, 255, 255, 0.12);
  color: #ffffff;
  transform: translateY(-1px);
}

.tool-btn:active {
  transform: translateY(0) scale(0.96);
  background: rgba(255, 255, 255, 0.15);
}

/* 目录滑入动效 */
.catalog-slide-enter-active,
.catalog-slide-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.catalog-slide-enter-from,
.catalog-slide-leave-to {
  opacity: 0;
}

.catalog-slide-enter-from .catalog-panel,
.catalog-slide-leave-to .catalog-panel {
  transform: translateX(-100%);
}

.catalog-slide-enter-to .catalog-panel,
.catalog-slide-leave-from .catalog-panel {
  transform: translateX(0);
}

/* 目录 */
.catalog {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 50;
}

.catalog-panel {
  width: 80%;
  max-width: 300px;
  height: 100%;
  background: #1a1a1a;
  padding: 0;
  overflow-y: auto;
  transform: translateX(0);
  transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.catalog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid transparent;
}
.reader-page.dark .catalog-header {
  background: rgba(255, 255, 255, 0.05);
  border-bottom-color: #333;
}

.reader-page.dark .catalog-header h3 {
  color: #fff;
}

.reader-page.light .catalog-header {
  background: rgba(0, 0, 0, 0.03);
  border-bottom-color: #e5e5e5;
}

.reader-page.light .catalog-header h3 {
  color: #222;
}

.catalog-header h3 {
  font-size: 17px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.close-btn {
  background: none;
  border: none;
  color: #aaa;
  font-size: 24px;
  width: 32px;
  height: 32px;
  cursor: pointer;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.close-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #0a0808;
}
/* 目录项 */

.catalog-item {
  padding: 16px;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.reader-page.dark .catalog-item {
  border-bottom: 1px solid #333;
  color: #ccc;
}

.reader-page.dark .catalog-item:hover {
  background: rgba(255, 255, 255, 0.06);
}

.reader-page.dark .catalog-item.active {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
  border-left: 3px solid #777;
  box-shadow: inset 3px 0 0 rgba(255, 255, 255, 0.25);
}
.reader-page.light .catalog-item {
  border-bottom: 1px solid #e5e5e5;
  color: #333;
}

.reader-page.light .catalog-item:hover {
  background: rgba(0, 0, 0, 0.04);
}

.reader-page.light .catalog-item.active {
  background: rgba(0, 0, 0, 0.06);
  color: #000;
  border-left: 3px solid #409eff;
  box-shadow: inset 3px 0 0 rgba(64, 158, 255, 0.8);
}
.catalog-item:hover {
  transform: translateX(2px);
}

.catalog-item:active {
  transform: scale(0.98);
}

.reader-page.light .chapter-title {
  color: #222;
}

.reader-page.dark .chapter-title {
  color: #ddd;
}

/* 设置 */
/* ===== 主题 ===== */
.reader-page.light {
  background: #f6f6f6;
  color: #222;
}

.reader-page.dark {
  background: #1e1e1e;
  color: #ddd;
}

/* ===== 设置面板遮罩 ===== */
.setting-mask {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  z-index: 100;
}

/* ===== 面板主体 ===== */
.setting-panel {
  position: absolute;
  bottom: 0;
  width: 100%;
  background: #2a2a2a;
  border-radius: 16px 16px 0 0;
  padding: 16px;
}

.reader-page.light .setting-panel {
  background: #fff;
}

/* ===== Header ===== */
.setting-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  margin-bottom: 12px;
}

.setting-header .close {
  font-size: 22px;
  cursor: pointer;
}

/* ===== 每一项 ===== */
.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 14px 0;
}

/* ===== 模式切换 ===== */
.switch button {
  margin-left: 6px;
  padding: 6px 12px;
  border-radius: 14px;
  border: none;
  background: #444;
  color: #ccc;
}

.switch button.active {
  background: #409eff;
  color: #fff;
}

/* ===== 字号控制 ===== */
.font-control {
  display: flex;
  align-items: center;
  gap: 10px;
}

.font-control button {
  padding: 6px 10px;
  border-radius: 6px;
  border: none;
  background: #444;
  color: #fff;
}

/* ===== 动画 ===== */
.setting-slide-enter-from,
.setting-slide-leave-to {
  transform: translateY(100%);
  opacity: 0;
}

.setting-slide-enter-active,
.setting-slide-leave-active {
  transition: all 0.3s ease;
}

/* 夜间模式开关 */
.toggle {
  padding: 6px 14px;
  border-radius: 999px;
  border: 1px solid #ddd;
  background: #f3f4f6;
  font-size: 13px;
  cursor: pointer;
}

.toggle.on {
  background: #5f7cff;
  border-color: #5f7cff;
  color: #fff;
}

/* 夜间模式 */
/* ======================
   日 / 夜 主题全局覆盖
   ====================== */

/* ===== Light ===== */
.reader-page.light {
  background: #f6f6f6;
  color: #222;
}

.reader-page.light .reader-header {
  background: rgba(246, 246, 246, 0.95);
  color: #333;
}

.reader-page.light .reader-footer {
  background: linear-gradient(to top, rgba(246, 246, 246, 0.98), rgba(246, 246, 246, 0.9));
}

.reader-page.light .nav-btn,
.reader-page.light .tool-btn {
  background: #e5e5e5;
  color: #333;
}

.reader-page.light .catalog-panel {
  background: #ffffff;
  color: #222;
}

.reader-page.light .setting-panel {
  background: #ffffff;
  color: #222;
}

/* ===== Dark ===== */
.reader-page.dark {
  background: #1e1e1e;
  color: #ddd;
}

.reader-page.dark .reader-header {
  background: rgba(30, 30, 30, 0.95);
}

.reader-page.dark .reader-footer {
  background: linear-gradient(to top, rgba(30, 30, 30, 0.98), rgba(30, 30, 30, 0.9));
}

.reader-page.dark .nav-btn,
.reader-page.dark .tool-btn {
  background: #2a2a2a;
  color: #ddd;
}

.reader-page.dark .catalog-panel {
  background: #1a1a1a;
  color: #ddd;
}

.reader-page.dark .setting-panel {
  background: #2a2a2a;
  color: #ddd;
}
</style>
