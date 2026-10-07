<script setup lang="ts">
/**
 * AddActivityView — page where the user records a new activity.
 * Wraps the ActivityForm in a card panel matching the dashboard style.
 * On submit, the payload is logged and a success message is shown.
 * Replace the local submit handler with an API call to the Spring Boot
 * backend once it is connected.
 */
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import ActivityForm from '../components/add-activity/ActivityForm.vue'
import type { ActivityCategory } from '../types/activity'

const router = useRouter()
const successMessage = ref('')

/** Called when the form is submitted with valid data. */
function handleSubmit(payload: { name: string; category: ActivityCategory; date: string; startTime: string; endTime: string }) {
  // TODO: POST payload to the Spring Boot backend at /api/activities
  successMessage.value = `"${payload.name}" saved successfully!`

  // Redirect to the dashboard after a brief confirmation
  setTimeout(() => { void router.push('/dashboard') }, 1200)
}
</script>

<template>
  <div class="page add-activity-page">
    <header class="page-header">
      <div>
        <p class="eyebrow">Your next win starts here</p>
        <h1>Add Activity</h1>
        <p class="subheading">Record what you did — every entry builds the picture of your week.</p>
      </div>
    </header>

    <p v-if="successMessage" class="success-banner">{{ successMessage }}</p>

    <section class="card-panel form-panel">
      <ActivityForm @submit="handleSubmit" />
    </section>
  </div>
</template>
