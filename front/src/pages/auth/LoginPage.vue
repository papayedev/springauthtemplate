<script setup>
import Logo from '@/components/Logo.vue'
import { useAuthStore } from '@/stores/authStore.js'
import { storeToRefs } from 'pinia'
import { reactive, ref } from 'vue'
import router from '@/router/index.js'

const visible = ref(false)
const loading = ref(false)

const rules = {
  required: (value) => !!value || 'Required.',
  email: (value) => {
    const pattern =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || 'Invalid email.'
  },
  password: (value) => {
    return value.length > 8 || 'Password must be at least 8 characters'
  },
}

const form = reactive({
  email: '',
  password: '',
})

const authStore = useAuthStore()

const { error } = storeToRefs(authStore)
const { login } = authStore

const loginAction = async () => {
  loading.value = true
  const success = await login(form.email, form.password)
  if (success) {
    await router.push('/')
  } else {
    loading.value = false
  }
}
</script>

<template>
  <v-container>
    <h1 style="text-align: center" class="mt-4 mb-4">Sign in</h1>
    <v-alert class="mt-4 mb-4" :text="error" title="Errors" type="error" v-if="error"></v-alert>
    <v-row>
      <v-col cols="12" md="6">
        <Logo
          name="sample"
          url="https://t4.ftcdn.net/jpg/01/43/42/83/360_F_143428338_gcxw3Jcd0tJpkvvb53pfEztwtU9sxsgT.jpg"
        />
      </v-col>
      <v-col cols="12" md="6">
        <form @submit.prevent.stop>
          <v-text-field
            prepend-inner-icon="mdi-email-outline"
            v-model="form.email"
            label="Email"
            :rules="[rules.required, rules.email]"
            required
          ></v-text-field>
          <v-text-field
            prepend-inner-icon="mdi-lock-outline"
            :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
            :type="visible ? 'text' : 'password'"
            v-model="form.password"
            label="Password"
            :rules="[rules.required, rules.password]"
            @click:append-inner="visible = !visible"
            required
          ></v-text-field>
          <v-btn :disabled="loading" :loading="loading" block @click="loginAction">Validate</v-btn>
        </form>
        <div class="mt-4">
          <router-link to="/password/request">Forgot Password</router-link>
          <br />
          <router-link to="/register">Don't have account</router-link>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>
