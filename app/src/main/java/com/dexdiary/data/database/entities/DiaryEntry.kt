package com.dexdiary.data.database.entities

import androidx.room.*

@Entity(tableName = "entries")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String, // YYYY-MM-DD
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "mood") val mood: String,
    @ColumnInfo(name = "tags") val tags: String, // comma-separated
    @ColumnInfo(name = "word_count") val wordCount: Int,
    @ColumnInfo(name = "char_count") val charCount: Int,
    @ColumnInfo(name = "reading_time") val readingTime: Int,
    @ColumnInfo(name = "has_image") val hasImage: Boolean,
    @ColumnInfo(name = "has_audio") val hasAudio: Boolean,
    @ColumnInfo(name = "has_location") val hasLocation: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "is_excused") val isExcused: Boolean = false,
    @ColumnInfo(name = "excuse_text") val excuseText: String? = null
)
