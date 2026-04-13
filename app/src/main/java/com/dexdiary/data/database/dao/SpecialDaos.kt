package com.dexdiary.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.dexdiary.data.database.entities.AiLog
import com.dexdiary.data.database.entities.Achievement
import kotlinx.coroutines.flow.Flow

@Dao
interface AiLogDao {
    @Query("SELECT * FROM ai_logs ORDER BY timestamp DESC")
    fun getAllLogs(): PagingSource<Int, AiLog>

    @Query("SELECT * FROM ai_logs ORDER BY timestamp DESC LIMIT 10")
    fun getRecentLogs(): Flow<List<AiLog>>

    @Insert
    suspend fun insertLog(log: AiLog)

    @Query("SELECT SUM(total_tokens) FROM ai_logs")
    suspend fun getTotalTokensUsed(): Long
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAchievements(achievements: List<Achievement>)

    @Query("UPDATE achievements SET is_unlocked = 1, unlocked_at = :timestamp WHERE id = :id")
    suspend fun unlockAchievement(id: String, timestamp: Long)
}
