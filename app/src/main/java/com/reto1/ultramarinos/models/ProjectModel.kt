package com.reto1.ultramarinos.models

data class Product(
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val offerPrice: Float? = null,
    val unit: String? = null,
    val category: String? = null,
)