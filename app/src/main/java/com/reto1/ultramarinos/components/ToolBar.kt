package com.reto1.ultramarinos.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.reto1.ultramarinos.GalleryViewModel
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.is_single_column
import com.reto1.ultramarinos.toolbarTitle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(viewModel: GalleryViewModel?) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = toolbarTitle, color = MaterialTheme.colorScheme.onSecondaryContainer) },
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
                val context = LocalContext.current
                IconButton(onClick = {
                    Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show()
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
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    )
}
