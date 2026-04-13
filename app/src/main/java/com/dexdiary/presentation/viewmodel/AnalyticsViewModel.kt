package com.dexdiary.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexdiary.data.repository.EntryRepository
import com.dexdiary.domain.OracleEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
    private val oracleEngine: OracleEngine
) : ViewModel() {

    var oraclePrediction by mutableStateOf("Consulting the stars...")
    var isOracleLoading by mutableStateOf(false)

    fun consultOracle() {
        isOracleLoading = true
        viewModelScope.launch {
            val entries = entryRepository.getAllEntries().first()
            oraclePrediction = oracleEngine.consultOracle(entries)
            isOracleLoading = false
        }
    }
}
