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

const logoutAction = async () => {
  await logout()
  await router.push('/login')
}

onMounted(async () => {
  await checkAuth()
  setInterval(checkAuth, 5000)
})

onUnmounted(() => clearInterval(interval))
</script>

<template>
  <v-layout class="h-screen rounded rounded-md border">
    <v-app-bar title="Application bar"></v-app-bar>

    <v-main>
      <v-container class="d-flex align-center justify-center">
        <h1>{{ user.role }}</h1>
      </v-container>
    </v-main>

    <v-bottom-navigation grow>
      <v-btn to="/" value="dashboard">
        <v-icon>mdi-home</v-icon>

        <span>Dashboard</span>
      </v-btn>

      <v-btn @click="logoutAction" value="logout">
        <v-icon>mdi-logout</v-icon>

        <span>Logout</span>
      </v-btn>
    </v-bottom-navigation>
  </v-layout>
</template>
