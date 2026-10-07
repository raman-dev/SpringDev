<script setup lang="ts">
/**
 * DashboardHeader — the greeting block at the top of the dashboard.
 * Shows the current date, a personalised greeting, and a week selector pill.
 */
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()

/** Today's date formatted for display. */
const todayLabel = new Date().toLocaleDateString('en-US', {
  weekday: 'long',
  month: 'long',
  day: 'numeric',
  year: 'numeric',
})

/** Short date label for the pill on the right. */
const shortDate = new Date().toLocaleDateString('en-US', {
  month: 'short',
  day: 'numeric',
  year: 'numeric',
})

/** Time-of-day greeting based on the current hour. */
function greeting(): string {
  const hour = new Date().getHours()
  if (hour < 12) return 'Good morning'
  if (hour < 18) return 'Good afternoon'
  return 'Good evening'
}
</script>

<template>
  <header class="page-header">
    <div>
      <p class="eyebrow">{{ todayLabel }}</p>
      <h1>{{ greeting() }}, {{ auth.displayName }}!</h1>
      <p class="subheading">Here's your activity overview for this week.</p>
    </div>
    <span class="date-pill">{{ shortDate }} <b>&#9635;</b></span>
  </header>
</template>
