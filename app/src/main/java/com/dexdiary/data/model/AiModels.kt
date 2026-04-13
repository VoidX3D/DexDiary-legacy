package com.dexdiary.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class AiProviderType {
    GEMINI, GROQ, NVIDIA, OPENROUTER
}

@Serializable
data class AiSettings(
    val provider: AiProviderType = AiProviderType.GEMINI,
    val apiKey: String = "",
    val modelName: String = "",
    val isEnabled: Boolean = false
)

data class AiResponse(
    val content: String,
    val success: Boolean,
    val errorMessage: String? = null
)
