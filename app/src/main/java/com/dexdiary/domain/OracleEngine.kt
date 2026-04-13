package com.dexdiary.domain

import com.dexdiary.data.database.entities.DiaryEntry
import com.dexdiary.data.model.AiSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OracleEngine @Inject constructor(
    private val aiEngine: AiEngine
) {

    suspend fun consultOracle(recentEntries: List<DiaryEntry>): String {
        if (recentEntries.isEmpty()) return "The future is a blank page. Start writing to see its shape."
        
        val context = recentEntries.take(5).joinToString("\n") { "${it.date}: ${it.mood}" }
        val prompt = "Based on these recent moods and patterns, predict the user's emotional vibe for tomorrow and give one piece of short, profound advice: $context"
        
        val response = aiEngine.generateSummary(prompt, AiSettings(isEnabled = true, apiKey = "MOCK_KEY"))
        return if (response.success) response.content else "The stars are cloudy. Try again tomorrow."
    }
}
