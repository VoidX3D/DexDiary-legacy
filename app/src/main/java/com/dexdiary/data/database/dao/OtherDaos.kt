package com.dexdiary.data.database.dao

import androidx.room.*
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
