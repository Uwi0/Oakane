package com.kakapo.oakane.presentation.model

import kotlinx.datetime.DayOfWeek

enum class ReminderDay(val title: String, val dayOfWeek: DayOfWeek) {
    SUNDAY("Sun", DayOfWeek.SUNDAY),
    MONDAY("Mon", DayOfWeek.MONDAY),
    TUESDAY("Tues", DayOfWeek.TUESDAY),
    WEDNESDAY("Wed", DayOfWeek.WEDNESDAY),
    THURSDAY("Thu", DayOfWeek.THURSDAY),
    FRIDAY("Fri", DayOfWeek.FRIDAY),
    SATURDAY("Sat", DayOfWeek.SATURDAY)
}