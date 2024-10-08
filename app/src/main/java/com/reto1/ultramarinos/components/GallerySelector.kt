package com.reto1.ultramarinos.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CategorySelector(onCategorySelected: (String?) -> Unit) {
    // Lista de categorías como strings, null es la opción para "Todas las categorías"
    val categories = listOf(null) + listOf("Pescado", "Legumbres", "Conservas")
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedCategory ?: "Todas las categorías",
            modifier = Modifier.padding(16.dp)
        )
    }

    // Mostrar la lista de categorías cuando se expanda
    if (expanded) {
        LazyColumn {
            items(categories) { category ->
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            selectedCategory = category // Actualizar la categoría seleccionada
                            onCategorySelected(category) // Pasar la categoría seleccionada al ViewModel
                            expanded = false // Cerrar el desplegable
                        }
                ) {
                    Text(
                        text = category ?: "Todas las categorías",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

