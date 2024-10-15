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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.IntentSenderRequest
import com.reto1.ultramarinos.views.HomeView
import com.reto1.ultramarinos.views.LoginView
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableIntStateOf

class MainActivity : ComponentActivity() {

    private lateinit var register: Register
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = MainViewModel(applicationContext)

        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                register.handleSignInResult(result.data)
            }
        }

        register = Register(this, signInLauncher)  // Передаем signInLauncher в Register
    register.isLoggedIn.value = true
        setContent {

            // Escucha cambios a esta variable
            val darkTheme : Boolean by mainViewModel.darkTheme.observeAsState(initial = true)
            val currentLanguage : String by mainViewModel.idioma.observeAsState(initial = "es")
            val activity = LocalContext.current as Activity

            AppTheme (
                darkTheme = darkTheme
            ){
                if (register.isLoggedIn.value) {
                    HomeView(
                        mainViewModel, darkTheme, mainViewModel.allIdiomas, currentLanguage,
                        onIdiomaActualChange = { idioma, activity ->
                            mainViewModel.onCurrentLanguageChange(idioma, activity)
                        },
                        register = register,
                    )
                } else {
                    LoginView(
                        register = register,    // Передаем register в LoginView
                        onLoginSuccess = {
                            register.isLoggedIn.value = true
                        },
                        onGoogleSignIn = {
                            register.startGoogleSignIn()
                        }
                    )
                }
            }

        }
    }
}
