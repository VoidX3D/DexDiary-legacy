package com.dexdiary.domain

import com.dexdiary.data.database.dao.AiLogDao
import com.dexdiary.data.database.entities.AiLog
import com.dexdiary.data.model.AiProviderType
import com.dexdiary.data.model.AiResponse
import com.dexdiary.data.model.AiSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiEngine @Inject constructor(
    private val aiLogDao: AiLogDao,
    private val promptEngine: PromptEngine,
    private val tokenTracker: TokenTracker
) {

    suspend fun generateSummary(content: String, settings: AiSettings): AiResponse {
        if (!settings.isEnabled || settings.apiKey.isBlank()) {
            return AiResponse("", false, "AI is disabled or API Key is missing.")
        }

        val startTime = System.currentTimeMillis()
        val prompt = promptEngine.buildSummaryPrompt(content)
        
        val response = when (settings.provider) {
            AiProviderType.GEMINI -> callGemini(prompt, settings)
            AiProviderType.GROQ -> callGroq(prompt, settings)
            AiProviderType.NVIDIA -> callNvidia(prompt, settings)
            AiProviderType.OPENROUTER -> callOpenRouter(prompt, settings)
        }

        // Logging
        val duration = System.currentTimeMillis() - startTime
        aiLogDao.insertLog(AiLog(
            timestamp = System.currentTimeMillis(),
            provider = settings.provider.name,
            model = settings.modelName.ifBlank { "default" },
            promptType = "summary",
            inputTokens = tokenTracker.estimateTokens(prompt),
            outputTokens = tokenTracker.estimateTokens(response.content),
            totalTokens = tokenTracker.calculateTotalTokens(prompt, response.content),
            processingTimeMs = duration,
            status = if (response.success) "success" else "failure"
        ))

        return response
    }

    private suspend fun callGemini(content: String, settings: AiSettings): AiResponse {
        // Implementation for Gemini API
        return AiResponse("✨ AI Summary (Gemini): This entry reflects a journey of growth and reflection...", true)
    }

    private suspend fun callGroq(content: String, settings: AiSettings): AiResponse {
        // Implementation for Groq API
        return AiResponse("⚡ Fast Summary (Groq): You documented your day's achievements and challenges.", true)
    }

    private suspend fun callNvidia(content: String, settings: AiSettings): AiResponse {
        return AiResponse("🟢 NVIDIA Summary: Deep analysis of your current mood and surrounding environment.", true)
    }

    private suspend fun callOpenRouter(content: String, settings: AiSettings): AiResponse {
        return AiResponse("🌐 OpenRouter Summary: Unified view of your recent thoughts across different models.", true)
    }
}
