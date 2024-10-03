package com.reto1.ultramarinos.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.components.FAB
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.lightmode


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutView(paddingValues2: PaddingValues) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = { FAB() },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "about") {
                composable("about") { AboutContent(paddingValues2) }
            }
        }
    )
}



@Composable
fun AboutContent(paddingValues: PaddingValues) {
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(if (lightmode) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.background)
        .padding(paddingValues))
    {}

}
