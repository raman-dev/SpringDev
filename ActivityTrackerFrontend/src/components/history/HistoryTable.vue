<script setup lang="ts">
/**
 * HistoryTable — a table listing all activities for the selected filter.
 * Shows the category dot, name, date, time range, and completion status.
 */
import type { Activity } from '../../types/activity'
import { categoryDotClass, categoryLabels } from '../../types/activity'

defineProps<{ activities: Activity[] }>()

/** Emitted when the user clicks the delete button on a row. */
const emit = defineEmits<{ 'delete': [id: number] }>()
</script>

<template>
  <div class="history-table-wrapper">
    <table class="history-table">
      <thead>
        <tr>
          <th>Activity</th>
          <th>Category</th>
          <th>Date</th>
          <th>Time</th>
          <th>Status</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="activity in activities" :key="activity.id">
          <td>
            <span class="activity-dot" :class="categoryDotClass[activity.category]"></span>
            <strong>{{ activity.name }}</strong>
          </td>
          <td>{{ categoryLabels[activity.category] }}</td>
          <td>{{ activity.date }}</td>
          <td>{{ activity.startTime }} &ndash; {{ activity.endTime }}</td>
          <td>
            <span class="status-badge" :class="activity.completed ? 'status-done' : 'status-pending'">
              {{ activity.completed ? 'Done' : 'Pending' }}
            </span>
          </td>
          <td>
            <button class="row-delete" aria-label="Delete activity" @click="emit('delete', activity.id)">&#10005;</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
