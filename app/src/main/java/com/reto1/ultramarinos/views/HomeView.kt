package com.reto1.ultramarinos.views

// HomeView.kt

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.CambiarIdiomaClass
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.Register
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.Carrusel
import com.reto1.ultramarinos.components.Carrusel2
import com.reto1.ultramarinos.components.RedesSociales
import com.reto1.ultramarinos.components.Timer
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.components.YouTubePlayer
import com.reto1.ultramarinos.models.Idioma
import com.reto1.ultramarinos.viewmodels.GalleryViewModel
import com.reto1.ultramarinos.viewmodels.MainViewModel
import java.util.Locale


@Composable
fun HomeView(
    mainViewModel: MainViewModel,
    cambiarIdiomaClass: CambiarIdiomaClass,
    isLightMode: Boolean,
    context: Context,
    register: Register,
    email: String,
    language: String,
    galleryViewModel: GalleryViewModel
) {

    val navController = rememberNavController()
    Scaffold(topBar = { ToolBar(galleryViewModel, navController, isLightMode) },
        bottomBar = { BottomNavBar(navController) },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeContent(paddingValues, email, language, context, register) }
                composable("about") { AboutContent(paddingValues, context) }
                composable("gallery") { GalleryView(paddingValues, galleryViewModel, isLightMode, email, language, register) }
                composable("settings") { SettingsContent(
                    paddingValues, mainViewModel, cambiarIdiomaClass, isLightMode,
                    context, register, email
                ) }
            }
        })
}

@Composable
fun HomeContent(paddingValues: PaddingValues, email: String, language: String, context: Context, register:Register) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(paddingValues)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.nav_home),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

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
            Carrusel2(GalleryViewModel(), email, language, register)

            YouTubePlayer(
                youtubeVideoId = "QG4oGxgnBBw", lifecycleOwner = LocalLifecycleOwner.current

            )

            RedesSociales(context = context)
        }
    }
}







