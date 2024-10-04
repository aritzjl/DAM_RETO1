package com.reto1.ultramarinos.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.reto1.ultramarinos.models.Product
import com.reto1.ultramarinos.models.ProductCategory

class GalleryViewModel : ViewModel() {
    var isSingleColumn by mutableStateOf(false)
        private set

    var selectedCategory by mutableStateOf<ProductCategory?>(null)
        private set

    fun toggleColumnCount() {
        isSingleColumn = !isSingleColumn
    }

    var artworks = mutableStateOf(sampleArtworks())

    fun filterArtworks(category: ProductCategory?) {
        selectedCategory = category
        artworks.value = sampleArtworks().filter { product ->
            category == null || product.category == category
        }
    }

    private fun sampleArtworks(): List<Product> {
        return listOf(
            Product(
                title = "Bacalada con espinas",
                description = "Bacalada con espinas, origen bacalao de Islandia. \n" +
                        "\n" +
                        "para consumir necesita un proceso de entre 2 y 3  días de ponerlo en mucha agua dentro de la nevera y cambiar ese agua 3 veces al dia, es importante este proceso por 2 cuestiones, la primera por desalar el bacalao y la segunda es por hidratar ese bacalao y que ahueque su carne.",
                imageUrl = "https://www.gregoriomartin.es/103-home_default/bacalada-con-espinas.jpg",
                price = 100.0f,
                offerPrice = 90.0f,
                unit = "Kg",
                category = ProductCategory.Pescado
            ),
            Product(
                title = "Arándano rojo",
                description = "Este producto no tiene descripción",
                imageUrl = "https://www.gregoriomartin.es/52-large_default/arandano-rojo.jpg",
                price = 500.0f,
                offerPrice = 450.0f,
                unit = "Kg",
            ),
            Product(
                title = "Sistema de predicción de precios para Order Inn",
                description = "Cada tajada pesa entre 275 a 300gr.",
                imageUrl = "https://www.gregoriomartin.es/22-large_default/bacalao-desalado-275-300gr.jpg",
                price = 200.0f,
                offerPrice = null,
                unit = "Kg",
                category = ProductCategory.Pescado
            ),
            Product(
                title = "Alubia Agarbanzada",
                description = "Este producto no tiene descripción",
                imageUrl = "https://www.gregoriomartin.es/55-large_default/alubia-agarbanzada.jpg",
                price = 300.0f,
                offerPrice = 250.0f,
                unit = "Kg",
                category = ProductCategory.Legumbres
            ),
            Product(
                title = "Bonito del norte en aceite de oliva ARROYABE 280gr",
                description = "Este producto no tiene descripción.",
                imageUrl = "https://www.gregoriomartin.es/79-large_default/bonito-del-norte-en-aceite-de-oliva-arroyabe-280gr.jpg",
                price = 600.0f,
                offerPrice = null,
                unit = "Kg",
                category = ProductCategory.Conservas
            )
        )
    }
}