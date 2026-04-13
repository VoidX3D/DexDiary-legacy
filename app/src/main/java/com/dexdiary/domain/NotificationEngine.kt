package com.dexdiary.domain

import com.dexdiary.data.database.entities.UserStats
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

sealed class NotificationMessage(val title: String, val body: String)

@Singleton
class NotificationEngine @Inject constructor() {

    private val morningReminders = listOf(
        Pair("🌅 New Day, New Log", "Your morning coffee is ready, and so is your diary."),
        Pair("☀️ Rise and Write", "Log your intentions for today!"),
        Pair("☕ Fresh Canvas", "A new day awaits your thoughts."),
        Pair("🌄 Morning Glory", "Did you dream of success? Write it down."),
        Pair("🐤 Early Bird Gets the Pts", "Log now for an early bird bonus!"),
        Pair("🌞 Awake yet?", "Your diary is lonelier than your inbox.")
    )

    private val nightReminders = listOf(
        Pair("🌙 Day in Review", "How was today? Don't let the memories fade."),
        Pair("🛌 Bedtime Thoughts", "Unburden your mind before sleep."),
        Pair("🌌 Starry Night", "Log your final thoughts for today."),
        Pair("🦉 Night Owl?", "Perfect time for some deep reflection."),
        Pair("💤 Sleep Well", "But write first. Your future self will thank you."),
        Pair("📖 Chapter Closed", "Ready to write the end of today's chapter?")
    )

    private val desperateReminders = listOf(
        Pair("🔥 STREAK AT RISK!", "Duolingo style: Write now or say goodbye to your fire!"),
        Pair("⚠️ 1 HOUR LEFT", "Your streak is begging for an entry."),
        Pair("💀 DON'T STOP NOW", "You've come so far. Don't let it end today."),
        Pair("😡 I'M DISAPPOINTED", "Is a 5-minute entry really too much to ask?"),
        Pair("💔 STREAK SUICIDE?", "Don't let your hard work go to waste."),
        Pair("🚨 EMERGENCY", "Your fire is flickering out. SAVE IT NOW."),
        Pair("🔪 STREAK ON THE EDGE", "One more hour and the fire dies. Your choice."),
        Pair("🎭 DRAMA!", "Your streak is literally crying in the corner right now.")
    )

    fun getTimelyNotification(stats: UserStats): Pair<String, String> {
        val now = LocalTime.now()
        
        return when {
            stats.currentStreak >= 3 && now.hour >= 21 -> desperateReminders.random()
            now.hour < 12 -> morningReminders.random()
            else -> nightReminders.random()
        }
    }
}
