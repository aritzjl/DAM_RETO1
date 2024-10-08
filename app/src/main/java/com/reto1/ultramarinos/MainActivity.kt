package com.reto1.ultramarinos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.reto1.ultramarinos.ui.theme.AppTheme
import com.reto1.ultramarinos.viewmodels.MainViewModel
import com.reto1.ultramarinos.views.HomeView
import com.reto1.ultramarinos.views.LoginView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = MainViewModel()

        setContent {

            // Escucha cambios a esta variable
            val darkTheme : Boolean by mainViewModel.darkTheme.observeAsState(initial = true)

            AppTheme (
                darkTheme = darkTheme
            ){
                if (isLoggedIn) {
                    HomeView(mainViewModel, darkTheme)
                } else {
                    /*LoginView(onLoginSuccess = {
                        isLoggedIn = true
                    })*/
                    //Disabled for development
                    HomeView(mainViewModel, darkTheme)
                }
            }

        }
    }
}