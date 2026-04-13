package com.dexdiary.data.database

import androidx.room.*
import com.dexdiary.data.database.dao.*
import com.dexdiary.data.database.entities.*

@Database(
    entities = [
        DiaryEntry::class,
        UserStats::class,
        PointTransaction::class,
        InventoryItem::class,
        MissedDay::class,
        Media::class,
        DiaryLocation::class,
        UnlockedTheme::class,
        ActivePowerUp::class,
        DailyReward::class,
        DailyMission::class,
        AiLog::class,
        Achievement::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun statsDao(): StatsDao
    abstract fun transactionDao(): TransactionDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun missedDayDao(): MissedDayDao
    abstract fun mediaDao(): MediaDao
    abstract fun locationDao(): LocationDao
    abstract fun themeDao(): ThemeDao
    abstract fun powerUpDao(): PowerUpDao
    abstract fun rewardDao(): RewardDao
    abstract fun missionDao(): MissionDao
    abstract fun aiLogDao(): AiLogDao
    abstract fun achievementDao(): AchievementDao
}
