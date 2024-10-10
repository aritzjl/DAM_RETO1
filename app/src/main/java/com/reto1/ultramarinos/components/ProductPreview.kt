package com.reto1.ultramarinos.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.models.Product

@Composable
fun ProductPreview(artwork: Product) {
    var showModal by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(6.dp) // Padding alrededor de la tarjeta
            .shadow(8.dp, RoundedCornerShape(16.dp)) // Sombra con bordes redondeados
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp)) // Fondo blanco y bordes redondeados
            //.border(2.dp, Color.White, RoundedCornerShape(16.dp)) // Borde fino
            .fillMaxWidth() // Ajustar el tamaño de la tarjeta
            .clickable {
                showModal = !showModal
            }
    ) {
        Column () {
            // Contenedor para superponer el icono sobre la imagen
            Box {
                // Imagen de la obra de arte
                Image(
                    painter = rememberAsyncImagePainter(artwork.imageUrl),
                    contentDescription = "Imagen de la obra de arte",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth() // Llenar el ancho
                        .aspectRatio(1f) // Relación de aspecto 1:1 para que sea cuadrada
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                )

                // Fondo para el icono (recuadro gris oscuro)
                Box(
                    modifier = Modifier
                        .size(36.dp) // Tamaño del recuadro
                        //.background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp)) // Color gris oscuro con bordes redondeados
                        .align(Alignment.TopEnd) // Alinear en la esquina superior derecha
                        .padding(8.dp) // Padding para ajustar la posición
                ) {
                    /*
                    // Icono superpuesto
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = "Icono del estilo de la obra de arte",
                        modifier = Modifier
                            .size(24.dp) // Tamaño del icono
                            .align(Alignment.Center), // Centrar el icono dentro del recuadro
                        tint = Color.White // Color del icono
                    )
                    */
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth() // Llenar el ancho
                    //.background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp)) // Color gris oscuro con bordes redondeados
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
                    .padding(bottom= 4.dp)
            ) {
                // Título de la obra de arte
                Text(
                    text = artwork.title,
                    style = MaterialTheme.typography.bodyLarge, // Usando la fuente EB Garamond definida en el tema
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                    , color = MaterialTheme.colorScheme.onPrimaryContainer // Color del texto
                )
            }
            if (artwork.offerPrice != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp)
                ) {
                    // Precio de oferta en rojo
                    Text(
                        text = artwork.offerPrice.toString() + "€" + (if (artwork.unit!=null) "/" + artwork.unit else ""),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,  // Tamaño más grande
                        color = Color.Red,  // Texto en rojo
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                              // Esto lo sitúa en la izquierda
                            .padding(start = 8.dp)
                    )

                    // Precio original más pequeño en la esquina derecha inferior
                    Text(
                        text = artwork.price.toString() + "€" + (if (artwork.unit!=null) "/" + artwork.unit else ""),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,  // Tamaño más pequeño
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier
                            .align(Alignment.Bottom)  // Alinearlo en la parte inferior
                            .padding(end = 8.dp)  // Alineado a la derecha
                    )
                }
            } else {
                // Mostrar solo el precio original sin oferta
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = artwork.price.toString() + "€" + (if (artwork.unit!=null) "/" + artwork.unit else ""),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,  // Tamaño más grande
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            // Esto lo sitúa en la izquierda
                            .padding(start = 8.dp)
                    )
                }
            }

        }
        if (showModal) {
            //Toast.makeText(context, "hola0", Toast.LENGTH_SHORT).show()
            ProductDetailModal(product = artwork, onDismiss = { showModal = false })
        }
    }
}