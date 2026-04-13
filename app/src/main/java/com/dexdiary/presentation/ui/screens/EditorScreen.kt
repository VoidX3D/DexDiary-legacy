package com.dexdiary.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dexdiary.presentation.ui.components.MoodSelector
import com.dexdiary.presentation.viewmodel.EditorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    navController: NavController,
    date: String?,
    viewModel: EditorViewModel = hiltViewModel()
) {
    LaunchedEffect(date) {
        viewModel.loadEntry(date)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(date ?: "New Entry", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        viewModel.saveEntry { navController.popBackStack() } 
                    }) {
                        Icon(Icons.Default.Done, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("How are you feeling?", style = MaterialTheme.typography.labelMedium)
            MoodSelector(
                selectedMood = viewModel.mood,
                onMoodSelected = { viewModel.mood = it }
            )
            
            OutlinedTextField(
                value = viewModel.tags,
                onValueChange = { viewModel.tags = it },
                label = { Text("Tags (space separated)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = viewModel.content,
                onValueChange = { viewModel.onContentChanged(it) },
                label = { Text("Write your heart out...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("Supports **bold**, *italic*, # headings...") }
            )
            
            if (viewModel.grammarTips.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "💡 Tip: ${viewModel.grammarTips.first()}",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (viewModel.aiSummary.isNotEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(com.dexdiary.presentation.ui.theme.DexIcons.Sparkle, null, Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("AI SUMMARY", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                        Text(viewModel.aiSummary, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                val stats = viewModel.analysis
                Text(
                    text = "${stats?.wordCount ?: 0} words · ${stats?.charCount ?: 0} chars · ${stats?.readingTime ?: 1} min read",
                    style = MaterialTheme.typography.bodySmall
                )
                
                IconButton(onClick = viewModel::generateAiSummary) {
                    if (viewModel.isAiLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        Icon(com.dexdiary.presentation.ui.theme.DexIcons.Sparkle, contentDescription = "AI Summary", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
