package com.reto1.ultramarinos.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.is_carousel_Paused
import com.reto1.ultramarinos.models.CartProduct
import com.reto1.ultramarinos.models.Product
import com.reto1.ultramarinos.viewmodels.CartViewModel


@Composable
fun ProductDetailModal(product: Product, email:String, onDismiss: () -> Unit) {
    var cart_amount by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val viewModel: CartViewModel = viewModel()
    is_carousel_Paused = true

    AlertDialog(
        onDismissRequest = {
            is_carousel_Paused = false
            onDismiss() },
        title = { Text(text = product.title) },
        text = {
            LazyColumn {
                item {
                    Image(
                        painter = rememberAsyncImagePainter(product.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.category.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Precio: ${product.price}€" + (if (product.unit != null) " / ${product.unit}" else ""))
                    product.offerPrice?.let {
                        Text(
                            text = "Precio de oferta: $it€" + (if (product.unit != null) " / ${product.unit}" else ""),
                            color = Color.Red
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .padding(top = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_do_disturb_on_24),
                            contentDescription = "Icono",
                            modifier = Modifier
                                .width(48.dp)
                                .clickable {
                                    if (cart_amount > 0) {
                                        cart_amount--
                                    }
                                }
                        )
                        Text(text = cart_amount.toString())
                        Image(
                            painter = painterResource(R.drawable.baseline_add_circle_24),
                            contentDescription = "Icono",
                            modifier = Modifier
                                .width(48.dp)
                                .clickable {
                                    if (cart_amount < 100) {
                                        cart_amount++
                                    }
                                }
                        )
                        Image(
                            painter = painterResource(R.drawable.baseline_add_shopping_cart_24),
                            contentDescription = "Icono",
                            modifier = Modifier
                                .width(48.dp)
                                .clickable {
                                    if (cart_amount > 0) {
                                        viewModel.addCartProduct(
                                            product,
                                            cart_amount,
                                            email,
                                            onSuccess = {
                                                Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
                                                cart_amount = 0  // Resetear el contador
                                                onDismiss()  // Cerrar el modal
                                            },
                                            onError = { exception ->
                                                Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                                            }
                                        )
                                    } else {
                                        Toast.makeText(context, "La cantidad debe ser mayor que 0", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        )

                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // Reanudar el carrusel al cerrar el modal
                is_carousel_Paused = false
                onDismiss()
            }) {
                Text("Cerrar")
            }
        }
    )
}
