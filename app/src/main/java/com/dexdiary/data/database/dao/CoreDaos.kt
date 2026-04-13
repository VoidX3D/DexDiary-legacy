package com.dexdiary.data.database.dao

import androidx.room.*
import com.dexdiary.data.database.entities.DiaryEntry
import com.dexdiary.data.database.entities.UserStats
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>

    @Query("SELECT * FROM entries WHERE date = :date")
    suspend fun getEntryByDate(date: String): DiaryEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: DiaryEntry): Long

    @Delete
    suspend fun deleteEntry(entry: DiaryEntry)

    @Query("SELECT * FROM entries WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchEntries(query: String): Flow<List<DiaryEntry>>
}

@Dao
interface StatsDao {
    @Query("SELECT * FROM user_stats WHERE id = 1")
    fun getStats(): Flow<UserStats?>

    @Query("SELECT * FROM user_stats WHERE id = 1")
    suspend fun getStatsSync(): UserStats?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(stats: UserStats)

    @Update
    suspend fun updateStats(stats: UserStats)

    @Upsert
    suspend fun upsertStats(stats: UserStats)
}
