package com.dexdiary.domain

import com.dexdiary.data.database.entities.DailyMission
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RewardManager @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun generateDailyMissions(): List<DailyMission> {
        val today = LocalDate.now().format(formatter)
        return listOf(
            DailyMission(
                date = today,
                title = "Deep Thinker",
                description = "Write more than 300 words today",
                targetCount = 300,
                rewardPoints = 25,
                missionType = "word_count"
            ),
            DailyMission(
                date = today,
                title = "Mood Explorer",
                description = "Select a mood other than 'Happy'",
                targetCount = 1,
                rewardPoints = 15,
                missionType = "mood_check"
            ),
            DailyMission(
                date = today,
                title = "Socialite",
                description = "Add at least 3 tags to your entry",
                targetCount = 3,
                rewardPoints = 10,
                missionType = "tagging"
            )
        )
    }

    fun getRandomGift(): Pair<String, Int> {
        val type = listOf("points", "points", "points", "freeze", "powerup").random()
        val amount = when (type) {
            "points" -> Random.nextInt(20, 101)
            "freeze" -> 1
            "powerup" -> 1
            else -> 0
        }
        return Pair(type, amount)
    }
}
