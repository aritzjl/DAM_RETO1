package com.reto1.ultramarinos.components

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.Register
import com.reto1.ultramarinos.models.CartProduct
import com.reto1.ultramarinos.viewmodels.CartViewModel

@Composable
fun FAB(isLightMode: Boolean, email: String, language: String) {
    val context = LocalContext.current
    val viewModel: CartViewModel = viewModel()
    var showCartModal by remember { mutableStateOf(false) }
    var cartItems by remember { mutableStateOf<List<CartProduct>>(emptyList()) }

    if (showCartModal)
    {
        CartModal(cartItems, email = email, language = language) {
            showCartModal = false
        }
    }

    FloatingActionButton(onClick = {
        Log.d("Cart", "Clicado: Obteniendo carrito")
        viewModel.getUserCart(email) { cartList ->
            Log.d("Cart", "Carrito obtenido: $cartList")
            try {
                val groupedCartItems = cartList
                    .groupBy { it.first.title }
                    .map { (title, items) ->
                        val totalQuantity = items.sumOf { it.second }
                        CartProduct(items.first().first, totalQuantity)
                    }
                Log.d("Cart", "Items agrupados: $groupedCartItems")
                cartItems = groupedCartItems

                // Mostrar el modal si hay productos
                if (cartItems.isNotEmpty()) {
                    showCartModal = true
                } else {
                    Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Cart", "Error: ${e.message}", e)
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }) {
        Image(
            painter = painterResource(R.drawable.baseline_shopping_cart_24),
            contentDescription = "Icono",
            modifier = Modifier.width(48.dp),
            colorFilter = ColorFilter.tint(if (isLightMode) Color.White else Color.Black),
        )
    }
}
