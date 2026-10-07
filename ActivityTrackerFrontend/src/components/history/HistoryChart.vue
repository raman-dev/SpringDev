<script setup lang="ts">
/**
 * HistoryChart — a simple bar chart showing activity counts per day
 * for the last 7 days. Uses CSS bars (no chart library dependency).
 */
defineProps<{
  /** Labels for each bar, e.g. ["Mon", "Tue", ...]. */
  labels: string[]
  /** Numeric values for each bar. */
  values: number[]
}>()

/** Maximum value, used to scale bar heights as percentages. */
function maxVal(values: number[]): number {
  return Math.max(...values, 1)
}
</script>

<template>
  <section class="history-chart card-panel">
    <div class="section-heading">
      <h2>Last 7 Days</h2>
    </div>
    <div class="chart-bars">
      <div v-for="(label, index) in labels" :key="label" class="chart-bar-group">
        <div class="chart-bar" :style="{ height: `${(values[index] / maxVal(values)) * 100}%` }">
          <span class="chart-bar-value">{{ values[index] }}</span>
        </div>
        <small>{{ label }}</small>
      </div>
    </div>
  </section>
</template>
