package com.reto1.ultramarinos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.models.Product

@Composable
fun ProductDetailModal(product: Product, onDismiss: () -> Unit) {
    var cantidad = 1
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = product.title) },
        text = {
            LazyColumn {
                item{
                    Image(
                        painter = rememberAsyncImagePainter(product.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f) // Para que sea cuadrada
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.category.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Precio: ${product.price}€" + (if (product.unit != null) " / ${product.unit}" else ""))
                    product.offerPrice?.let {
                        Text(text = "Precio de oferta: $it€" + (if (product.unit != null) " / ${product.unit}" else ""), color = Color.Red)
                    }
                }

            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}
