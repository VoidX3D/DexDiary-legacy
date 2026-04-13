package com.dexdiary.data.repository

import com.dexdiary.data.database.dao.EntryDao
import com.dexdiary.data.database.entities.DiaryEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepository @Inject constructor(
    private val entryDao: EntryDao
) {
    fun getAllEntries(): Flow<List<DiaryEntry>> = entryDao.getAllEntries()

    suspend fun getEntryByDate(date: String): DiaryEntry? = entryDao.getEntryByDate(date)

    suspend fun insertEntry(entry: DiaryEntry): Long = entryDao.insertEntry(entry)

    suspend fun deleteEntry(entry: DiaryEntry) = entryDao.deleteEntry(entry)

    fun searchEntries(query: String): Flow<List<DiaryEntry>> = entryDao.searchEntries(query)
}
