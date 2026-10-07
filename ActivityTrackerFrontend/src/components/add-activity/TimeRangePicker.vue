<script setup lang="ts">
/**
 * TimeRangePicker — two time inputs (start and end) for specifying
 * when an activity took place.
 */
defineProps<{
  startTime: string
  endTime: string
  error?: string
}>()
const emit = defineEmits<{ 'update:startTime': [value: string]; 'update:endTime': [value: string] }>()
</script>

<template>
  <div class="time-range-picker">
    <label class="field">
      <span>Start time</span>
      <input
        type="time"
        :value="startTime"
        @input="emit('update:startTime', ($event.target as HTMLInputElement).value)"
      />
    </label>
    <label class="field">
      <span>End time</span>
      <input
        type="time"
        :value="endTime"
        @input="emit('update:endTime', ($event.target as HTMLInputElement).value)"
      />
    </label>
    <small v-if="error" class="field-error">{{ error }}</small>
  </div>
</template>
