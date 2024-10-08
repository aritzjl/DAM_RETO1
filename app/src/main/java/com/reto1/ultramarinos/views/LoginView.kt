package com.reto1.ultramarinos.views

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.reto1.ultramarinos.R

@Composable
fun LoginView(onLoginSuccess: () -> Unit) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Volver al inicio",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // Logic for navigating to the home screen
                }
            )
        }
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
                    connectToBD(email, password) { success ->
                        if (success) {
                            errorMessage = "sadnffd lkmf  lknfg;kn klrgj"
                            onLoginSuccess()
                        } else {
                            errorMessage = "Invalid email or password."
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

        // Display error message if any
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Todavia no tienes cuenta?",
                color = Color.Gray
            )
            Text(
                text = " Crear",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // Logic for navigating to the register view
                }
            )
        }
    }
}

fun connectToBD(email: String, password: String, onResult: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    // Sign in using Firebase Authentication
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true) // Successful login
            } else {
                onResult(false) // Failed login
            }
        }
        .addOnFailureListener { e ->
            println("Error logging in: ${e.message}")
            onResult(false) // Failed login due to error
        }
}