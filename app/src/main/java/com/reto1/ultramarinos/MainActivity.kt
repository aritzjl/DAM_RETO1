package com.reto1.ultramarinos

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Gallery
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.Artwork
import com.reto1.ultramarinos.GalleryViewModel
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.ui.theme.AppTheme
import com.reto1.ultramarinos.ui.theme.ColorFamily
import com.reto1.ultramarinos.components.ArtworkCard
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

var total_likes by mutableIntStateOf(0)
var is_single_column by mutableStateOf(false)
var toolbarTitle by mutableStateOf("Inicio")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarTitle = "Hola"
        enableEdgeToEdge()
        setContent {
            HomeView()
        }
    }
}

@Composable
fun FAB(){
    val context = LocalContext.current
    FloatingActionButton(onClick = {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("aritzzjl@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "He visto tu portfolio de Kotlin")
        }
        if (emailIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(emailIntent)
        }
    }) {
        if (toolbarTitle != "Galería")
        {
            Image(painter= painterResource(R.drawable.baseline_email_24), contentDescription = "Icono", modifier= Modifier
                .width(48.dp)
                .clickable {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("aritzzjl@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "He visto tu portfolio de Kotlin")
                    }
                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(emailIntent)
                    }
                })
        }
        else {
            Image(painter= painterResource(R.drawable.baseline_add_circle_outline_24), contentDescription = "Icono", modifier= Modifier
                .width(48.dp)
                .clickable {

                })
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(viewModel: GalleryViewModel?) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = toolbarTitle, color = Color.Black) },
        actions = {
            if (toolbarTitle != "Galería") {
                IconButton(onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Echa un vistazo a mi web https://github.com/aritzjl")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Compartir vía"))
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.share_icon),
                        contentDescription = "Compartir",
                        tint = Color.Black
                    )
                }
            } else {
                IconButton(onClick = {
                    is_single_column = !is_single_column
                }) {
                    val icon = if (is_single_column) {
                        painterResource(id = R.drawable.baseline_apps_24) // Icono de cuadrícula
                    } else {
                        painterResource(id = R.drawable.baseline_view_list_24) // Icono de lista
                    }
                    Icon(
                        painter = icon,
                        contentDescription = "Alternar vista",
                        tint = Color.Black
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}


@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = {
                toolbarTitle = "Inicio"
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            selected = navController.currentBackStackEntry?.destination?.route == "about",
            onClick = {
                toolbarTitle = "Sobre mí"
                navController.navigate("about") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Create, contentDescription = "Gallery") },
            selected = navController.currentBackStackEntry?.destination?.route == "gallery",
            onClick = {
                toolbarTitle = "Galería"
                navController.navigate("gallery") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = navController.currentBackStackEntry?.destination?.route == "settings",
            onClick = {
                toolbarTitle = "Ajustes"
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

//  SETTINGS VIEW
@Preview
@Composable
fun SettingsView() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeContent(paddingValues) }
                composable("about") { AboutView(paddingValues) }
                composable("gallery") { GalleryView(paddingValues) }
                composable("settings") { SettingsContent(paddingValues) }
            }
        }
    )
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
        .padding(paddingValues)) {
        item {
            Text(text = "Ajustes", fontWeight = FontWeight.ExtraBold ,fontSize = 32.sp, color = Color.White, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), textAlign = TextAlign.Center)
        }
    }
}
//  HOME VIEW
@Preview
@Composable
fun HomeView() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeContent(paddingValues) }
                composable("about") { AboutView(paddingValues) }
                composable("gallery") { GalleryView(paddingValues) }
                composable("settings") { SettingsContent(paddingValues) }
            }
        }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)) {
        item {
            Text(text = "Inicio", fontWeight = FontWeight.ExtraBold ,fontSize = 32.sp, color = Color.White, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), textAlign = TextAlign.Center)
        }
    }
}

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutView(paddingValues2: PaddingValues) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { ToolBar(null) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = { FAB() },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "about") {
                composable("about") { AboutContent(paddingValues2) }
            }
        }
    )
}



@Composable
fun AboutContent(paddingValues: PaddingValues) {
    val counter by remember { derivedStateOf { total_likes } }

    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
        .padding(paddingValues))
    {
        item{
            Image(painter= painterResource(R.drawable.img_1), contentDescription = "Icono", modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(
                    CircleShape
                ))
            Text(text = "Aritz Jaber", fontWeight = FontWeight.ExtraBold ,fontSize = 32.sp, color = Color.White, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), textAlign = TextAlign.Center)
            Text(text = "** Full Stack Developer **", fontWeight = FontWeight.SemiBold ,fontSize = 28.sp, color = Color.LightGray, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), textAlign = TextAlign.Center)

        }
    }

}
