package com.reto1.ultramarinos.views

// HomeView.kt

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource

// HomeView.kt

import androidx.compose.material3.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.reto1.ultramarinos.components.MiHilo
import com.reto1.ultramarinos.components.ProductPreview
import com.reto1.ultramarinos.components.Timer
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.components.YouTubePlayer
import com.reto1.ultramarinos.models.Idioma
import com.reto1.ultramarinos.viewmodels.GalleryViewModel
import com.reto1.ultramarinos.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeView(
    mainViewModel: MainViewModel,
    isLightMode: Boolean,
    idiomaList: List<Idioma>,
    idiomaActual: String,
    onIdiomaActualChange: (Idioma, Activity) -> Unit,
) {

    val activity = LocalContext.current as Activity
    val navController = rememberNavController()
    Scaffold(topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeContent(paddingValues) }
                composable("about") { AboutContent(paddingValues) }
                composable("gallery") { GalleryView(paddingValues) }
                composable("settings") {
                    SettingsContent(
                        paddingValues,
                        mainViewModel,
                        isLightMode,
                        idiomaList,
                        idiomaActual,
                        onIdiomaActualChange,
                        activity
                    )
                }
            }
        })
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    val hilo = MiHilo()
    hilo.start()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(paddingValues)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.home_main_title),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_utramarinos),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(256.dp)
                        .alpha(0.6f)
                )
            }

            Carrusel()

            Text(
                text = stringResource(id = R.string.home_text_1),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.home_text_2),
                fontWeight = FontWeight.Thin,
                fontSize = 22.sp,
                lineHeight = 35.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 40.dp),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.home_text_3),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 35.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, bottom = 40.dp),
                textAlign = TextAlign.Center
            )

            Timer()
            Carrusel2(GalleryViewModel())

            YouTubePlayer(
                youtubeVideoId = "QG4oGxgnBBw", lifecycleOwner = LocalLifecycleOwner.current

            )

        }
    }
}

@Composable
fun Carrusel(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val fraction = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        0.9f // 80% width in landscape
    } else {
        1f // 100% width in portrait
    }

    // meto las imagenes en una coleccion
    val images = listOf(

        R.drawable.imgcarruselpresentacion1,
        R.drawable.imgcarruselpresentacion2,
        R.drawable.imgcarruselpresentacion3,
        R.drawable.imgcarruselpresentacion4

    )


    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .fillMaxWidth(fraction)
                .clip(RoundedCornerShape(0.dp))
        ) {
            HorizontalPager(
                state = pagerState, modifier.wrapContentSize()

            ) {

                    currentPage ->
                Card(

                    modifier = Modifier
                        .height(height = 250.dp)
                        .fillMaxWidth()


                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )


                }

            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < images.size) {
                        scope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(
                        0x52373737
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }

            IconButton(
                onClick = {
                    val previousPage = pagerState.currentPage - 1
                    if (previousPage >= 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(previousPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(
                        0x52373737
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }

        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(top = 8.dp, bottom = 14.dp, start = 5.dp, end = 5.dp)
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }

    }

}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {

    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(color = if (isSelected) Color.Black else Color.LightGray)
    )
}


@Composable
fun Carrusel2(viewModel: GalleryViewModel, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val fraction = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        0.9f // 80% width in landscape
    } else {
        1f // 100% width in portrait
    }

    val products = viewModel.artworks.value

    val pagerState = rememberPagerState(pageCount = { products.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(fraction)
                .height(500.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .wrapContentSize()
                    .padding(26.dp)
            ) { currentPage ->
                val product = products[currentPage]
                Card(
                    modifier = Modifier
                        .height(height = 500.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        ProductPreview(product)
                    }
                }
            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < products.size) {
                        scope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(
                        0x52373737
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }

            IconButton(
                onClick = {
                    val previousPage = pagerState.currentPage - 1
                    if (previousPage >= 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(previousPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(
                        0x52373737
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }

        }

        PageIndicator(
            pageCount = products.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(top = 8.dp, bottom = 14.dp, start = 5.dp, end = 5.dp)
        )
    }
}


