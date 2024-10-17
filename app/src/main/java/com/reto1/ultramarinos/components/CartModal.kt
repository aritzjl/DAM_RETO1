package com.reto1.ultramarinos.components

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.Register
import com.reto1.ultramarinos.models.CartProduct
import com.reto1.ultramarinos.viewmodels.CartViewModel

@Composable
fun CartModal(cartItems: List<CartProduct>, email: String, language: String, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val viewModel: CartViewModel = viewModel()

    // Cálculo del precio total considerando el precio de oferta
    val totalPrice = cartItems.map {
        val itemPrice = it.product.offerPrice ?: it.product.price
        itemPrice * it.amount
    }.sum()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Carrito de Compras", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                cartItems.forEach { cartProduct ->
                    val itemTotal = (cartProduct.product.offerPrice ?: cartProduct.product.price) * cartProduct.amount

                    // Traducción del contenido del producto según el idioma
                    val productText = when (language) {
                        "en" -> "${cartProduct.product.title_en ?: cartProduct.product.title} x ${cartProduct.amount}"
                        "eu" -> "${cartProduct.product.title_eus ?: cartProduct.product.title} x ${cartProduct.amount}"
                        else -> "${cartProduct.product.title} x ${cartProduct.amount}"
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(cartProduct.product.imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = productText,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        // Mostrar el precio correspondiente
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.clearUserCart(email, {
                        Toast.makeText(context, "Compra realizada!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }, { exception ->
                        Toast.makeText(context, "Error al realizar la compra: ${exception.message}", Toast.LENGTH_SHORT).show()
                    })
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Comprar", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            Row {
                // Botón para vaciar el carrito
                Button(
                    onClick = {
                        viewModel.clearUserCart(email, {
                            Toast.makeText(context, "Carrito vaciado!", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }, { exception ->
                            Toast.makeText(context, "Error al vaciar el carrito: ${exception.message}", Toast.LENGTH_SHORT).show()
                        })
                    }
                ) {
                    Text("Eliminar Carrito")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón para cerrar sin vaciar el carrito
                Button(
                    onClick = {
                        onDismiss() // Cerrar el modal sin hacer nada más
                    }
                ) {
                    Text("Cerrar")
                }
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}
