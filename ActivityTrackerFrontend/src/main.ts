/**
 * App entry point — creates the Vue app, installs Pinia and Vue Router,
 * wires the global 401 handler, and checks for an existing session.
 */
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './style.css'
import App from './App.vue'
import router from './router'
import { configureApi } from './services/api'
import { useAuthStore } from './stores/auth'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia).use(router)

/** On any 401 response, log out and redirect to the login page. */
configureApi(() => {
  void useAuthStore(pinia).logout()
  void router.push({ name: 'login' })
})

app.mount('#app')

/** Check if the browser already has a valid session on startup. */
void useAuthStore(pinia).fetchSession()
