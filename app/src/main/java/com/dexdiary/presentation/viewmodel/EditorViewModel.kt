package com.dexdiary.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexdiary.data.database.entities.DiaryEntry
import com.dexdiary.data.database.entities.PointTransaction
import com.dexdiary.data.database.entities.UserStats
import com.dexdiary.data.repository.EntryRepository
import com.dexdiary.data.repository.RewardRepository
import com.dexdiary.data.repository.StatsRepository
import com.dexdiary.data.database.entities.DailyReward
import com.dexdiary.data.model.AiSettings
import com.dexdiary.domain.AiEngine
import com.dexdiary.domain.PointsCalculator
import com.dexdiary.domain.RewardManager
import com.dexdiary.domain.StreakManager
import com.dexdiary.domain.TextAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
    private val statsRepository: StatsRepository,
    private val rewardRepository: RewardRepository,
    private val pointsCalculator: PointsCalculator,
    private val streakManager: StreakManager,
    private val aiEngine: AiEngine,
    private val textAnalyzer: TextAnalyzer
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var mood by mutableStateOf("happy")
    var tags by mutableStateOf("")
    var isSaving by mutableStateOf(false)
    var entryDate by mutableStateOf("")

    var aiSummary by mutableStateOf("")
    var isAiLoading by mutableStateOf(false)
    var grammarTips by mutableStateOf<List<String>>(emptyList())
    var analysis by mutableStateOf<com.dexdiary.domain.TextAnalysis?>(null)

    fun onContentChanged(newContent: String) {
        content = newContent
        val result = textAnalyzer.analyze(newContent)
        analysis = result
        grammarTips = result.grammarTips
    }

    fun generateAiSummary() {
        if (content.isBlank()) return
        isAiLoading = true
        viewModelScope.launch {
            val response = aiEngine.generateSummary(content, AiSettings(isEnabled = true, apiKey = "MOCK_KEY"))
            if (response.success) {
                aiSummary = response.content
            }
            isAiLoading = false
        }
    }

    fun loadEntry(date: String?) {
        val targetDate = date ?: LocalDate.now().format(formatter)
        entryDate = targetDate
        viewModelScope.launch {
            val entry = entryRepository.getEntryByDate(targetDate)
            if (entry != null) {
                title = entry.title
                content = entry.content
                mood = entry.mood
                tags = entry.tags
            }
        }
    }

    fun saveEntry(onComplete: () -> Unit) {
        if (isSaving) return
        isSaving = true

        viewModelScope.launch {
            val wordCount = content.split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
            val charCount = content.length
            val readingTime = (wordCount / 200).coerceAtLeast(1)
            
            val stats = statsRepository.getStatsSync() ?: UserStats()
            
            val isNewEntry = entryRepository.getEntryByDate(entryDate) == null
            
            val entry = DiaryEntry(
                date = entryDate,
                title = title,
                content = content,
                mood = mood,
                tags = tags,
                wordCount = wordCount,
                charCount = charCount,
                readingTime = readingTime,
                hasImage = false, // TODO: Media
                hasAudio = false,
                hasLocation = false,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            entryRepository.insertEntry(entry)

            if (isNewEntry) {
                // Calculate points and streak
                val newStreak = streakManager.calculateNewStreak(stats, entryDate)
                val earnedPoints = pointsCalculator.calculatePoints(
                    wordCount = wordCount,
                    hasImage = false,
                    hasAudio = false,
                    hasLocation = false,
                    streak = newStreak,
                    isDoublePointsActive = false, // TODO: Power-ups
                    isExcuseUsed = false
                )

                val newTotalPoints = stats.totalPoints + earnedPoints
                val newAvailablePoints = stats.availablePoints + earnedPoints
                
                statsRepository.upsertStats(stats.copy(
                    totalPoints = newTotalPoints,
                    availablePoints = newAvailablePoints,
                    currentStreak = newStreak,
                    longestStreak = maxOf(stats.longestStreak, newStreak),
                    lastEntryDate = entryDate,
                    totalWords = stats.totalWords + wordCount,
                    totalEntries = stats.totalEntries + 1
                ))

                statsRepository.addTransaction(PointTransaction(
                    date = entryDate,
                    type = "write",
                    points = earnedPoints,
                    multiplier = pointsCalculator.getStreakMultiplier(newStreak),
                    note = "Daily Entry"
                ))
            }
            
            val missions = rewardRepository.getMissionsForDate(entryDate).first()
            missions.forEach { mission: com.dexdiary.data.database.entities.DailyMission ->
                var updatedMission = mission
                when (mission.missionType) {
                    "word_count" -> {
                        updatedMission = mission.copy(currentCount = wordCount)
                    }
                    "mood_check" -> {
                        if (mood != "happy") updatedMission = mission.copy(currentCount = 1)
                    }
                    "tagging" -> {
                        val tagCount = tags.split("""[\s,]+""".toRegex()).filter { it.isNotEmpty() }.size
                        updatedMission = mission.copy(currentCount = tagCount)
                    }
                }
                
                if (updatedMission.currentCount >= updatedMission.targetCount && !updatedMission.isCompleted) {
                    updatedMission = updatedMission.copy(isCompleted = true)
                    // Grant mission rewards
                    val currentStats = statsRepository.getStatsSync() ?: UserStats()
                    statsRepository.updateStats(currentStats.copy(
                        availablePoints = currentStats.availablePoints + updatedMission.rewardPoints
                    ))
                    statsRepository.addTransaction(PointTransaction(
                        date = entryDate,
                        type = "mission",
                        points = updatedMission.rewardPoints,
                        multiplier = 1.0f,
                        note = "Mission: ${updatedMission.title}"
                    ))
                }
                rewardRepository.updateMission(updatedMission)
            }

            isSaving = false
            onComplete()
        }
    }
}
