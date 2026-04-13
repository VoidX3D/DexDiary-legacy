package com.dexdiary.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateUtils {
    private val dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getTodayDate(): String = LocalDate.now().format(dbFormatter)

    fun formatDisplayDate(dateStr: String): String {
        val date = LocalDate.parse(dateStr, dbFormatter)
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    fun isToday(dateStr: String): Boolean = dateStr == getTodayDate()
}
