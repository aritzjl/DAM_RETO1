package com.reto1.ultramarinos.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.reto1.ultramarinos.models.Product
import android.util.Log

class CartViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun addCartProduct(product: Product, quantity: Int, userEmail: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        // Consultar la colección "Productos" para encontrar el ID del documento por nombre
        db.collection("Productos")
            .whereEqualTo("nombre", product.title)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val productDocument = querySnapshot.documents.first()
                    val productId = productDocument.id

                    // Crear el cartItem usando el ID del documento
                    val cartItem = hashMapOf(
                        "cantidad" to quantity,
                        "producto" to db.collection("Productos").document(productId),
                        "user" to userEmail
                    )

                    // Agregar el cartItem a la colección "PedidoProducto"
                    db.collection("PedidoProducto")
                        .add(cartItem)
                        .addOnSuccessListener {
                            onSuccess() // Llamamos al callback de éxito
                        }
                        .addOnFailureListener { exception ->
                            onError(exception) // Llamamos al callback de error
                            Log.e("CartViewModel", "Error añadiendo producto al carrito", exception)
                        }
                } else {
                    Log.e("CartViewModel", "No se encontró el producto con el nombre: ${product.title}")
                    onError(Exception("Producto no encontrado"))
                }
            }
            .addOnFailureListener { exception ->
                onError(exception) // Llamamos al callback de error
                Log.e("CartViewModel", "Error consultando productos", exception)
            }
    }

    fun getUserCart(email: String, onCartLoaded: (List<Pair<Product, Int>>) -> Unit) {
        Log.d("CartViewModel", "Obteniendo el carrito para el usuario: $email")

        // Consultar la colección PedidoProducto para el usuario actual
        db.collection("PedidoProducto")
            .whereEqualTo("user", email)
            .get()
            .addOnSuccessListener { cartDocuments ->
                Log.d("CartViewModel", "Consulta exitosa. Documentos obtenidos: ${cartDocuments.size()}")

                if (!cartDocuments.isEmpty) {
                    val cartItems = mutableListOf<Pair<Product, Int>>()

                    // Iterar sobre cada documento en PedidoProducto
                    for (cartDoc in cartDocuments) {
                        val productRef = cartDoc.getDocumentReference("producto")
                        val cantidad = cartDoc.getLong("cantidad")?.toInt() ?: 0
                        Log.d("CartViewModel", "Documento: ${cartDoc.id}, Cantidad: $cantidad")

                        // Consultar la colección de Productos para obtener los detalles del producto
                        productRef?.get()
                            ?.addOnSuccessListener { productDocument ->
                                if (productDocument.exists()) {
                                    // Obtener los datos del producto y construir el objeto Product
                                    val product = Product(
                                        title = productDocument.getString("nombre") ?: "",
                                        description = productDocument.getString("descripcion") ?: "",
                                        imageUrl = productDocument.getString("foto") ?: "",
                                        price = productDocument.getDouble("precio")?.toFloat() ?: 0f,
                                        offerPrice = productDocument.getDouble("precio_oferta")?.toFloat(),
                                        unit = productDocument.getString("unidad"),
                                        category = productDocument.getString("categoria")
                                    )
                                    // Añadir el producto y su cantidad al carrito
                                    cartItems.add(product to cantidad)
                                    Log.d("CartViewModel", "Producto añadido: ${product.title}, Cantidad: $cantidad")

                                    // Si hemos procesado todos los productos, devolver la lista
                                    if (cartItems.size == cartDocuments.size()) {
                                        Log.d("CartViewModel", "Todos los productos procesados. Enviando carrito: $cartItems")
                                        onCartLoaded(cartItems)
                                    }
                                } else {
                                    Log.e("CartViewModel", "El producto no existe: ${productRef.path}")
                                }
                            }
                            ?.addOnFailureListener { e ->
                                Log.e("CartViewModel", "Error al obtener producto: $e")
                            }
                    }
                } else {
                    // Si el carrito está vacío, devolvemos una lista vacía
                    Log.d("CartViewModel", "El carrito está vacío para el usuario: $email")
                    onCartLoaded(emptyList())
                }
            }
            .addOnFailureListener { e ->
                Log.e("CartViewModel", "Error al obtener el carrito del usuario: $e")
            }
    }
}