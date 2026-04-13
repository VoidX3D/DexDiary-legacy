package com.dexdiary.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dexdiary.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                Text("Appearance", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                // Simplified theme selector
                Button(onClick = { viewModel.changeTheme("Light") }) { Text("Light") }
                Button(onClick = { viewModel.changeTheme("Dark") }) { Text("Dark (Unlocked if you have 5k points)") }
                Button(onClick = { viewModel.changeTheme("Sepia") }) { Text("Sepia") }
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Data", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(onClick = { /* Export */ }) { Text("Export as JSON") }
                Button(onClick = { /* Export */ }) { Text("Export as Markdown") }
            }
        }
    }
}
