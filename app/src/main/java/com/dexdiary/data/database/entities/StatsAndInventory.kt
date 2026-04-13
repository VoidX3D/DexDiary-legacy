package com.dexdiary.data.database.entities

import androidx.room.*

@Entity(tableName = "user_stats")
data class UserStats(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "total_points") val totalPoints: Int = 0,
    @ColumnInfo(name = "available_points") val availablePoints: Int = 0,
    @ColumnInfo(name = "current_streak") val currentStreak: Int = 0,
    @ColumnInfo(name = "longest_streak") val longestStreak: Int = 0,
    @ColumnInfo(name = "last_entry_date") val lastEntryDate: String = "",
    @ColumnInfo(name = "total_words") val totalWords: Int = 0,
    @ColumnInfo(name = "total_entries") val totalEntries: Int = 0,
    @ColumnInfo(name = "total_images") val totalImages: Int = 0,
    @ColumnInfo(name = "total_audio") val totalAudio: Int = 0,
    @ColumnInfo(name = "total_locations") val totalLocations: Int = 0
)

@Entity(tableName = "point_transactions")
data class PointTransaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "type") val type: String, // write, streak_bonus, word_bonus, etc.
    @ColumnInfo(name = "points") val points: Int,
    @ColumnInfo(name = "multiplier") val multiplier: Float,
    @ColumnInfo(name = "note") val note: String? = null
)

@Entity(tableName = "inventory")
data class InventoryItem(
    @PrimaryKey val itemId: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "purchased_at") val purchasedAt: Long,
    @ColumnInfo(name = "expires_at") val expiresAt: Long? = null
)
