<script setup>
defineProps(['status'])
</script>

<template>
  <div class="alert">
    <article :class="status">
      <slot></slot>
    </article>
  </div>
</template>
<style scoped>
.success {
  background-color: #186a18;
}
.warning {
  background-color: #838315;
}
.error {
  background-color: #711616;
}
</style>
