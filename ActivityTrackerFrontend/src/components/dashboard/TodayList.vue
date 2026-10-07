<script setup lang="ts">
/**
 * TodayList — the "Today's Activity" panel showing the current day's
 * activities with their category dot, time range, and completion checkmark.
 */
import { RouterLink } from 'vue-router'
import type { TodayActivity } from '../../types/activity'
import { categoryDotClass } from '../../types/activity'

defineProps<{ activities: TodayActivity[]; dateLabel: string }>()
</script>

<template>
  <section class="today-panel card-panel">
    <div class="section-heading">
      <div>
        <h2>Today's Activity</h2>
        <p>{{ dateLabel }}</p>
      </div>
      <RouterLink to="/add-activity">View all</RouterLink>
    </div>
    <div
      v-for="activity in activities"
      :key="activity.name + activity.startTime"
      class="today-row"
    >
      <span class="activity-dot" :class="categoryDotClass[activity.category]"></span>
      <strong>{{ activity.name }}</strong>
      <small>{{ activity.startTime }} &ndash; {{ activity.endTime }}</small>
      <b v-if="activity.completed">&#10003;</b>
    </div>
  </section>
</template>
