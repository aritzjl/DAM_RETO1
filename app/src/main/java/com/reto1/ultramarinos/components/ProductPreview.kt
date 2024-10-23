package com.reto1.ultramarinos.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.Register
import com.reto1.ultramarinos.models.Product
import com.reto1.ultramarinos.viewmodels.MainViewModel

@Composable
fun ProductPreview(
    product: Product,
    email: String,
    language: String,
    register: Register
) {
    // Desestructuración de las propiedades de Product
    var (
        title,
        description,
        imageUrl,
        price,
        offerPrice,
        unit,
        category,
        title_eus,
        title_en,
        description_eus,
        description_en
    ) = product

    var showModal by remember { mutableStateOf(false) }
    if (language == "en")
    {
        title = title_en.toString();
    }
    else if (language == "eu")
    {
        title = title_eus.toString();
    }

    Box(
        modifier = Modifier
            .padding(6.dp) // Padding alrededor de la tarjeta
            .shadow(8.dp, RoundedCornerShape(16.dp)) // Sombra con bordes redondeados
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp)) // Fondo blanco y bordes redondeados
            .fillMaxWidth() // Ajustar el tamaño de la tarjeta
            .clickable {
                showModal = !showModal
            }
    ) {
        Column {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Imagen de la obra de arte",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth() // Llenar el ancho
                        .aspectRatio(1f) // Relación de aspecto 1:1 para que sea cuadrada
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
                    .padding(bottom = 4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            if (offerPrice != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "$offerPrice€${if (unit != null) "/$unit" else ""}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Text(
                        text = "$price€${if (unit != null) "/$unit" else ""}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(end = 8.dp)
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "$price€${if (unit != null) "/$unit" else ""}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        if (showModal) {
            ProductDetailModal(product = product, email, language, register, onDismiss = { showModal = false })
        }
    }
}
