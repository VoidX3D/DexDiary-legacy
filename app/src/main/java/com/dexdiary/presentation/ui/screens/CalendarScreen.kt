package com.dexdiary.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dexdiary.presentation.navigation.Screen
import com.dexdiary.presentation.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val entryDates by viewModel.entryDates.collectAsState()
    val today = LocalDate.now()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(
                text = today.month.name + " " + today.year,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                contentPadding = PaddingValues(4.dp)
            ) {
                // Simplified calendar: just today and some days before
                items(31) { index ->
                    val day = index + 1
                    val dateStr = today.withDayOfMonth(day).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    val hasEntry = entryDates.contains(dateStr)
                    
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(
                                color = if (hasEntry) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                                shape = MaterialTheme.shapes.small
                            )
                            .clickable { 
                                navController.navigate(Screen.Editor.createRoute(dateStr))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = day.toString())
                    }
                }
            }
        }
    }
}
