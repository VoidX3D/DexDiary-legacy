package com.dexdiary.domain

import com.dexdiary.data.database.entities.UserStats
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreakManager @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun calculateNewStreak(stats: UserStats, entryDate: String, isFrozen: Boolean = false): Int {
        val today = LocalDate.now()
        val lastDate = if (stats.lastEntryDate.isNotEmpty()) LocalDate.parse(stats.lastEntryDate, formatter) else null
        val currentEntryDate = LocalDate.parse(entryDate, formatter)

        if (lastDate == null) return 1

        val daysBetween = ChronoUnit.DAYS.between(lastDate, currentEntryDate)

        return when {
            daysBetween == 1L -> stats.currentStreak + 1
            daysBetween <= 0L -> stats.currentStreak // Same day or past (though UI prevents past usually)
            isFrozen -> stats.currentStreak + 1 // Protected
            else -> 1 // Broken
        }
    }

    fun isStreakBroken(lastEntryDate: String): Boolean {
        if (lastEntryDate.isEmpty()) return false
        val today = LocalDate.now()
        val lastDate = LocalDate.parse(lastEntryDate, formatter)
        val daysBetween = ChronoUnit.DAYS.between(lastDate, today)
        return daysBetween > 1
    }

    fun getStreakBonus(newStreak: Int): Int {
        return when (newStreak) {
            30 -> 250
            14 -> 100
            7 -> 50
            3 -> 20
            else -> 0
        }
    }
}
