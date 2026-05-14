<script setup>
import Logo from '@/components/Logo.vue'
import Alert from '@/components/Alert.vue'
import { useAuthStore } from '@/stores/authStore.js'
import { storeToRefs } from 'pinia'
import { reactive } from 'vue'
import router from '@/router/index.js'

const form = reactive({
  email: '',
  code: '',
})

const authStore = useAuthStore()

const { error } = storeToRefs(authStore)
const { verification } = authStore

const verificationAction = async () => {
  const success = await verification(form.email, form.code)
  if (success) {
    await router.push('/login')
  }
}
</script>

<template>
  <div class="container fluid" style="margin-top: 15%; margin-bottom: 10%">
    <Alert v-if="error" status="error">{{ error }}</Alert>
    <h1>Vérification d'email</h1>
    <div class="grid">
      <Logo
        name="sample"
        url="https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg"
      />
      <div class="form">
        <form @submit.prevent.stop>
          <label for="email">Adresse mail</label>
          <input v-model="form.email" type="email" id="email" name="email" placeholder="Email" />
          <label for="code">Code de vérification</label>
          <input v-model="form.code" type="password" id="code" name="code" placeholder="******" />
          <input type="submit" value="Activer" @click="verificationAction" />
        </form>
      </div>
    </div>
  </div>
</template>
