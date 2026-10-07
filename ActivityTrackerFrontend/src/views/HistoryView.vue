<script setup lang="ts">
/**
 * HistoryView — the activity history page.
 * Shows a chart of the last 7 days, filter controls, and a table
 * of all activities. Data is currently hard-coded; replace with API
 * calls to the Spring Boot backend once connected.
 */
import { ref, computed } from 'vue'
import HistoryChart from '../components/history/HistoryChart.vue'
import HistoryFilters from '../components/history/HistoryFilters.vue'
import HistoryTable from '../components/history/HistoryTable.vue'
import type { Activity, ActivityCategory } from '../types/activity'

/** Sample activity data — replace with GET /api/activities. */
const allActivities = ref<Activity[]>([
  { id: 1, name: 'Work', category: 'work', date: '2025-09-24', startTime: '9:00 AM', endTime: '12:00 PM', completed: true },
  { id: 2, name: 'Gym', category: 'gym', date: '2025-09-24', startTime: '1:00 PM', endTime: '2:00 PM', completed: true },
  { id: 3, name: 'Study', category: 'study', date: '2025-09-24', startTime: '3:00 PM', endTime: '5:00 PM', completed: true },
  { id: 4, name: 'Cooking', category: 'cooking', date: '2025-09-23', startTime: '6:00 PM', endTime: '7:00 PM', completed: true },
  { id: 5, name: 'Work', category: 'work', date: '2025-09-23', startTime: '9:00 AM', endTime: '12:00 PM', completed: true },
  { id: 6, name: 'Gym', category: 'gym', date: '2025-09-22', startTime: '7:00 AM', endTime: '8:00 AM', completed: true },
  { id: 7, name: 'Study', category: 'study', date: '2025-09-22', startTime: '2:00 PM', endTime: '4:00 PM', completed: true },
  { id: 8, name: 'Walk', category: 'other', date: '2025-09-21', startTime: '6:00 PM', endTime: '6:30 PM', completed: false },
])

/** Filter state. */
const search = ref('')
const category = ref<ActivityCategory | 'all'>('all')
const fromDate = ref('')
const toDate = ref('')

/** Chart data — activity counts for the last 7 days. */
const chartLabels = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
const chartValues = [5, 6, 4, 6, 5, 3, 4]

/** Filtered activities based on the current filter state. */
const filteredActivities = computed(() => {
  return allActivities.value.filter((activity) => {
    // Search filter — matches activity name
    if (search.value && !activity.name.toLowerCase().includes(search.value.toLowerCase())) {
      return false
    }
    // Category filter
    if (category.value !== 'all' && activity.category !== category.value) {
      return false
    }
    // Date range filter
    if (fromDate.value && activity.date < fromDate.value) {
      return false
    }
    if (toDate.value && activity.date > toDate.value) {
      return false
    }
    return true
  })
})

/** Delete an activity by id — replace with DELETE /api/activities/:id. */
function handleDelete(id: number) {
  allActivities.value = allActivities.value.filter((a) => a.id !== id)
}
</script>

<template>
  <div class="page history-page">
    <header class="page-header">
      <div>
        <p class="eyebrow">A clear view of your progress</p>
        <h1>Activity History</h1>
        <p class="subheading">Review, filter, and manage everything you've logged.</p>
      </div>
    </header>

    <HistoryChart :labels="chartLabels" :values="chartValues" />

    <section class="card-panel history-content">
      <HistoryFilters
        :search="search"
        :category="category"
        :from-date="fromDate"
        :to-date="toDate"
        @update:search="search = $event"
        @update:category="category = $event"
        @update:from-date="fromDate = $event"
        @update:to-date="toDate = $event"
      />

      <p v-if="filteredActivities.length === 0" class="empty-state">No activities match your filters.</p>

      <HistoryTable
        v-else
        :activities="filteredActivities"
        @delete="handleDelete"
      />
    </section>
  </div>
</template>
