package com.reto1.ultramarinos.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.primaryDark
import com.example.compose.primaryLight
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.lightmode
import com.reto1.ultramarinos.models.Idioma
import com.reto1.ultramarinos.ui.theme.Theme


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
    var showMenu by remember { mutableStateOf(false) }
    var idioma = "Castellano"
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxSize()
            .background(if (lightmode) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onBackground)
            .padding(paddingValues)
            .padding(start = 16.dp)
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.img_1),
                contentDescription = "Icono",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Usuario",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = if (lightmode) Color.Black else Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (lightmode) R.drawable.baseline_email_perfil_24 else R.drawable.baseline_email_24),
                    contentDescription = "email",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(40.dp)
                )
                Text(
                    text = "email@email.com",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = if (lightmode) Color.Black else Color.White,
                    modifier = Modifier
                        .padding(start = 10.dp),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (lightmode) R.drawable.baseline_g_translate_24 else R.drawable.baseline_light_g_translate_24),
                    contentDescription = "traductor",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(40.dp)
                )
                TextButton(
                    onClick = {
                        showMenu = !showMenu
                    }
                )  {
                    Text(
                        text = "$idioma",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = if (lightmode) Color.Black else Color.White,
                        modifier = Modifier
                            .padding(start = 10.dp),
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Castellano")
                        },
                        onClick = {
                            idioma = "Castellano"
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "English")
                        },
                        onClick = {
                            idioma = "English"
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Euskara")
                        },
                        onClick = {
                            idioma = "Euskara"
                            showMenu = false
                        }
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                SwitchVista()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (lightmode) R.drawable.baseline_exit_to_app_24 else R.drawable.baseline_light_exit_to_app_24),
                    contentDescription = "cerrar",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(40.dp)
                )
                TextButton(
                    onClick = {}
                )  {
                    Text(
                        text = "Cerrar Sesión",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = if (lightmode) Color.Black else Color.White,
                        modifier = Modifier
                            .padding(start = 10.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchVista() {
    Image(
        painter = painterResource(
            id =
            if (lightmode) {
                R.drawable.baseline_light_mode_24
            } else {
                R.drawable.baseline_dark_mode_24
            }
        ),
        contentDescription = "switch",
        modifier = Modifier
            .padding(start = 5.dp)
            .size(40.dp)
    )
    Text(
        text =
        if (lightmode) {
            "Modo Claro"
        } else {
            "Modo Oscuro"
        },
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = if (lightmode) Color.Black else Color.White,
        modifier = Modifier
            .padding(10.dp),
    )
    Switch(
        checked = lightmode,
        onCheckedChange = {
            lightmode = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = MaterialTheme.colorScheme.onBackground,
            uncheckedThumbColor = Color.Black,
            uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}