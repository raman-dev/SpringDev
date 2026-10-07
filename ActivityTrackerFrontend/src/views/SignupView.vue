<script setup lang="ts">
/**
 * SignupView — the account creation page.
 * Shows the app brand, email/password fields, and a link back to login.
 * On submit, calls the auth store's signUp method and redirects to the dashboard.
 */
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import BaseButton from '../components/BaseButton.vue'
import BaseInput from '../components/BaseInput.vue'

const router = useRouter()
const auth = useAuthStore()

/** Form state. */
const email = ref('')
const password = ref('')
/** Set true on first submit attempt — gates the validation error display. */
const submitted = ref(false)

/** Validates required fields, then calls the auth store. */
async function submit() {
  submitted.value = true
  if (!email.value || !password.value) return
  try {
    await auth.signUp(email.value, password.value)
    await router.push('/dashboard')
  } catch {
    // Error message is displayed from the auth store
  }
}
</script>

<template>
  <section class="login-page">
    <div class="login-card">
      <div class="login-brand">
        <span class="hero-mark">&#10003;</span>
        <h1>Create your account</h1>
        <p>Start tracking your activities today.</p>
      </div>
      <form class="login-form" @submit.prevent="submit">
        <BaseInput
          v-model="email"
          label="Email address"
          placeholder="you@example.com"
          type="email"
          :error="submitted && !email ? 'Enter your email address.' : ''"
        />
        <BaseInput
          v-model="password"
          label="Password"
          placeholder="Choose a password"
          type="password"
          :error="submitted && !password ? 'Enter a password.' : ''"
        />
        <p v-if="auth.error" class="form-error">{{ auth.error }}</p>
        <BaseButton type="submit">{{ auth.isLoading ? 'Creating account…' : 'Sign Up' }}</BaseButton>
      </form>
      <div class="or-divider"><span>or</span></div>
      <RouterLink to="/login">
        <BaseButton variant="secondary" type="button">Back to log in</BaseButton>
      </RouterLink>
    </div>
  </section>
</template>
