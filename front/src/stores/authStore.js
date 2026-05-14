import { defineStore } from 'pinia'
import { ref } from 'vue'

const API_URL = 'http://localhost:8080/auth'

export const useAuthStore = defineStore('authStore', () => {
  const error = ref('')

  const register = async (email, password) => {
    error.value = ''

    const res = await fetch(API_URL + '/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email,
        password,
      }),
    })

    if (!res.ok) {
      const data = await res.json()
      error.value = data.message
      return false
    }

    return true
  }

  const login = async (email, password) => {
    error.value = ''

    const res = await fetch(API_URL + '/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    })

    const data = await res.json()

    if (!res.ok) {
      error.value = data.message
      return false
    }

    localStorage.setItem('accessToken', data.accessToken)
    localStorage.setItem('refreshToken', data.refreshToken)

    return true
  }

  const verification = async (email, verificationCode) => {
    error.value = ''

    const res = await fetch(API_URL + '/activate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, verificationCode }),
    })

    if (!res.ok) {
      const data = await res.json()
      error.value = data.message
      return false
    }

    return true
  }

  const passwordRequest = async (email) => {
    error.value = ''

    const res = await fetch(API_URL + '/request/password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email,
      }),
    })

    if (!res.ok) {
      const data = await res.json()
      error.value = data.message
      return false
    }

    return true
  }

  const passwordUpdate = async (email, verificationCode, password) => {
    error.value = ''

    const res = await fetch(API_URL + '/update/password', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, verificationCode, password }),
    })

    if (!res.ok) {
      const data = await res.json()
      error.value = data.message
      return false
    }

    return true
  }

  const refresh = async () => {
    const refreshToken = localStorage.getItem('refreshToken')

    const res = await fetch(API_URL + '/refresh', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ refreshToken }),
    })

    if (!res.ok) {
      localStorage.removeItem('accessToken')
      localStorage.removeItem('refreshToken')
      return false
    }

    const data = await res.json()

    localStorage.setItem('accessToken', data.accessToken)

    return true
  }

  const logout = async () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
  }

  return {
    error,

    register,
    login,
    verification,
    passwordRequest,
    passwordUpdate,
    refresh,
    logout,
  }
})
