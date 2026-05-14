import { createRouter, createWebHistory } from 'vue-router'

import RegisterPage from '@/pages/auth/RegisterPage.vue'
import LoginPage from '@/pages/auth/LoginPage.vue'
import ActivateAccountPage from '@/pages/auth/ActivateAccountPage.vue'
import PasswordRequestPage from '@/pages/auth/PasswordRequestPage.vue'
import PasswordUpdatePage from '@/pages/auth/PasswordUpdatePage.vue'

import { useAuthStore } from '@/stores/authStore.js'
import DashboardPage from '@/pages/DashboardPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: '/',
      component: DashboardPage,
      meta: {
        requiresAuth: true,
      },
    },

    {
      path: '/register',
      component: RegisterPage,
      meta: {
        guestOnly: true,
      },
    },

    {
      path: '/login',
      component: LoginPage,
      meta: {
        guestOnly: true,
      },
    },

    {
      path: '/verification',
      component: ActivateAccountPage,
    },

    {
      path: '/password/request',
      component: PasswordRequestPage,
    },

    {
      path: '/password/update',
      component: PasswordUpdatePage,
    },
  ],
})

router.beforeEach(async (to) => {
  const authStore = useAuthStore()

  const accessToken = localStorage.getItem('accessToken')
  const refreshToken = localStorage.getItem('refreshToken')

  if (!accessToken || !refreshToken) {
    if (to.meta.requiresAuth) {
      return '/login'
    }

    return true
  }

  const refreshed = await authStore.refresh()

  if (!refreshed) {
    if (to.meta.requiresAuth) {
      return '/login'
    }

    return true
  }

  if (to.meta.guestOnly) {
    return '/'
  }

  return true
})

export default router
