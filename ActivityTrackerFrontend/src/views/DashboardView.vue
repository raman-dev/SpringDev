<script setup lang="ts">
/**
 * DashboardView — the main landing page after login.
 * Composes DashboardHeader, StatsGrid, WeekGrid, and TodayList.
 * Data is currently hard-coded; replace with API calls when the
 * Spring Boot backend is connected.
 */
import DashboardHeader from '../components/dashboard/DashboardHeader.vue'
import StatsGrid from '../components/dashboard/StatsGrid.vue'
import WeekGrid from '../components/dashboard/WeekGrid.vue'
import TodayList from '../components/dashboard/TodayList.vue'
import type { ActivityStat, WeekDay, TodayActivity } from '../types/activity'

/** Summary statistics for the three dashboard cards. */
const stats: ActivityStat[] = [
  { label: 'Daily Activity', value: 6, caption: 'activities today', change: '2 vs. yesterday', icon: '\u25A3' },
  { label: 'Weekly Activity', value: 34, caption: 'activities this week', change: '6 vs. last week', icon: '\u25A6' },
  { label: 'Monthly Activity', value: 128, caption: 'activities this month', change: '12 vs. last month', icon: '\u25A4' },
]

/** Seven-day overview grid data. */
const days: WeekDay[] = [
  { name: 'Sun', date: 'Sep 21', count: 5, activities: ['Gym', 'Study', 'Cooking', 'Reading', 'Walk'] },
  { name: 'Mon', date: 'Sep 22', count: 6, activities: ['Work', 'Gym', 'Study', 'Cooking', 'Reading', 'Walk'] },
  { name: 'Tue', date: 'Sep 23', count: 4, activities: ['Work', 'Study', 'Cooking', 'Walk'] },
  { name: 'Wed', date: 'Sep 24', count: 6, active: true, activities: ['Work', 'Gym', 'Study', 'Cooking', 'Reading', 'Walk'] },
  { name: 'Thu', date: 'Sep 25', count: 5, activities: ['Work', 'Gym', 'Study', 'Cooking', 'Walk'] },
  { name: 'Fri', date: 'Sep 26', count: 3, activities: ['Work', 'Study', 'Walk'] },
  { name: 'Sat', date: 'Sep 27', count: 4, activities: ['Gym', 'Study', 'Cooking', 'Walk'] },
]

/** Today's activity list. */
const todayActivities: TodayActivity[] = [
  { name: 'Work', startTime: '9:00 AM', endTime: '12:00 PM', category: 'work', completed: true },
  { name: 'Gym', startTime: '1:00 PM', endTime: '2:00 PM', category: 'gym', completed: true },
  { name: 'Study', startTime: '3:00 PM', endTime: '5:00 PM', category: 'study', completed: true },
]

/** Formatted date label for the TodayList header. */
const todayDateLabel = new Date().toLocaleDateString('en-US', {
  weekday: 'short',
  month: 'short',
  day: 'numeric',
})
</script>

<template>
  <div class="page dashboard-page">
    <DashboardHeader />
    <StatsGrid :stats="stats" />
    <WeekGrid :days="days" @previous-week="() => {}" @next-week="() => {}" />
    <TodayList :activities="todayActivities" :date-label="todayDateLabel" />
  </div>
</template>
