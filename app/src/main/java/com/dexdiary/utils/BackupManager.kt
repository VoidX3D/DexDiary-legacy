package com.dexdiary.utils

import android.content.Context
import com.dexdiary.data.repository.EntryRepository
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupManager @Inject constructor(
    private val context: Context,
    private val entryRepository: EntryRepository
) {
    suspend fun createBackup(): String {
        val entries = entryRepository.getAllEntries().first()
        val json = Json.encodeToString(entries)
        
        val backupFile = File(context.getExternalFilesDir(null), "dex_diary_backup_${System.currentTimeMillis()}.json")
        backupFile.writeText(json)
        
        return backupFile.absolutePath
    }
}
