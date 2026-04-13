package com.dexdiary.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptEngine @Inject constructor() {

    fun buildSummaryPrompt(content: String): String {
        return """
            Task: Summarize the following diary entry into a single, poetic, and reflective sentence.
            Tone: Emotional, supportive, and insightful.
            Content: "$content"
        """.trimIndent()
    }

    fun buildGrammarPrompt(content: String): String {
        return """
            Task: Analyze the following text for grammar and spelling issues. 
            Provide 3 brief, friendly tips for improvement.
            Content: "$content"
        """.trimIndent()
    }

    fun buildMissionGeneratorPrompt(userStats: String): String {
        return """
            Task: Generate 3 unique daily challenges for a diary user based on their stats.
            Stats: $userStats
        """.trimIndent()
    }
}
