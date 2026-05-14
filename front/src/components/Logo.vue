<script setup>
defineProps(['url', 'name'])
</script>

<template>
  <div class="logo">
    <img :src="url" :alt="name">
  </div>
</template>
