package com.dexdiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexdiary.data.database.entities.InventoryItem
import com.dexdiary.data.database.entities.UnlockedTheme
import com.dexdiary.data.database.entities.UserStats
import com.dexdiary.data.repository.ShopRepository
import com.dexdiary.data.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {

    val stats = statsRepository.getStats().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserStats()
    )

    val inventory = shopRepository.getInventory().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val unlockedThemes = shopRepository.getUnlockedThemes().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun buyItem(itemId: String, cost: Int, isPermanent: Boolean = false) {
        val currentStats = stats.value
        if (currentStats.availablePoints >= cost) {
            viewModelScope.launch {
                statsRepository.updateStats(currentStats.copy(
                    availablePoints = currentStats.availablePoints - cost
                ))

                if (isPermanent) {
                    shopRepository.unlockTheme(UnlockedTheme(
                        themeId = itemId,
                        unlockedAt = System.currentTimeMillis(),
                        isRental = false
                    ))
                } else {
                    val existing = shopRepository.getItem(itemId)
                    if (existing != null) {
                        shopRepository.updateItem(existing.copy(quantity = existing.quantity + 1))
                    } else {
                        shopRepository.purchaseItem(InventoryItem(
                            itemId = itemId,
                            quantity = 1,
                            purchasedAt = System.currentTimeMillis()
                        ))
                    }
                }
            }
        }
    }
}
