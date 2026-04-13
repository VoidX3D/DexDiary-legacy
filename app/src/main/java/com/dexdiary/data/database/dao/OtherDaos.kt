package com.dexdiary.data.database.dao

import androidx.room.*
import androidx.paging.PagingSource
import com.dexdiary.data.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM point_transactions ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<PointTransaction>>

    @Insert
    suspend fun insertTransaction(transaction: PointTransaction)
}

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory")
    fun getInventory(): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventory WHERE itemId = :itemId")
    suspend fun getItem(itemId: String): InventoryItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem)

    @Update
    suspend fun updateItem(item: InventoryItem)

    @Delete
    suspend fun deleteItem(item: InventoryItem)
}

@Dao
interface MissedDayDao {
    @Query("SELECT * FROM missed_days ORDER BY date DESC")
    fun getMissedDays(): Flow<List<MissedDay>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissedDay(missedDay: MissedDay)
}

@Dao
interface MediaDao {
    @Query("SELECT * FROM media WHERE entry_id = :entryId")
    fun getMediaForEntry(entryId: Long): Flow<List<Media>>

    @Insert
    suspend fun insertMedia(media: Media)

    @Query("DELETE FROM media WHERE entry_id = :entryId")
    suspend fun deleteMediaForEntry(entryId: Long)
}

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations WHERE entry_id = :entryId")
    suspend fun getLocationForEntry(entryId: Long): DiaryLocation?

    @Insert
    suspend fun insertLocation(location: DiaryLocation)
}

@Dao
interface ThemeDao {
    @Query("SELECT * FROM unlocked_themes")
    fun getUnlockedThemes(): Flow<List<UnlockedTheme>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockTheme(theme: UnlockedTheme)
}

@Dao
interface PowerUpDao {
    @Query("SELECT * FROM active_powerups WHERE expires_at > :currentTime")
    fun getActivePowerUps(currentTime: Long): Flow<List<ActivePowerUp>>

    @Insert
    suspend fun activatePowerUp(powerUp: ActivePowerUp)
}

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
