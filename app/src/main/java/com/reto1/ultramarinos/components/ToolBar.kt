package com.reto1.ultramarinos.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.reto1.ultramarinos.viewmodels.GalleryViewModel
import com.reto1.ultramarinos.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(galeryViewModel: GalleryViewModel, navController: NavController, isLightMode: Boolean) {
    val context = LocalContext.current
    val actualView = navController.currentBackStackEntryAsState().value?.destination?.route

    TopAppBar(
        title = { Text(text = "", color = MaterialTheme.colorScheme.onSecondaryContainer) },
        actions = {
            if (actualView != "gallery") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_utramarinos),
                        contentDescription = "logo",
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .size(80.dp)
                            .alpha(0.6f)
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        onClick = {
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
                            tint = if (isLightMode) Color.White else Color.Black
                        )
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_utramarinos),
                        contentDescription = "logo",
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .size(80.dp)
                            .alpha(0.6f)
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        onClick = {
                        galeryViewModel.isSingleColumn = !galeryViewModel.isSingleColumn
                    }) {
                        val icon = if (galeryViewModel.isSingleColumn) {
                            painterResource(id = R.drawable.baseline_apps_24) // Icono de cuadrícula
                        } else {
                            painterResource(id = R.drawable.baseline_view_list_24) // Icono de lista
                        }
                        Icon(
                            painter = icon,
                            contentDescription = "Alternar vista",
                            tint = if (isLightMode) Color.White else Color.Black
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    )
}
