package com.dexdiary.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(navController: NavController) {
    var morningEnabled by remember { mutableStateOf(true) }
    var nightEnabled by remember { mutableStateOf(true) }
    var desperateEnabled by remember { mutableStateOf(true) }
    var aggressiveness by remember { mutableStateOf(0.5f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications Engine") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()
        ) {
            item {
                Text("Frequency Controls", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(16.dp))
            }

            item {
                NotificationToggle("Morning Motivation", morningEnabled) { morningEnabled = it }
                NotificationToggle("Evening Reflection", nightEnabled) { nightEnabled = it }
                NotificationToggle("Desperate Unhinged Reminders", desperateEnabled) { desperateEnabled = it }
            }

            item {
                Spacer(Modifier.height(32.dp))
                Text("Aggressiveness Level", style = MaterialTheme.typography.titleMedium)
                Slider(
                    value = aggressiveness,
                    onValueChange = { aggressiveness = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = when {
                        aggressiveness < 0.3f -> "Gentle - Like a whisper"
                        aggressiveness < 0.7f -> "Normal - Helpful reminders"
                        else -> "BADASS - Duolingo is your middle name"
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun NotificationToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
