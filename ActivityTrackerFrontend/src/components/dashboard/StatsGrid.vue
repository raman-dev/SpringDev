<script setup lang="ts">
/**
 * StatsGrid — the row of three stat cards (daily, weekly, monthly)
 * shown at the top of the dashboard.
 */
import StatCard from '../StatCard.vue'
import type { ActivityStat } from '../../types/activity'

defineProps<{ stats: ActivityStat[] }>()
</script>

<template>
  <section class="stats-grid">
    <StatCard
      v-for="stat in stats"
      :key="stat.label"
      :label="stat.label"
      :value="String(stat.value)"
      :caption="stat.caption"
      :change="stat.change"
      :icon="stat.icon"
    />
  </section>
</template>
