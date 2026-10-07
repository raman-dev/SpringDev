<script setup lang="ts">
/**
 * BaseInput — reusable labelled text input with v-model support
 * and an optional error message slot.
 */
defineProps<{ modelValue: string; label: string; placeholder?: string; type?: string; error?: string }>()
defineEmits<{ 'update:modelValue': [value: string] }>()
</script>

<template>
  <label class="field">
    <span>{{ label }}</span>
    <input
      :type="type ?? 'text'"
      :value="modelValue"
      :placeholder="placeholder"
      @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
    />
    <small v-if="error" class="field-error">{{ error }}</small>
  </label>
</template>
