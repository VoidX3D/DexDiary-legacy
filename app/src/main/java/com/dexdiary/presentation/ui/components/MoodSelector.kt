package com.dexdiary.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MoodItem(val id: String, val icon: String)

@Composable
fun MoodSelector(
    selectedMood: String,
    onMoodSelected: (String) -> Unit
) {
    val moods = listOf(
        MoodItem("happy", "😊"),
        MoodItem("sad", "😢"),
        MoodItem("angry", "😡"),
        MoodItem("tired", "😫"),
        MoodItem("love", "😍"),
        MoodItem("cool", "😎"),
        MoodItem("confused", "😕"),
        MoodItem("grateful", "🙏")
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(moods) { mood ->
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onMoodSelected(mood.id) }
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (selectedMood == mood.id) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium
                    ) {}
                }
                Text(text = mood.icon, fontSize = 24.sp)
            }
        }
    }
}
