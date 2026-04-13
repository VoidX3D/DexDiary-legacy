package com.dexdiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexdiary.data.database.entities.DiaryEntry
import com.dexdiary.data.database.entities.UserStats
import com.dexdiary.data.repository.EntryRepository
import com.dexdiary.data.repository.RewardRepository
import com.dexdiary.data.repository.StatsRepository
import com.dexdiary.data.database.entities.DailyReward
import com.dexdiary.domain.RewardManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
    private val statsRepository: StatsRepository,
    private val rewardRepository: RewardRepository,
    private val rewardManager: RewardManager
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val stats = statsRepository.getStats().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserStats()
    )

    val recentEntries = entryRepository.getAllEntries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _dailyRewardClaimed = MutableStateFlow(true)
    val dailyRewardClaimed = _dailyRewardClaimed.asStateFlow()

    val dailyMissions = rewardRepository.getMissionsForDate(LocalDate.now().format(formatter))
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            if (statsRepository.getStatsSync() == null) {
                statsRepository.insertStats(UserStats())
            }
            
            val today = LocalDate.now().format(formatter)
            _dailyRewardClaimed.value = rewardRepository.getRewardForDate(today) != null
            
            val missionsToday = rewardRepository.getMissionsForDate(today).first()
            if (missionsToday.isEmpty()) {
                rewardRepository.insertMissions(rewardManager.generateDailyMissions())
            }
        }
    }

    fun claimDailyReward() {
        val today = LocalDate.now().format(formatter)
        viewModelScope.launch {
            val (type, amount) = rewardManager.getRandomGift()
            rewardRepository.insertReward(DailyReward(today, System.currentTimeMillis(), type, amount))
            
            val statsVal = stats.value
            if (type == "points") {
                statsRepository.updateStats(statsVal.copy(availablePoints = statsVal.availablePoints + amount))
            } else if (type == "freeze") {
                // TODO: Update inventory
            }
            
            _dailyRewardClaimed.value = true
        }
    }

    fun isTodayMissed(): Boolean {
        val today = LocalDate.now().format(formatter)
        val lastEntry = stats.value.lastEntryDate
        return lastEntry != today
    }

    fun deleteEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            entryRepository.deleteEntry(entry)
        }
    }
}
