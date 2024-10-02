package com.reto1.ultramarinos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
                items(listOf(product)) { productItem ->
                    Image(
                        painter = rememberAsyncImagePainter(productItem.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f) // Para que sea cuadrada
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = productItem.description)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Precio: ${productItem.price}€" + (if (productItem.unit != null) " / ${productItem.unit}" else ""))
                    productItem.offerPrice?.let {
                        Text(text = "Precio de oferta: $it€" + (if (productItem.unit != null) " / ${productItem.unit}" else ""), color = Color.Red)
                    }
                    Row (modifier = Modifier.fillMaxWidth().padding(top= 8.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Cantidad", fontSize = 20.sp,)
                        Image(painter= painterResource(R.drawable.baseline_do_disturb_on_24), contentDescription = "Icono", modifier= Modifier
                            .width(48.dp)
                            .clickable {

                            })
                        Text(text = cantidad.toString())
                        Image(painter= painterResource(R.drawable.baseline_add_circle_24), contentDescription = "Icono", modifier= Modifier
                            .width(48.dp)
                            .clickable {

                            })
                        Image(painter= painterResource(R.drawable.baseline_add_shopping_cart_24), contentDescription = "Icono", modifier= Modifier
                            .width(48.dp)
                            .clickable {

                            })
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
