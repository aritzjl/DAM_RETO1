package com.reto1.ultramarinos.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reto1.ultramarinos.ProductCategory

@Composable
fun GalleryFilter(
    selectedCategory: MutableState<ProductCategory?>,
    onCategorySelected: (ProductCategory?) -> Unit
) {
    // Lista de categorías incluyendo la opción "Todos" para mostrar todas las categorías
    val categories = listOf(null) + ProductCategory.values().toList()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        categories.forEach { category ->
            OutlinedButton(
                onClick = { onCategorySelected(category) },
                modifier = Modifier.padding(4.dp)
            ) {
                BasicText(
                    text = category?.name ?: "Todos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
