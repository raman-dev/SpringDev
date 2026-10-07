/**
 * Axios HTTP client configured for the Spring Boot backend.
 * Sends credentials (JSESSIONID cookie) with every request and
 * globally intercepts 401 responses to trigger a logout redirect.
 */
import axios from 'axios'

/** Shape of the user object returned by the backend auth endpoints. */
export interface AuthUser {
  name: string
  email: string
}

/** Pre-configured axios instance — all API calls go through this. */
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:2020/',
  withCredentials: true,
  headers: { 'Content-Type': 'application/json' },
})

/** Callback registered by the app to handle 401 responses (e.g. redirect to login). */
let onUnauthorized: (() => void) | null = null

/** Registers the global 401 handler. Called once during app startup. */
export function configureApi(handler: () => void) {
  onUnauthorized = handler
}

/** Response interceptor — on 401, fires the registered handler. */
api.interceptors.response.use(
  (response) => response,
  (error: unknown) => {
    if (axios.isAxiosError(error) && error.response?.status === 401 && onUnauthorized) {
      onUnauthorized()
    }
    return Promise.reject(error)
  },
)
