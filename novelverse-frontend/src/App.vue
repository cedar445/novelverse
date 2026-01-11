<template>
  <div id="app" :class="readerStore.theme">
    <router-view />
    <BottomNav v-if="showBottomNav" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import BottomNav from '@/components/BottomNav.vue'
import { useReaderStore } from '@/stores/reader'
import { useUserStore } from './stores/user'

const readerStore = useReaderStore()
const route = useRoute()

const showBottomNav = computed(() => {
  return !route.path.startsWith('/read')
})

const userStore = useUserStore()
userStore.restore()
</script>
