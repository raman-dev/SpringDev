<script setup lang="ts">
/**
 * StatCard — a single statistic card showing an icon, label, value,
 * caption, and change indicator. Used in the dashboard stats grid.
 */
defineProps<{ label: string; value: string; caption: string; change: string; icon: string }>()
</script>

<template>
  <article class="stat-card">
    <div class="stat-icon">{{ icon }}</div>
    <div>
      <p class="stat-label">{{ label }}</p>
      <strong class="stat-value">{{ value }}</strong>
      <p class="stat-caption">{{ caption }}</p>
      <p class="stat-change">&uarr; {{ change }}</p>
    </div>
  </article>
</template>
