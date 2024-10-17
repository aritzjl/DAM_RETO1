package com.reto1.ultramarinos.viewmodels

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.reto1.ultramarinos.models.Product

class GalleryViewModel : ViewModel() {
    var isSingleColumn by mutableStateOf(false)

    var selectedCategory by mutableStateOf<String?>(null)
        private set

    var categories by mutableStateOf(
        listOf(
            "Pescado",
            "Legumbres",
            "Conservas"
        )
    )

    private var allProducts = listOf<Product>()

    // Lista filtrada de productos que se mostrarán en la interfaz
    var artworks = mutableStateOf<List<Product>>(emptyList())
        private set

    init {
        loadProducts() // Cargar productos al inicializar el ViewModel
    }

    // Función para cargar todos los productos desde Firebase
    private fun loadProducts() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Productos")
            .get()
            .addOnSuccessListener { docs ->
                val productsList = docs.map { documento ->
                    Product(
                        title = documento.data["nombre"].toString(),
                        description = documento.data["descripcion"].toString(),
                        imageUrl = documento.data["foto"].toString(),
                        price = documento.data["precio"].toString().toFloat(),
                        offerPrice = documento.data["precio_oferta"].toString().toFloatOrNull(),
                        unit = documento.data["unidad"].toString(),
                        category = documento.data["categoria"].toString(),

                        title_en = documento.data["nombre_ing"].toString(),
                        title_eus = documento.data["nombre_eus"].toString(),

                        description_en = documento.data["descripcion_ing"].toString(),
                        description_eus = documento.data["descripcion_eus"].toString(),
                    )
                }
                // Guardar todos los productos en la lista completa
                allProducts = productsList
                // Inicialmente, mostrar todos los productos (sin filtro)
                artworks.value = allProducts
            }
            .addOnFailureListener {
                // Manejar errores si es necesario
            }
    }

    // Función para filtrar productos según la categoría seleccionada
    fun filterArtworks(category: String?) {
        artworks.value = if (category == null) {
            // Si no hay categoría seleccionada, mostrar todos los productos
            allProducts
        } else {
            // Si hay una categoría seleccionada, filtrar los productos por esa categoría
            allProducts.filter { it.category == category }
        }
    }
}