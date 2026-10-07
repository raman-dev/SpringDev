<script setup lang="ts">
/**
 * App.vue — root layout component.
 * Shows the sidebar (nav + profile + logout) on all authenticated pages,
 * and hides it on the login and signup pages.
 */
import { computed } from 'vue'
import { RouterLink, RouterView, useRoute, useRouter } from 'vue-router'
import { useAuthStore } from './stores/auth'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

/** Sidebar is hidden on the login and signup pages. */
const showShell = computed(() => route.name !== 'login' && route.name !== 'signup')

/** Navigation items for the sidebar. */
const navigation = [
  { label: 'Home', to: '/dashboard', icon: '\u2302' },
  { label: 'Add Activity', to: '/add-activity', icon: '+' },
  { label: 'History', to: '/history', icon: '\u25A3' },
  { label: 'Settings', to: '/settings', icon: '\u2699' },
]

/** Logs the user out and redirects to the login page. */
async function handleLogout() {
  await auth.logout()
  await router.push({ name: 'login' })
}
</script>

<template>
  <div class="app-frame" :class="{ 'auth-frame': !showShell }">
    <aside v-if="showShell" class="sidebar">
      <RouterLink to="/dashboard" class="brand">
        <span class="brand-mark">&#10003;</span>
        <span>Activity Tracker</span>
      </RouterLink>
      <nav class="main-nav" aria-label="Main navigation">
        <RouterLink
          v-for="item in navigation"
          :key="item.to"
          :to="item.to"
          class="nav-item"
        >
          <span class="nav-icon">{{ item.icon }}</span>{{ item.label }}
        </RouterLink>
      </nav>
      <div class="sidebar-footer">
        <div class="profile">
          <span class="avatar">{{ auth.displayName.charAt(0).toUpperCase() }}</span>
          <span>
            <strong>{{ auth.displayName }}</strong>
            <small>{{ auth.email || 'Not signed in' }}</small>
          </span>
        </div>
        <button class="logout-button" type="button" @click="handleLogout">
          <span>&#8629;</span> Logout
        </button>
      </div>
    </aside>
    <main class="content"><RouterView /></main>
  </div>
</template>
