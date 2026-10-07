/**
 * Shared type definitions for activity-related data across the app.
 * These mirror the DTOs returned by the Spring Boot backend.
 */

/** A single activity entry recorded by the user. */
export interface Activity {
  /** Unique identifier from the backend. */
  id: number
  /** Display name, e.g. "Gym", "Study", "Cooking". */
  name: string
  /** Category used for color-coding in the UI. */
  category: ActivityCategory
  /** ISO date string for the day the activity was performed. */
  date: string
  /** Start time in "h:mm AM/PM" format. */
  startTime: string
  /** End time in "h:mm AM/PM" format. */
  endTime: string
  /** Whether the activity has been marked as completed. */
  completed: boolean
}

/** The five color-coded categories shown as dots throughout the UI. */
export type ActivityCategory = 'work' | 'gym' | 'study' | 'cooking' | 'other'

/** Summary statistics shown in the dashboard stat cards. */
export interface ActivityStat {
  label: string
  value: number
  caption: string
  change: string
  icon: string
}

/** A single day in the weekly overview grid. */
export interface WeekDay {
  name: string
  date: string
  count: number
  /** Whether this is the currently-selected day. */
  active?: boolean
  activities: string[]
}

/** A row in the "Today's Activity" list on the dashboard. */
export interface TodayActivity {
  name: string
  startTime: string
  endTime: string
  category: ActivityCategory
  completed: boolean
}

/** Maps categories to the dot color classes defined in CSS. */
export const categoryDotClass: Record<ActivityCategory, string> = {
  work: 'dot-0',
  gym: 'dot-1',
  study: 'dot-2',
  cooking: 'dot-3',
  other: 'dot-4',
}

/** Human-readable labels for each category, used in dropdowns and legends. */
export const categoryLabels: Record<ActivityCategory, string> = {
  work: 'Work',
  gym: 'Gym',
  study: 'Study',
  cooking: 'Cooking',
  other: 'Other',
}
