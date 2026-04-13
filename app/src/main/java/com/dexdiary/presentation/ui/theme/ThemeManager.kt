package com.dexdiary.presentation.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.dexdiary.data.repository.ShopRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

typealias BooleanDefault = Boolean

@Singleton
class ThemeManager @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private val _currentTheme = mutableStateOf("Light")
    val currentTheme: State<String> = _currentTheme

    fun setTheme(themeId: String) {
        _currentTheme.value = themeId
    }

    fun getUnlockedThemes() = shopRepository.getUnlockedThemes()
}

enum class DexTheme(
    val id: String,
    val displayName: String,
    val cost: Int,
    val isLocked: BooleanDefault = true
) {
    LIGHT("Light", "Light", 0, false),
    DARK("Dark", "Dark Mode", 5000, true),
    SEPIA("Sepia", "Sepia", 500, true),
    OCEAN("Ocean", "Ocean", 800, true),
    FOREST("Forest", "Forest", 800, true),
    EMBER("Ember", "Ember", 1500, true),
    MIDNIGHT("Midnight", "Midnight", 1200, true),
    FROST("Frost", "Frost", 1500, true)
}
