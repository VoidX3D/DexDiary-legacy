package com.dexdiary.data.database.entities

import androidx.room.*

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_unlocked") val isUnlocked: Boolean = false,
    @ColumnInfo(name = "unlocked_at") val unlockedAt: Long? = null,
    @ColumnInfo(name = "icon") val icon: String
)

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): kotlinx.coroutines.flow.Flow<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAchievements(achievements: List<Achievement>)

    @Query("UPDATE achievements SET is_unlocked = 1, unlocked_at = :timestamp WHERE id = :id")
    suspend fun unlockAchievement(id: String, timestamp: Long)
}
