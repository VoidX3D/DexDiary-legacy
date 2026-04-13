package com.dexdiary.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dexdiary.presentation.viewmodel.AiUsageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiUsageReportScreen(
    navController: NavController,
    viewModel: AiUsageViewModel = hiltViewModel()
) {
    val logs by viewModel.recentLogs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Usage Report") },
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
                Text("Recent Requests", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }
            
            items(logs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = log.provider, fontWeight = FontWeight.Bold)
                            Text(text = "${log.totalTokens} tokens", color = MaterialTheme.colorScheme.primary)
                        }
                        Text(text = "Model: ${log.model}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Type: ${log.promptType}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Time: ${log.processingTimeMs}ms", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
