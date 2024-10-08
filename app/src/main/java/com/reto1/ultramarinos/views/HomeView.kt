package com.reto1.ultramarinos.views

// HomeView.kt

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource

// HomeView.kt

import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.viewmodels.MainViewModel
import com.reto1.ultramarinos.components.YouTubePlayer
import com.reto1.ultramarinos.viewmodels.GalleryViewModel

@Composable
fun HomeView(mainViewModel: MainViewModel, isLightMode: Boolean) {

    val navController = rememberNavController()
    Scaffold(
        topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeContent(paddingValues) }
                composable("about") { AboutView(paddingValues) }
                composable("gallery") { GalleryView(paddingValues) }
                composable("settings") { SettingsContent(paddingValues, mainViewModel, isLightMode) }
            }
        }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onPrimary)
        .padding(paddingValues)) {
        item {
            Text(
                text = "Inicio",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), textAlign = TextAlign.Center
            )

            Carrusel()

            Text(
                text = "Desde 1931 somos especialistas en bacalao, legumbres, quesos, embutidos, conservas, vinos, condimentos, chocolates...\n",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), textAlign = TextAlign.Center
            )
            Text(
                text = "Uno de nuestros secretos es que somos amantes de la gastronomía de calidad en Ultramarinos Gregorio Martín.\n" +
                        "Nuestra experiencia de estos años unido a una materia prima de calidad es la combinación perfecta para un producto 5 estrellas.",
                fontWeight = FontWeight.Thin,
                fontSize = 22.sp,
                lineHeight = 35.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, bottom = 40.dp), textAlign = TextAlign.Center
            )
            Text(
                text = "Productos destacados",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 35.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, bottom = 40.dp), textAlign = TextAlign.Center
            )

            Carrusel2(GalleryViewModel())

            YouTubePlayer(
                youtubeVideoId = "QG4oGxgnBBw",
                lifecycleOwner = LocalLifecycleOwner.current

            )

        }
    }
}

@Composable
fun Carrusel(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val fraction = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        0.8f // 80% width in landscape
    } else {
        1f // 100% width in portrait
    }

// meto las imagenes en una coleccion
    val images = listOf(

        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,

        )


    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize().fillMaxWidth(fraction)) {
            HorizontalPager(
                state = pagerState,
                modifier
                    .wrapContentSize()
                    .padding(26.dp) // donde se encuentra la trajeta
            ) {

                    currentPage ->
                Card(

                    modifier = Modifier
                        .size(height = 250.dp, width = 600.dp)


                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "", contentScale = ContentScale.FillBounds
                    )


                }

            }
        }

    }
}


@Composable
fun Carrusel2(viewModel: GalleryViewModel, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val fraction = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        0.8f // 80% width in landscape
    } else {
        1f // 100% width in portrait
    }

    val products = viewModel.artworks.value

    val pagerState = rememberPagerState(
        pageCount = { products.size }
    )

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(fraction)
                .height(350.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(26.dp)
            ) { currentPage ->
                val product = products[currentPage]
                Card(
                    modifier = Modifier
                        .size(height = 400.dp, width = 600.dp)
                ) {
                    Column {
                        Image(
                            painter = rememberAsyncImagePainter(model = product.imageUrl),
                            contentDescription = product.title,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = product.title,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = "${product.price} ${product.unit}",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

