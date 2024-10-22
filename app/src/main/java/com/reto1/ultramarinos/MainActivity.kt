package com.reto1.ultramarinos

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reto1.ultramarinos.viewmodels.GalleryViewModel

class MainActivity : ComponentActivity() {

    private var mainViewModel: MainViewModel = MainViewModel(this)
    private var cambiarIdiomaClass: CambiarIdiomaClass = CambiarIdiomaClass()
    private lateinit var register: Register
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargar el tema
        mainViewModel.getTheme(this)

        // Cargar idioma
        val language = mainViewModel.getLanguage(this) ?: "es"

        Log.d("MainActivity", "Language: $language")

        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                register.handleSignInResult(result.data)
            }
        }

        register = Register(this, signInLauncher)

        // Recuperar el estado de inicio de sesión y el email
        val email = register.loadEmail("email", "")
        val isLoggedIn = register.loadBoolean("is_logged_in", false)
        register.isLoggedIn.value = isLoggedIn

        setContent {
            // Escuchar cambios en el tema oscuro
            val darkTheme: Boolean by mainViewModel.darkTheme.observeAsState(initial = false)
            val context = LocalContext.current
            val activity = LocalContext.current as Activity

            // Observa cambios en el estado de inicio de sesión
            val loggedInState = remember { register.isLoggedIn }.value
            val currentEmail = remember { register.loadEmail("email", "") }

            AppTheme(darkTheme = darkTheme) {
                if (loggedInState && currentEmail.isNotEmpty()) {
                    // Navegar a HomeView si está autenticado
                    HomeView(
                        mainViewModel = mainViewModel,
                        cambiarIdiomaClass = cambiarIdiomaClass,
                        darkTheme,
                        context = context,
                        register = register,
                        email = currentEmail,
                        language = language,
                        galleryViewModel = GalleryViewModel(),

                    )
                } else {
                    // Mostrar LoginView si no está autenticado
                    LoginView(
                        register = register,
                        onLoginSuccess = {
                            // Actualizar estado de inicio de sesión
                            register.isLoggedIn.value = true
                        },
                        onGoogleSignIn = {
                            register.startGoogleSignIn()
                        },
                        activity = activity
                    )
                }
            }
        }
    }
}
