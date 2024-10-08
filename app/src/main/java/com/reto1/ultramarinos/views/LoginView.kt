package com.reto1.ultramarinos.views

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRegistrar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.core.Tag
import com.google.firebase.firestore.FirebaseFirestore
import com.reto1.ultramarinos.R

@Composable
fun LoginView(onLoginSuccess: () -> Unit) {

    val Context = LocalContext.current

    // Simulación de un campo de texto para el nombre de usuario y la contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Simulación de la lógica de inicio de sesión
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_utramarinos),
            contentDescription = "logo",
            modifier = Modifier
                .size(256.dp)
                .alpha(0.6f)
        )
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("example@domain.com") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Show Password")
            Checkbox(
                checked = showPassword,
                onCheckedChange = { showPassword = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (password.length < 6){
                        errorMessage = "Password must be at least 6 characters."
                        return@Button
                    }else {
                        connectToBD(Context, email, password) { success ->
                            if (success) {
                                errorMessage = "sadnffd lkmf  lknfg;kn klrgj"
                                onLoginSuccess()
                            } else {
                                errorMessage = "Invalid email or password."
                            }
                        }
                    }
                } else {
                    errorMessage = "Please fill in all fields."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            )
        ) {
            Text(text = "Login", color = Color.White)
        }

        Button(
            onClick = {

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (password.length < 6) {
                        errorMessage = "Password must be at least 6 characters."
                        return@Button
                    } else {
                        registerToBD(Context, email, password) { success ->
                            if (success) {
                                errorMessage = "sadnffd lkmf  lknfg;kn klrgj"
                                onLoginSuccess()
                            } else {
                                errorMessage = "Invalid email or password."
                            }
                        }
                    }
                } else {
                    errorMessage = "Please fill in all fields."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            )
        ) {
            Text(text = "Registrar", color = Color.White)
        }

        // Display error message if any
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

fun connectToBD(context: Context, email: String, password: String, onResult: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true)
                Toast.makeText(context, "Log in successful!", Toast.LENGTH_SHORT).show()
            } else {
                onResult(false)
            }
        }
        .addOnFailureListener { e ->
            println("Error logging in: ${e.message}")
            onResult(false)
        }
}

fun registerToBD(context: Context, email: String, password: String, onResult: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                onResult(true)
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                onResult(false)
            }
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Error registering: ${e.message}")
            onResult(false)
        }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(onLoginSuccess = {})
}