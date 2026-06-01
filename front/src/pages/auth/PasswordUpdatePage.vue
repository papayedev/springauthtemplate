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
  code: (value) => {
    return value.length > 5 || 'Verification code must be at least 5 characters'
  },
}

const form = reactive({
  email: '',
  code: '',
  password: '',
})

const authStore = useAuthStore()

const { error } = storeToRefs(authStore)
const { passwordUpdate } = authStore

const passwordUpdateAction = async () => {
  loading.value = true
  const success = await passwordUpdate(form.email, form.code, form.password)
  if (success) {
    await router.push('/login')
  } else {
    loading.value = false
  }
}
</script>

<template>
  <v-container>
    <h1 class="text-center mt-4 mb-4">Update password</h1>
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
            prepend-inner-icon="mdi-code-array"
            v-model="form.code"
            label="Verification code"
            :rules="[rules.required, rules.code]"
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
          <v-btn :disabled="loading" :loading="loading" block @click="passwordUpdateAction"
            >Validate</v-btn
          >
        </form>
      </v-col>
    </v-row>
  </v-container>
</template>
