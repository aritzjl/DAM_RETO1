package com.reto1.ultramarinos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    var isSingleColumn by mutableStateOf(false)
        private set

    fun toggleColumnCount() {
        isSingleColumn = !isSingleColumn
    }
    var artworks = mutableStateOf(sampleArtworks())

    private fun sampleArtworks(): List<Artwork> {
        return listOf(
            Artwork(
                name = "PadelChiquito",
                title = "PadelChiquito",
                description = "Comparador de carácterísticas de palas de Padle y precios de diferentes tiendas.",
                creationDate = "2023-03-15",
                style = ArtworkStyle.WATERCOLOUR,
                imageResId = R.drawable.img_11 // Asegúrate de tener este recurso de imagen
            ),
            Artwork(
                name = "Capital Fondeo",
                title = "Capital Fondeo",
                description = "Web de venta de cuentas fondeadas para trading.",
                creationDate = "2024-01-10",
                style = ArtworkStyle.DIGITAL,
                imageResId = R.drawable.img_12 // Asegúrate de tener este recurso de imagen
            ),
            Artwork(
                name = "Predicción Precios",
                title = "Sistema de predicción de precios para Order Inn",
                description = "Web con modelo de machine learning entrenado con datos de más de 50mil productos de Order Inn, utilizado para predecir los precios de futuros productos. Admeás, incorpora OCR y actualizaciones automáticas con lenguaje natural conectando el código fuente con OpenAI",
                creationDate = "2022-11-05",
                style = ArtworkStyle.INK,
                imageResId = R.drawable.img_13 // Asegúrate de tener este recurso de imagen
            ),
            Artwork(
                name = "Gestion Minera",
                title = "Gestion Minera",
                description = "Herramienta de gesitón interna para empresa minera de Perú",
                creationDate = "2023-07-22",
                style = ArtworkStyle.WATERCOLOUR,
                imageResId = R.drawable.img_14 // Asegúrate de tener este recurso de imagen
            ),
            Artwork(
                name = "Programa Trading Amazon",
                title = "Programa Trading Amazon",
                description = "Programa que genera excel con los productos que cumplan los criterios solciitados en la interfaz para la compraventa de items en Amazon.",
                creationDate = "2024-02-14",
                style = ArtworkStyle.DIGITAL,
                imageResId = R.drawable.img_15 // Asegúrate de tener este recurso de imagen
            )
        )
    }

}