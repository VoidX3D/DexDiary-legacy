package com.dexdiary.presentation.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue500 = Color(0xFF2196F3)
val Blue300 = Color(0xFF64B5F6)

// Theme Colors
val SepiaBackground = Color(0xFFF4ECD8)
val SepiaSurface = Color(0xFFE8DCC8)
val SepiaPrimary = Color(0xFF8B5A2B)

val OceanBackground = Color(0xFFE8F4F8)
val OceanSurface = Color(0xFFD0E8F0)
val OceanPrimary = Color(0xFF0077BE)

val ForestBackground = Color(0xFFE8F0E8)
val ForestSurface = Color(0xFFD0E0D0)
val ForestPrimary = Color(0xFF2E7D32)

val EmberBackground = Color(0xFFF8E8E8)
val EmberSurface = Color(0xFFF0D0D0)
val EmberPrimary = Color(0xFFD84315)

val MidnightBackground = Color(0xFFE8E0F0)
val MidnightSurface = Color(0xFFD0C8E0)
val MidnightPrimary = Color(0xFF6A1B9A)

val FrostBackground = Color(0xFFE8F0F0)
val FrostSurface = Color(0xFFD0E0E0)
val FrostPrimary = Color(0xFF00838F)

@Composable
fun DexDiaryTheme(
    themeId: String = "Light",
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeId) {
        "Dark" -> darkColorScheme(primary = Blue300, background = Color(0xFF121212), surface = Color(0xFF1E1E1E))
        "Sepia" -> lightColorScheme(primary = SepiaPrimary, background = SepiaBackground, surface = SepiaSurface)
        "Ocean" -> lightColorScheme(primary = OceanPrimary, background = OceanBackground, surface = OceanSurface)
        "Forest" -> lightColorScheme(primary = ForestPrimary, background = ForestBackground, surface = ForestSurface)
        "Ember" -> lightColorScheme(primary = EmberPrimary, background = EmberBackground, surface = EmberSurface)
        "Midnight" -> lightColorScheme(primary = MidnightPrimary, background = MidnightBackground, surface = MidnightSurface)
        "Frost" -> lightColorScheme(primary = FrostPrimary, background = FrostBackground, surface = FrostSurface)
        else -> lightColorScheme(primary = Blue500, background = Color.White, surface = Color(0xFFF5F5F5))
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
