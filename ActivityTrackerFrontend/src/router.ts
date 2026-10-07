/**
 * Router configuration for the Activity Tracker app.
 * Defines all routes, the auth guard, and page title management.
 */
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from './stores/auth'
import LoginView from './views/LoginView.vue'
import SignupView from './views/SignupView.vue'
import DashboardView from './views/DashboardView.vue'
import AddActivityView from './views/AddActivityView.vue'
import HistoryView from './views/HistoryView.vue'
import PlaceholderView from './views/PlaceholderView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', name: 'login', component: LoginView, meta: { title: 'Log in' } },
    { path: '/signup', name: 'signup', component: SignupView, meta: { title: 'Sign up' } },
    { path: '/', redirect: '/dashboard' },
    { path: '/dashboard', name: 'dashboard', component: DashboardView, meta: { title: 'Home', requiresAuth: false } },
    { path: '/add-activity', name: 'add-activity', component: AddActivityView, meta: { title: 'Add Activity', requiresAuth: false } },
    { path: '/history', name: 'history', component: HistoryView, meta: { title: 'History', requiresAuth: false } },
    { path: '/settings', name: 'settings', component: PlaceholderView, props: { title: 'Settings', eyebrow: 'Make Activity Tracker feel like yours' }, meta: { title: 'Settings', requiresAuth: false } },
    { path: '/:pathMatch(.*)*', name: 'not-found', component: PlaceholderView, props: { title: 'Page not found', eyebrow: 'That page does not exist.' }, meta: { title: 'Not found' } },
  ],
})

/**
 * Navigation guard — redirects unauthenticated users to the login page,
 * and sends already-authenticated users away from login/signup to the dashboard.
 */
router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isAuthenticated) return { name: 'login' }
  if ((to.name === 'login' || to.name === 'signup') && auth.isAuthenticated) return { name: 'dashboard' }
})

/** Sets the browser tab title after each navigation. */
router.afterEach((to) => {
  document.title = `${String(to.meta.title ?? 'Activity Tracker')} · Activity Tracker`
})

export default router
