<script setup>
defineProps(['url', 'name'])
</script>

<template>
  <div class="logo">
    <v-img width="100%" height="50%" :aspect-ratio="16 / 9" :src="url" :alt="name" cover></v-img>
  </div>
</template>
