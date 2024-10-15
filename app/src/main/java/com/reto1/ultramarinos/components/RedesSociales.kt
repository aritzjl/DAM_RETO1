package com.reto1.ultramarinos.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.reto1.ultramarinos.R

@Composable
fun RedesSociales(context: Context) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(10.dp)
            .padding(vertical = 30.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.facebook),
            contentDescription = "facebook",
            modifier = Modifier
                .size(70.dp)
                .clickable(onClick = {
                    val webIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.facebook.com/ultramarinosgregoriomartin")
                    }

                    context.startActivity(webIntent)
                })
        )
        Image(
            painter = painterResource(R.drawable.instagram),
            contentDescription = "instagram",
            modifier = Modifier
                .size(70.dp)
                .clickable(onClick = {
                    val webIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.instagram.com/ultramarinosgregoriomartin/")
                    }

                    context.startActivity(webIntent)
                })
        )
        Image(
            painter = painterResource(R.drawable.youtube),
            contentDescription = "youtube",
            modifier = Modifier
                .size(70.dp)
                .clickable(onClick = {
                    val webIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.youtube.com/channel/UCJ48FqMHiq1pNAtAk2S8T8w")
                    }

                    context.startActivity(webIntent)
                })
        )
    }
}