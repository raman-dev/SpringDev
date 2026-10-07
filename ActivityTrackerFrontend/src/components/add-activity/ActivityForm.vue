<script setup lang="ts">
/**
 * ActivityForm — the full form for creating a new activity.
 * Composes BaseInput, CategoryPicker, TimeRangePicker, and BaseButton.
 * Validates required fields and emits a "submit" event with the payload.
 */
import { ref, computed } from 'vue'
import BaseInput from '../BaseInput.vue'
import BaseButton from '../BaseButton.vue'
import CategoryPicker from './CategoryPicker.vue'
import TimeRangePicker from './TimeRangePicker.vue'
import type { ActivityCategory } from '../../types/activity'

/** Emitted when the form passes validation and the user clicks Save. */
const emit = defineEmits<{
  submit: [payload: { name: string; category: ActivityCategory; date: string; startTime: string; endTime: string }]
}>()

/** Form state. */
const name = ref('')
const category = ref<ActivityCategory>('work')
const date = ref(new Date().toISOString().slice(0, 10))
const startTime = ref('09:00')
const endTime = ref('10:00')
const submitted = ref(false)

/** Validation: name is required, end time must be after start time. */
const timeError = computed(() => {
  if (!startTime.value || !endTime.value) return ''
  return endTime.value <= startTime.value ? 'End time must be after start time.' : ''
})

/** Returns true when all required fields are filled and valid. */
const isValid = computed(() => name.value.trim() !== '' && !timeError.value)

function handleSubmit() {
  submitted.value = true
  if (!isValid.value) return
  emit('submit', {
    name: name.value.trim(),
    category: category.value,
    date: date.value,
    startTime: startTime.value,
    endTime: endTime.value,
  })
}
</script>

<template>
  <form class="activity-form" @submit.prevent="handleSubmit">
    <BaseInput
      v-model="name"
      label="Activity name"
      placeholder="e.g. Morning run"
      :error="submitted && !name ? 'Enter an activity name.' : ''"
    />

    <div class="field">
      <span>Category</span>
      <CategoryPicker v-model="category" />
    </div>

    <div class="field">
      <span>Date</span>
      <input
        type="date"
        class="date-input"
        :value="date"
        @input="date = ($event.target as HTMLInputElement).value"
      />
    </div>

    <TimeRangePicker
      :start-time="startTime"
      :end-time="endTime"
      :error="submitted ? timeError : ''"
      @update:start-time="startTime = $event"
      @update:end-time="endTime = $event"
    />

    <BaseButton type="submit">Save Activity</BaseButton>
  </form>
</template>
