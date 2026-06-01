<script setup>
import Logo from '@/components/Logo.vue'
import { useAuthStore } from '@/stores/authStore.js'
import { storeToRefs } from 'pinia'
import { reactive, ref } from 'vue'
import router from '@/router/index.js'

const loading = ref(false)

const rules = {
  required: (value) => !!value || 'Required.',
  email: (value) => {
    const pattern =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || 'Invalid email.'
  },
}

const form = reactive({
  email: '',
})

const authStore = useAuthStore()

const { error } = storeToRefs(authStore)
const { passwordRequest } = authStore

const passwordRequestAction = async () => {
  loading.value = true
  const success = await passwordRequest(form.email)
  if (success) {
    await router.push('/password/update')
  } else {
    loading.value = false
  }
}
</script>

<template>
  <v-container>
    <h1 style="text-align: center" class="mt-4 mb-4">Demande de mot de passe</h1>
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
          <v-btn :loading="loading" :disabled="loading" block @click="passwordRequestAction"
            >Validate</v-btn
          >
        </form>
        <router-link to="/login">Sign in</router-link>
      </v-col>
    </v-row>
  </v-container>
</template>
