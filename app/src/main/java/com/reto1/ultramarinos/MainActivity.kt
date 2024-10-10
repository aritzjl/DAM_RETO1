package com.reto1.ultramarinos

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import com.reto1.ultramarinos.models.Idioma
import com.reto1.ultramarinos.ui.theme.AppTheme
import com.reto1.ultramarinos.viewmodels.MainViewModel
import com.reto1.ultramarinos.views.HomeView
import com.reto1.ultramarinos.views.LoginView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = MainViewModel(applicationContext)

        setContent {

            // Escucha cambios a esta variable
            val darkTheme : Boolean by mainViewModel.darkTheme.observeAsState(initial = true)
            val currentLanguage : String by mainViewModel.idioma.observeAsState(initial = "es")
            val activity = LocalContext.current as Activity

            AppTheme (
                darkTheme = darkTheme
            ){
                if (isLoggedIn) {
                    HomeView(
                        mainViewModel, darkTheme, mainViewModel.allIdiomas, currentLanguage
                    ) { idioma, activity ->
                        mainViewModel.onCurrentLanguageChange(idioma, activity)
                    }
                } else {
                    /*LoginView(onLoginSuccess = {
                        isLoggedIn = true
                    })*/
                    //Disabled for development
                    HomeView(
                        mainViewModel, darkTheme, mainViewModel.allIdiomas, currentLanguage
                    ) { idioma, activity ->
                        mainViewModel.onCurrentLanguageChange(idioma, activity)
                    }
                }
            }

        }
    }
}