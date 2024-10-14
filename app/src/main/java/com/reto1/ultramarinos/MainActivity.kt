package com.reto1.ultramarinos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.reto1.ultramarinos.ui.theme.AppTheme
import com.reto1.ultramarinos.viewmodels.MainViewModel
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.IntentSenderRequest
import com.reto1.ultramarinos.views.HomeView
import com.reto1.ultramarinos.views.LoginView
import androidx.activity.result.ActivityResultLauncher

class MainActivity : ComponentActivity() {

    private var mainViewModel: MainViewModel = MainViewModel(this)
    private var cambiarIdiomaClass: CambiarIdiomaClass = CambiarIdiomaClass()
    private lateinit var register: Register
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun attachBaseContext(newBase: Context?) {
        val language = mainViewModel.getLanguage(newBase!!) ?: "es"
        cambiarIdiomaClass.setLocale(newBase,language)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Call the getTheme method
        mainViewModel.getTheme(this)

        // Call the getLanguage method
        val language = mainViewModel.getLanguage(this)
        Log.d("MainActivity", "Language: $language")

        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                register.handleSignInResult(result.data)
            }
        }

        register = Register(this, signInLauncher)  // Передаем signInLauncher в Register

        setContent {

            // Escucha cambios a esta variable
            val darkTheme : Boolean by mainViewModel.darkTheme.observeAsState(initial = false)
            val context = LocalContext.current
            val activity = LocalContext.current as Activity

            AppTheme (
                darkTheme = darkTheme
            ){
                if (!register.isLoggedIn.value) {
                    HomeView(
                        mainViewModel,
                        darkTheme,
                        mainViewModel.allIdiomas,
                        context,
                        activity,
                        register = register
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
