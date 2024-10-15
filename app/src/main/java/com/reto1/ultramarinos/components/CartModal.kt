package com.reto1.ultramarinos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.widget.Toast
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.models.CartProduct

@Composable
fun CartModal(cartItems: List<CartProduct>, onDismiss: () -> Unit) {
    val context = LocalContext.current

    // Calcular el precio total del carrito como un Float usando map y sum
    val totalPrice = cartItems.map { it.product.price * it.amount }.sum()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Carrito de Compras", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                cartItems.forEach { cartProduct ->
                    val itemTotal = cartProduct.product.price * cartProduct.amount
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Usar una imagen de producto (asegúrate de tener la URL correcta)
                        Image(
                            painter = rememberAsyncImagePainter(cartProduct.product.imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "${cartProduct.product.title} x ${cartProduct.amount}",
                            modifier = Modifier.weight(1f).padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$${itemTotal}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Precio Total: $${totalPrice}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary // Usar color primario de MaterialTheme
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    Toast.makeText(context, "Compra realizada!", Toast.LENGTH_SHORT).show()
                    onDismiss() // Cerrar el modal después de la compra
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) // Usar color primario
            ) {
                Text("Comprar", color = MaterialTheme.colorScheme.onPrimary) // Color de texto en función del contenedor
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cerrar")
            }
        },
        modifier = Modifier.padding(16.dp) // Agregar margen alrededor del modal
    )
}