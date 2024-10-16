package com.reto1.ultramarinos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.models.Product
import com.reto1.ultramarinos.components.FAB
import com.reto1.ultramarinos.viewmodels.GalleryViewModel
import com.reto1.ultramarinos.components.ProductPreview
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.CartModal
import com.reto1.ultramarinos.components.CategorySelector
import com.reto1.ultramarinos.components.ToolBar

//  GALLERY VIEW

@Composable
fun GalleryView(paddingValues: PaddingValues, galleryViewModel: GalleryViewModel, isLightMode: Boolean, email: String) {
    val navController = rememberNavController()
    val viewModel: GalleryViewModel = viewModel()

    Scaffold(
        topBar = { ToolBar(galleryViewModel, navController, isLightMode) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = { FAB(isLightMode, email) }
    ) { innerPadding ->
        GalleryContent(
            paddingValues = paddingValues,
            isSingleColumn = galleryViewModel.isSingleColumn,
            artworks = viewModel.artworks.value,
            paddingValues2 = innerPadding,
            onCategorySelected = { category -> viewModel.filterArtworks(category) } // Pasar la categoría seleccionada
        )
    }
}

@Composable
fun GalleryContent(
    paddingValues: PaddingValues,
    isSingleColumn: Boolean,
    artworks: List<Product>,
    paddingValues2: PaddingValues,
    onCategorySelected: (String?) -> Unit // Cambié ProductCategory? a String?
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        // Mostrar el selector de categorías
        CategorySelector(onCategorySelected = onCategorySelected)

        LazyVerticalGrid(
            columns = if (isSingleColumn) GridCells.Fixed(1) else GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(
                count = artworks.size,
                key = { index -> artworks[index].title }
            ) { index ->
                val artwork = artworks[index]
                ProductPreview(artwork)
            }
        }
    }
}
