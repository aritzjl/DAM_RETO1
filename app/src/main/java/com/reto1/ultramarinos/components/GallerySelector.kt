package com.reto1.ultramarinos.components

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
import androidx.compose.ui.unit.dp
import com.reto1.ultramarinos.models.ProductCategory

@Composable
fun CategorySelector(onCategorySelected: (ProductCategory?) -> Unit) {
    val categories = ProductCategory.values().toList() + listOf(null) // Agregar "Todas las categorías"
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
    Row (modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiaryContainer).clickable { expanded = !expanded }) {
        Text(
            text = selectedCategory?.name ?: "Selecciona una categoría",
            modifier = Modifier
                .padding(16.dp)
        )
    }

    val borderColor = MaterialTheme.colorScheme.primary;
    // Mostrar la lista de categorías
    if (expanded) {
        LazyColumn {
            items(categories) { category ->
                Row (modifier = Modifier

                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        selectedCategory = category
                        onCategorySelected(category)
                        expanded = false // Cerrar la lista al seleccionar una opción
                    })
                {
                    Text(
                        text = category?.name ?: "Todas las categorías",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}