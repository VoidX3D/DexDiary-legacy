package com.dexdiary.domain

import com.dexdiary.data.database.dao.AchievementDao
import com.dexdiary.data.database.entities.Achievement
import com.dexdiary.data.database.entities.UserStats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementManager @Inject constructor(
    private val achievementDao: AchievementDao
) {

    suspend fun initializeAchievements() {
        val initial = listOf(
            Achievement("streak_3", "Spark", "Keep a 3-day streak", false, null, "🔥"),
            Achievement("streak_7", "Fired Up", "Keep a 7-day streak", false, null, "🌋"),
            Achievement("words_1000", "Wordsmith", "Write 1,000 words in total", false, null, "✍️"),
            Achievement("night_owl", "Night Owl", "Write an entry after 12 AM", false, null, "🦉"),
            Achievement("early_bird", "Early Bird", "Write an entry before 7 AM", false, null, "🐦"),
            Achievement("mood_diver", "Emotional Explorer", "Use all 8 moods", false, null, "🎭"),
            Achievement("dark_lord", "Dark Lord", "Unlock Dark Mode", false, null, "🌑")
        )
        achievementDao.insertAchievements(initial)
    }

    suspend fun checkAchievements(stats: UserStats) {
        if (stats.currentStreak >= 3) achievementDao.unlockAchievement("streak_3", System.currentTimeMillis())
        if (stats.currentStreak >= 7) achievementDao.unlockAchievement("streak_7", System.currentTimeMillis())
        if (stats.totalWords >= 1000) achievementDao.unlockAchievement("words_1000", System.currentTimeMillis())
        // Other checks happen in specific events
    }
}
