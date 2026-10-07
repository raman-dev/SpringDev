<script setup lang="ts">
/**
 * WeekGrid — the 7-day overview grid showing each day's activities
 * with color-coded dots. Includes prev/next week navigation buttons.
 */
import type { WeekDay } from '../../types/activity'

defineProps<{ days: WeekDay[] }>()

/** Emitted when the user clicks the previous or next week arrow. */
const emit = defineEmits<{ 'previous-week': []; 'next-week': [] }>()
</script>

<template>
  <section class="week-panel card-panel">
    <div class="section-heading">
      <h2>This Week</h2>
      <div class="week-actions">
        <button aria-label="Previous week" @click="emit('previous-week')">&#8249;</button>
        <button aria-label="Next week" @click="emit('next-week')">&#8250;</button>
      </div>
    </div>
    <div class="days-grid">
      <article
        v-for="day in days"
        :key="day.date"
        class="day-card"
        :class="{ selected: day.active }"
      >
        <header>
          <strong>{{ day.name }}</strong>
          <small>{{ day.date }}</small>
        </header>
        <p class="day-count">+ {{ day.count }}</p>
        <ul>
          <li v-for="(activity, index) in day.activities" :key="activity">
            <span class="activity-dot" :class="`dot-${index % 5}`"></span>
            {{ activity }}
          </li>
        </ul>
      </article>
    </div>
  </section>
</template>
