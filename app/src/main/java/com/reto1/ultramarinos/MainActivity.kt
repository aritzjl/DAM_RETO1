package com.reto1.ultramarinos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.IntentSenderRequest
import com.reto1.ultramarinos.views.HomeView
import com.reto1.ultramarinos.views.LoginView
import androidx.activity.result.ActivityResultLauncher

class MainActivity : ComponentActivity() {

    private lateinit var register: Register
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                register.handleSignInResult(result.data)
            }
        }

        register = Register(this, signInLauncher)  // Передаем signInLauncher в Register

        setContent {
            if (register.isLoggedIn.value) {
                HomeView()
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
