package com.reto1.ultramarinos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.reto1.ultramarinos.ui.theme.AppTheme
import com.reto1.ultramarinos.views.HomeView
import com.reto1.ultramarinos.views.LoginView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                if (isLoggedIn) {
                    HomeView()
                } else {
                    /*LoginView(onLoginSuccess = {
                        isLoggedIn = true
                    })*/
                    //Disabled for development
                    HomeView()
                }
            }

        }
    }
}