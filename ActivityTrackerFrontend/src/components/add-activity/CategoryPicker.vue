<script setup lang="ts">
/**
 * CategoryPicker — a row of selectable category buttons.
 * Each category maps to a color dot used throughout the app.
 */
import type { ActivityCategory } from '../../types/activity'
import { categoryLabels, categoryDotClass } from '../../types/activity'

defineProps<{ modelValue: ActivityCategory }>()
const emit = defineEmits<{ 'update:modelValue': [value: ActivityCategory] }>()

/** All selectable categories. */
const categories = Object.keys(categoryLabels) as ActivityCategory[]
</script>

<template>
  <div class="category-picker" role="radiogroup" aria-label="Activity category">
    <button
      v-for="cat in categories"
      :key="cat"
      type="button"
      role="radio"
      :aria-checked="modelValue === cat"
      class="category-option"
      :class="{ selected: modelValue === cat }"
      @click="emit('update:modelValue', cat)"
    >
      <span class="activity-dot" :class="categoryDotClass[cat]"></span>
      {{ categoryLabels[cat] }}
    </button>
  </div>
</template>
