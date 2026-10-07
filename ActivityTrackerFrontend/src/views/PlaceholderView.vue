<script setup lang="ts">
/**
 * PlaceholderView — generic placeholder for pages not yet built.
 * Currently used for Settings and the 404 catch-all route.
 */
defineProps<{ title: string; eyebrow: string }>()
</script>

<template>
  <div class="page placeholder-page">
    <div class="placeholder-icon">&#10003;</div>
    <p class="eyebrow">{{ eyebrow }}</p>
    <h1>{{ title }}</h1>
    <p class="subheading">This page is ready for its focused activity-tracking experience.</p>
  </div>
</template>
