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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.R
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

    val precio_total = stringResource(id = R.string.cart_total_price)
    val eliminar_bien = stringResource(id = R.string.cart_delete_good)
    val eliminar_error = stringResource(id = R.string.cart_delete_error)
    val add_bien = stringResource(id = R.string.cart_buy_good)
    val add_error = stringResource(id = R.string.cart_buy_error)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.cart_title), style = MaterialTheme.typography.titleLarge) },
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
                    text = precio_total + " $${totalPrice}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.clearUserCart(email, {
                        Toast.makeText(context, add_bien, Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }, { exception ->
                        Toast.makeText(context, add_error + " ${exception.message}", Toast.LENGTH_SHORT).show()
                    })
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(id = R.string.cart_buy), color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            Row {
                // Botón para vaciar el carrito
                Button(
                    onClick = {
                        viewModel.clearUserCart(email, {
                            Toast.makeText(context, eliminar_bien, Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }, { exception ->
                            Toast.makeText(context, eliminar_error + " ${exception.message}", Toast.LENGTH_SHORT).show()
                        })
                    }
                ) {
                    Text(stringResource(id = R.string.cart_delete))
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón para cerrar sin vaciar el carrito
                Button(
                    onClick = {
                        onDismiss() // Cerrar el modal sin hacer nada más
                    }
                ) {
                    Text(stringResource(id = R.string.cart_close))
                }
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}
