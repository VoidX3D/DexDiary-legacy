package com.dexdiary.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointsCalculator @Inject constructor() {

    fun calculatePoints(
        wordCount: Int,
        hasImage: Boolean,
        hasAudio: Boolean,
        hasLocation: Boolean,
        streak: Int,
        isDoublePointsActive: Boolean,
        isExcuseUsed: Boolean
    ): Int {
        var basePoints = 0
        
        // Write any entry
        basePoints += 10
        
        // Word count bonuses
        if (wordCount >= 1000) basePoints += 25
        else if (wordCount >= 500) basePoints += 10
        else if (wordCount >= 250) basePoints += 5
        
        // Media bonuses
        if (hasImage) basePoints += 5
        if (hasAudio) basePoints += 5
        if (hasLocation) basePoints += 3
        
        // Excuse bonus
        if (isExcuseUsed) basePoints += 5

        // Multiplier from streak
        val streakMultiplier = when {
            streak >= 30 -> 3.0f
            streak >= 14 -> 2.5f
            streak >= 7 -> 2.0f
            streak >= 3 -> 1.5f
            else -> 1.0f
        }

        var total = (basePoints * streakMultiplier).toInt()

        // Double points power-up
        if (isDoublePointsActive) {
            total *= 2
        }

        return total
    }

    fun getStreakMultiplier(streak: Int): Float {
        return when {
            streak >= 30 -> 3.0f
            streak >= 14 -> 2.5f
            streak >= 7 -> 2.0f
            streak >= 3 -> 1.5f
            else -> 1.0f
        }
    }
}
