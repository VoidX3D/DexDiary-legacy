package com.dexdiary.data.database.entities

import androidx.room.*

@Entity(tableName = "ai_logs")
data class AiLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "provider") val provider: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "prompt_type") val promptType: String, // summary, grammar, mission_gen
    @ColumnInfo(name = "input_tokens") val inputTokens: Int,
    @ColumnInfo(name = "output_tokens") val outputTokens: Int,
    @ColumnInfo(name = "total_tokens") val totalTokens: Int,
    @ColumnInfo(name = "processing_time_ms") val processingTimeMs: Long,
    @ColumnInfo(name = "status") val status: String // success, failure
)
