package com.reto1.ultramarinos.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.unit.dp
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.viewmodels.GalleryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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