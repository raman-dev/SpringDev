/**
 * Pinia auth store — manages the signed-in user state.
 * Talks to the Spring Boot backend's form-login, register, logout,
 * and session endpoints via the shared axios instance.
 */
import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { api, type AuthUser } from '../services/api'

export const useAuthStore = defineStore('auth', () => {
  /** The currently signed-in user, or null when not authenticated. */
  const user = ref<AuthUser | null>(null)
  /** True while a login or signup request is in flight. */
  const isLoading = ref(false)
  /** Last error message from a failed auth action (shown in the UI). */
  const error = ref('')

  /** Derived: true when a user object exists. */
  const isAuthenticated = computed(() => user.value !== null)
  /** Derived: display name for the sidebar profile. */
  const displayName = computed(() => user.value?.name ?? 'User')
  /** Derived: email for the sidebar profile. */
  const email = computed(() => user.value?.email ?? '')

  /**
   * Checks if the browser already has a valid session (JSESSIONID cookie)
   * by calling GET /api/session. Called once on app startup.
   */
  async function fetchSession() {
    try {
      const { data } = await api.get<AuthUser>('/session')
      user.value = data
    } catch {
      user.value = null
    }
  }

  /**
   * Submits form-encoded credentials to POST /api/login.
   * On success, stores the returned user object.
   */
  async function login(email: string, password: string) {
    isLoading.value = true
    error.value = ''
    try {
      const { data } = await api.post<AuthUser>(
        '/login',
        new URLSearchParams({ username: email, password }),
        { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } },
      )
      user.value = data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'We could not sign you in. Check your details and try again.'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  /**
   * Registers a new user via POST /api/register with a JSON body.
   * On success, stores the returned user object.
   */
  async function signUp(email: string, password: string) {
    isLoading.value = true
    error.value = ''
    try {
      const { data } = await api.post<AuthUser>('/register', { email, password })
      user.value = data
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'We could not create your account. Please try again.'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  /** Calls POST /api/logout and clears the local user state. */
  async function logout() {
    await api.post('/logout').catch(() => undefined)
    user.value = null
  }

  /** Clears the last error message (e.g. when the user starts typing again). */
  function clearError() { error.value = '' }

  return { user, isLoading, error, isAuthenticated, displayName, email, fetchSession, login, signUp, logout, clearError }
})
