<script setup>
import { useUserStore } from '@/stores/userStore.js'
import { storeToRefs } from 'pinia'
import { onMounted, onUnmounted } from 'vue'
import router from '@/router/index.js'
import { useAuthStore } from '@/stores/authStore.js'

const userStore = useUserStore()
const authStore = useAuthStore()

const { user } = storeToRefs(userStore)
const { getRole } = userStore
const { logout } = authStore

let interval = null

const checkAuth = async () => {
  const result = await getRole()
  if (!result) {
    await logout()
    await router.push('/login')
  }
}

onMounted(async () => {
  await checkAuth()
  setInterval(checkAuth, 5000)
})

onUnmounted(() => clearInterval(interval))
</script>
<template>
  <h1>{{ user.role }}</h1>
</template>
