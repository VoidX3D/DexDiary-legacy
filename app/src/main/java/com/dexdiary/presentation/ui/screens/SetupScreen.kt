package com.dexdiary.presentation.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dexdiary.presentation.navigation.Screen
import com.dexdiary.presentation.ui.theme.DexIcons

@Composable
fun SetupScreen(navController: NavController) {
    var step by remember { mutableStateOf(1) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(targetState = step, label = "setup_step") { targetStep ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    when (targetStep) {
                        1 -> SetupStep(
                            icon = DexIcons.DiaryBook,
                            title = "Welcome to Dex Diary",
                            description = "The first diary that's actually fun to write in."
                        )
                        2 -> SetupStep(
                            icon = DexIcons.Sparkle,
                            title = "AI-Powered Insights",
                            description = "Get summaries and grammar tips locally or via your favorite AI provider."
                        )
                        3 -> SetupStep(
                            icon = androidx.compose.material.icons.Icons.Default.Star,
                            title = "Gamified Growth",
                            description = "Earn points, keep streaks, and unlock premium themes like Dark Mode."
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    if (step < 3) step++
                    else navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (step < 3) "Next" else "Get Started")
            }
        }
    }
}

@Composable
fun SetupStep(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
