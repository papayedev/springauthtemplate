import { defineStore } from 'pinia'
import { reactive } from 'vue'

const API_URL = 'http://localhost:8080/users'

export const useUserStore = defineStore('userStore', () => {
  const user = reactive({
    role: '',
  })

  const getRole = async () => {
    const accessToken = localStorage.getItem('accessToken')

    const res = await fetch(API_URL + '/role', {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })

    if (!res.ok) {
      return false
    }

    user.role = await res.json()
    return true
  }

  return {
    user,
    getRole,
  }
})
