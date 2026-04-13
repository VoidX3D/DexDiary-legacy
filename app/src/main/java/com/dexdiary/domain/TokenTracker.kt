package com.dexdiary.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenTracker @Inject constructor() {

    // Simple estimation: ~4 chars per token for English
    fun estimateTokens(text: String): Int {
        if (text.isEmpty()) return 0
        return (text.length / 4.0).toInt().coerceAtLeast(1)
    }

    fun calculateTotalTokens(input: String, output: String): Int {
        return estimateTokens(input) + estimateTokens(output)
    }
}
