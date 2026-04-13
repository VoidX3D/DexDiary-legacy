package com.dexdiary.data.database.entities

import androidx.room.*

@Entity(tableName = "missed_days")
data class MissedDay(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "excuse_text") val excuseText: String,
    @ColumnInfo(name = "excused_at") val excusedAt: Long,
    @ColumnInfo(name = "freeze_used") val freezeUsed: Boolean
)

@Entity(tableName = "media")
data class Media(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "entry_id") val entryId: Long,
    @ColumnInfo(name = "type") val type: String, // image, audio
    @ColumnInfo(name = "uri") val uri: String,
    @ColumnInfo(name = "thumbnail_uri") val thumbnailUri: String? = null
)

@Entity(tableName = "locations")
data class DiaryLocation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "entry_id") val entryId: Long,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "place_name") val placeName: String
)

@Entity(tableName = "unlocked_themes")
data class UnlockedTheme(
    @PrimaryKey val themeId: String,
    @ColumnInfo(name = "unlocked_at") val unlockedAt: Long,
    @ColumnInfo(name = "is_rental") val isRental: Boolean,
    @ColumnInfo(name = "expires_at") val expiresAt: Long? = null
)

@Entity(tableName = "active_powerups")
data class ActivePowerUp(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "activated_at") val activatedAt: Long,
    @ColumnInfo(name = "expires_at") val expiresAt: Long
)
