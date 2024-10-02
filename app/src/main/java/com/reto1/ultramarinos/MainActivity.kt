package com.reto1.ultramarinos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.reto1.ultramarinos.ui.theme.AppTheme
import com.reto1.ultramarinos.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarTitle = "Inicio"
        setContent {
            AppTheme {
                HomeView()
            }
        }
    }
}


