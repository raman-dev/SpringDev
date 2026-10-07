<script setup lang="ts">
/**
 * HistoryFilters — filter bar for the history page.
 * Lets the user filter by category and date range, with a search field.
 */
import type { ActivityCategory } from '../../types/activity'
import { categoryLabels } from '../../types/activity'

defineProps<{
  search: string
  category: ActivityCategory | 'all'
  fromDate: string
  toDate: string
}>()
const emit = defineEmits<{
  'update:search': [value: string]
  'update:category': [value: ActivityCategory | 'all']
  'update:fromDate': [value: string]
  'update:toDate': [value: string]
}>()

/** All selectable categories plus an "all" option. */
const categoryOptions: (ActivityCategory | 'all')[] = ['all', ...Object.keys(categoryLabels) as ActivityCategory[]]
</script>

<template>
  <div class="history-filters">
    <label class="field">
      <span>Search</span>
      <input
        type="text"
        placeholder="Search activities…"
        :value="search"
        @input="emit('update:search', ($event.target as HTMLInputElement).value)"
      />
    </label>
    <label class="field">
      <span>Category</span>
      <select
        :value="category"
        @change="emit('update:category', ($event.target as HTMLSelectElement).value as ActivityCategory | 'all')"
      >
        <option v-for="opt in categoryOptions" :key="opt" :value="opt">
          {{ opt === 'all' ? 'All categories' : categoryLabels[opt] }}
        </option>
      </select>
    </label>
    <label class="field">
      <span>From</span>
      <input
        type="date"
        :value="fromDate"
        @input="emit('update:fromDate', ($event.target as HTMLInputElement).value)"
      />
    </label>
    <label class="field">
      <span>To</span>
      <input
        type="date"
        :value="toDate"
        @input="emit('update:toDate', ($event.target as HTMLInputElement).value)"
      />
    </label>
  </div>
</template>
