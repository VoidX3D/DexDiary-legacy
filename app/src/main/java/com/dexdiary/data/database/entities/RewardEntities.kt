package com.dexdiary.data.database.entities

import androidx.room.*

@Entity(tableName = "daily_rewards")
data class DailyReward(
    @PrimaryKey val date: String, // YYYY-MM-DD
    @ColumnInfo(name = "claimed_at") val claimedAt: Long,
    @ColumnInfo(name = "reward_type") val rewardType: String, // points, freeze, powerup
    @ColumnInfo(name = "amount") val amount: Int
)

@Entity(tableName = "daily_missions")
data class DailyMission(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "target_count") val targetCount: Int,
    @ColumnInfo(name = "current_count") val currentCount: Int = 0,
    @ColumnInfo(name = "reward_points") val rewardPoints: Int,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "mission_type") val missionType: String // word_count, mood_check, tagging
)
