package com.reto1.ultramarinos

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Register(private val activity: Activity, private val signInLauncher: ActivityResultLauncher<IntentSenderRequest>) {
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    var isLoggedIn = mutableStateOf(false)

    init {
        oneTapClient = Identity.getSignInClient(activity)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(activity.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    fun startGoogleSignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
                    signInLauncher.launch(intentSenderRequest)
                } catch (e: Exception) {
                    Log.e("Register", "Google Sign-In failed", e)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Register", "Google Sign-In failed: ${e.message}")
            }
    }

    fun handleSignInResult(result: Intent?) {
        if (result != null) {
            try {
                val credential: SignInCredential? = oneTapClient.getSignInCredentialFromIntent(result)
                val idToken = credential?.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                Log.d("Register", "Google Sign-In successful!")
                                isLoggedIn.value = true
                            } else {
                                Log.e("Register", "Google Sign-In failed", task.exception)
                            }
                        }
                }
            } catch (e: ApiException) {
                Log.e("Register", "Google Sign-In failed", e)
            }
        }
    }


    fun registerWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("Register", "Registration successful!")
                    isLoggedIn.value = true
                    onResult(true, null)
                } else {
                    Log.e("Register", "Registration failed", task.exception)
                    onResult(false, task.exception?.message)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Register", "Error registering: ${e.message}")
                onResult(false, e.message)
            }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("Register", "Sign-In successful!")
                    isLoggedIn.value = true
                    onResult(true, null)
                } else {
                    Log.e("Register", "Sign-In failed", task.exception)
                    onResult(false, task.exception?.message)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Register", "Error signing in: ${e.message}")
                onResult(false, e.message)
            }
    }
}