package com.reto1.ultramarinos.models

abstract class Item {
    abstract val title: String
    abstract val description: String
    abstract val price: Float
}

data class Product(
    override val title: String,
    override val description: String,
    val imageUrl: String,
    override val price: Float,
    val offerPrice: Float? = null,
    val unit: String? = null,
    val category: String? = null,

    val title_eus: String? = null,
    val title_en: String? = null,

    val description_eus: String? = null,
    val description_en: String? = null,
) : Item()
