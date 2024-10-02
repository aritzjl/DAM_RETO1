package com.reto1.ultramarinos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.ToolBar


//  SETTINGS VIEW
@Preview
@Composable
fun SettingsView() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeContent(paddingValues) }
                composable("about") { AboutView(paddingValues) }
                composable("gallery") { GalleryView(paddingValues) }
                composable("settings") { SettingsContent(paddingValues) }
            }
        }
    )
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)) {
        item {
            Text(text = "Ajustes", fontWeight = FontWeight.ExtraBold ,fontSize = 32.sp, color = Color.White, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), textAlign = TextAlign.Center)
        }
    }
}