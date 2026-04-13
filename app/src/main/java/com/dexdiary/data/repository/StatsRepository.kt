package com.dexdiary.data.repository

import com.dexdiary.data.database.dao.StatsDao
import com.dexdiary.data.database.dao.TransactionDao
import com.dexdiary.data.database.entities.PointTransaction
import com.dexdiary.data.database.entities.UserStats
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatsRepository @Inject constructor(
    private val statsDao: StatsDao,
    private val transactionDao: TransactionDao
) {
    fun getStats(): Flow<UserStats?> = statsDao.getStats()

    suspend fun getStatsSync(): UserStats? = statsDao.getStatsSync()

    suspend fun updateStats(stats: UserStats) = statsDao.updateStats(stats)

    suspend fun insertStats(stats: UserStats) = statsDao.insertStats(stats)

    fun getAllTransactions(): Flow<List<PointTransaction>> = transactionDao.getAllTransactions()

    suspend fun addTransaction(transaction: PointTransaction) = transactionDao.insertTransaction(transaction)
}
