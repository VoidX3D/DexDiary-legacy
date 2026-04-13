package com.dexdiary.domain

import javax.inject.Inject
import javax.inject.Singleton

data class TextAnalysis(
    val wordCount: Int,
    val charCount: Int,
    val letterCount: Int,
    val sentenceCount: Int,
    val paragraphCount: Int,
    val readingTime: Int,
    val grammarTips: List<String>
)

@Singleton
class TextAnalyzer @Inject constructor() {

    fun analyze(text: String): TextAnalysis {
        val words = text.split("\\s+".toRegex()).filter { it.isNotEmpty() }
        val chars = text.length
        val letters = text.filter { it.isLetter() }.length
        val sentences = text.split("[.!?]+".toRegex()).filter { it.trim().isNotEmpty() }.size
        val paragraphs = text.split("\n+".toRegex()).filter { it.trim().isNotEmpty() }.size
        
        val grammarTips = mutableListOf<String>()
        if (text.isNotEmpty() && !text[0].isUpperCase()) {
            grammarTips.add("Try starting your first sentence with a capital letter.")
        }
        if (text.isNotEmpty() && !text.endsWith(".") && !text.endsWith("!") && !text.endsWith("?")) {
            grammarTips.add("Consider ending your entry with proper punctuation.")
        }
        if (words.size > 20 && sentences == 1) {
            grammarTips.add("Long sentence detected. Maybe split it for better flow?")
        }

        return TextAnalysis(
            wordCount = words.size,
            charCount = chars,
            letterCount = letters,
            sentenceCount = sentences,
            paragraphCount = paragraphs,
            readingTime = (words.size / 200).coerceAtLeast(1),
            grammarTips = grammarTips
        )
    }
}
