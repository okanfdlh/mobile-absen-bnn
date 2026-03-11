package com.example.absenbnn.util

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

object DateKeys {
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun todayString(): String = LocalDate.now().format(dateFormatter)

    fun parseDate(dateString: String): LocalDate = LocalDate.parse(dateString, dateFormatter)

    fun monthKey(date: LocalDate): String = YearMonth.from(date).toString()

    fun monthKeyFromDateString(dateString: String): String = monthKey(parseDate(dateString))

    fun year(date: LocalDate): Int = date.year

    fun monthNumber(date: LocalDate): Int = date.monthValue

    fun weekKeyIso(date: LocalDate): String {
        val wf = WeekFields.ISO
        val week = date.get(wf.weekOfWeekBasedYear())
        val weekYear = date.get(wf.weekBasedYear())
        return String.format(Locale.US, "%04d-W%02d", weekYear, week)
    }

    fun weekKeyIsoFromDateString(dateString: String): String = weekKeyIso(parseDate(dateString))
}
