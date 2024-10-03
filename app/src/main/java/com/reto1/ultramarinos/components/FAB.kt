package com.reto1.ultramarinos.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.toolbarTitle


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
        if (toolbarTitle != "Productos")
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
            Image(painter= painterResource(R.drawable.baseline_shopping_cart_24), contentDescription = "Icono", modifier= Modifier
                .width(48.dp)
                .clickable {

                })
        }

    }
}