package com.kakapo.oakane.presentation.viewModel.reports.model

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class MonthReport(val monthNumber: Int, val title: String, val description: String) {
    JAN(1, "Jan", "January"),
    FEB(2, "Feb", "February"),
    MAR(3, "Mar", "March"),
    APR(4, "Apr", "April"),
    MAY(5, "May", "May"),
    JUN(6, "Jun", "June"),
    JUL(7, "Jul", "July"),
    AUG(8, "Aug", "August"),
    SEP(9, "Sep", "September"),
    OCT(10, "Oct", "October"),
    NOV(11, "Nov", "November"),
    DEC(12, "Dec", "December");

    fun toNumberString(): String {
        return when(this.monthNumber) {
            in 1 .. 9 -> "0${this.monthNumber}"
            else -> this.monthNumber.toString()
        }
    }
}

fun currentMonth(): MonthReport {
    val currentMonth = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).monthNumber
    return MonthReport.entries.find { it.monthNumber == currentMonth } ?: MonthReport.JAN
}

fun Int.toMonthReport(): MonthReport {
    return MonthReport.entries.find { it.monthNumber == this } ?: MonthReport.JAN
}