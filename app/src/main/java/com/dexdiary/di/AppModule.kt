package com.dexdiary.di

import android.content.Context
import androidx.room.Room
import com.dexdiary.data.database.AppDatabase
import com.dexdiary.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dex_diary_db"
        ).build()
    }

    @Provides
    fun provideEntryDao(db: AppDatabase): EntryDao = db.entryDao()

    @Provides
    fun provideStatsDao(db: AppDatabase): StatsDao = db.statsDao()

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideInventoryDao(db: AppDatabase): InventoryDao = db.inventoryDao()

    @Provides
    fun provideMissedDayDao(db: AppDatabase): MissedDayDao = db.missedDayDao()

    @Provides
    fun provideMediaDao(db: AppDatabase): MediaDao = db.mediaDao()

    @Provides
    fun provideLocationDao(db: AppDatabase): LocationDao = db.locationDao()

    @Provides
    fun provideThemeDao(db: AppDatabase): ThemeDao = db.themeDao()

    @Provides
    fun providePowerUpDao(db: AppDatabase): PowerUpDao = db.powerUpDao()

    @Provides
    fun provideRewardDao(db: AppDatabase): RewardDao = db.rewardDao()

    @Provides
    fun provideMissionDao(db: AppDatabase): MissionDao = db.missionDao()

    @Provides
    fun provideAiLogDao(db: AppDatabase): AiLogDao = db.aiLogDao()

    @Provides
    fun provideAchievementDao(db: AppDatabase): AchievementDao = db.achievementDao()
}
