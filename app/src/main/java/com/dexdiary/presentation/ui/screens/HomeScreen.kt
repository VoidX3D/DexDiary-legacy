package com.dexdiary.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dexdiary.presentation.navigation.Screen
import com.dexdiary.presentation.ui.components.EntryCard
import com.dexdiary.presentation.viewmodel.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val entries by viewModel.recentEntries.collectAsState()
    val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dex Diary", fontWeight = FontWeight.ExtraBold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Shop.route) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Shop")
                    }
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screen.Editor.createRoute(today)) },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Write Today") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                StreakCard(
                    streak = stats?.currentStreak ?: 0,
                    points = stats?.availablePoints ?: 0,
                    onPointsClick = { navController.navigate(Screen.Points.route) }
                )
            }

            val rewardClaimed by viewModel.dailyRewardClaimed.collectAsState()
            if (!rewardClaimed) {
                item {
                    DailyRewardCard(onClaim = viewModel::claimDailyReward)
                }
            }
            
            val missions by viewModel.dailyMissions.collectAsState()
            if (missions.isNotEmpty()) {
                item {
                    Text(
                        text = "Daily Missions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                items(missions) { mission ->
                    MissionCard(mission)
                }
            }
            
            item {
                Text(
                    text = "Recent Entries",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            
            items(entries) { entry ->
                EntryCard(
                    entry = entry,
                    onEditClick = { navController.navigate(Screen.Editor.createRoute(entry.date)) },
                    onLongClick = { }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun StreakCard(
    streak: Int,
    points: Int,
    onPointsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🔥", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$streak Day Streak",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Keep writing to keep it alive!",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Surface(
                onClick = onPointsClick,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = points.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "PTS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun DailyRewardCard(onClaim: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "🎁 Daily Gift Available!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Open it to see what's inside!", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onClaim) {
                Text("Open")
            }
        }
    }
}

@Composable
fun MissionCard(mission: com.dexdiary.data.database.entities.DailyMission) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = mission.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(text = mission.description, style = MaterialTheme.typography.bodySmall)
                LinearProgressIndicator(
                    progress = (mission.currentCount.toFloat() / mission.targetCount).coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
            if (mission.isCompleted) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Completed")
            }
        }
    }
}
}
}