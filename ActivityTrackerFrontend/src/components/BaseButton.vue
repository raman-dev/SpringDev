<script setup lang="ts">
/**
 * BaseButton — reusable button with three visual variants.
 * Used across login, signup, add-activity, and other forms.
 */
defineProps<{ variant?: 'primary' | 'secondary' | 'ghost'; type?: 'button' | 'submit' }>()
</script>

<template>
  <button
    :type="type ?? 'button'"
    class="base-button"
    :class="`button-${variant ?? 'primary'}`"
  >
    <slot />
  </button>
</template>
