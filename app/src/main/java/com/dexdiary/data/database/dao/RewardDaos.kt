package com.dexdiary.data.database.dao

import androidx.room.*
import com.dexdiary.data.database.entities.DailyReward
import com.dexdiary.data.database.entities.DailyMission
import kotlinx.coroutines.flow.Flow

@Dao
interface RewardDao {
    @Query("SELECT * FROM daily_rewards WHERE date = :date")
    suspend fun getRewardForDate(date: String): DailyReward?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: DailyReward)
}

@Dao
interface MissionDao {
    @Query("SELECT * FROM daily_missions WHERE date = :date")
    fun getMissionsForDate(date: String): Flow<List<DailyMission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissions(missions: List<DailyMission>)

    @Update
    suspend fun updateMission(mission: DailyMission)
}
