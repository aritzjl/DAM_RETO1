package com.reto1.ultramarinos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.Artwork
import com.reto1.ultramarinos.components.FAB
import com.reto1.ultramarinos.GalleryViewModel
import com.reto1.ultramarinos.components.ArtworkCard
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.is_single_column


//  GALLERY VIEW

@Composable
fun GalleryView(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    val viewModel: GalleryViewModel = viewModel()

    Scaffold(
        topBar = { ToolBar(viewModel) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = { FAB() }
    ) { innerPadding ->
        GalleryContent(paddingValues, viewModel.isSingleColumn, viewModel.artworks.value, innerPadding)
    }
}



@Composable
fun GalleryContent(
    paddingValues: PaddingValues,
    isSingleColumn: Boolean, // Cambiar a MutableState<Boolean>
    artworks: List<Artwork>, // Asegúrate de que este tipo sea List<Artwork>
    paddingValues2: PaddingValues,
) {
    LazyVerticalGrid(
        columns = if (is_single_column) GridCells.Fixed(1) else GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0.1f, 0.1f, 0.1f, 0.9f))
            .padding(paddingValues)
    ) {
        items(
            count = artworks.size,
            key = { index -> artworks[index].name }
        ) { index ->
            val artwork = artworks[index]
            ArtworkCard(artwork)
        }
    }
}
