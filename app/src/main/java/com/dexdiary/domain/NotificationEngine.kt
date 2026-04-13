package com.dexdiary.domain

import com.dexdiary.data.database.entities.UserStats
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

sealed class NotificationMessage(val title: String, val body: String)

@Singleton
class NotificationEngine @Inject constructor() {

    private val openers = listOf("Hey!", "Wait,", "Excuse me?", "Psst...", "Listen,", "Attention!")
    
    private val morningCore = listOf(
        "the sun is up and your diary is empty.",
        "it's time to log your morning intentions.",
        "don't let your morning coffee be the only productive thing you do today.",
        "a new canvas awaits your thoughts."
    )

    private val nightCore = listOf(
        "the day is ending. How was it?",
        "reflect on today before the memories fade.",
        "your future self wants to know what happened today.",
        "unburden your mind before you sleep."
    )

    private val desperateCore = listOf(
        "your streak is literally dying.",
        "don't let your fire go out now.",
        "is 30 seconds of writing really that hard?",
        "you've worked too hard to lose it all now."
    )

    private val suffixes = listOf(
        "Write now.",
        "Don't wait.",
        "Just one sentence.",
        "Go go go!",
        "Accountability is key.",
        "Your streak depends on it.",
        "Make it happen."
    )

    fun getTimelyNotification(stats: UserStats): Pair<String, String> {
        val now = LocalTime.now()
        val opener = openers.random()
        val suffix = suffixes.random()
        
        return when {
            stats.currentStreak >= 3 && now.hour >= 21 -> {
                Pair("🚨 EMERGENCY", "$opener ${desperateCore.random()} $suffix")
            }
            now.hour < 12 -> {
                Pair("🌅 Morning Ritual", "$opener ${morningCore.random()} $suffix")
            }
            else -> {
                Pair("🌙 Nightly Reflection", "$opener ${nightCore.random()} $suffix")
            }
        }
    }
}
