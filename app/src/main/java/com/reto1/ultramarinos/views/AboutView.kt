package com.reto1.ultramarinos.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reto1.ultramarinos.components.FAB
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.components.BottomNavBar
import com.reto1.ultramarinos.components.ToolBar
import com.reto1.ultramarinos.lightmode


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
    LazyColumn(modifier= Modifier
        .fillMaxSize()
        .background(if (lightmode) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onBackground)
        .padding(paddingValues)) {
        item {
            Text(
                text = "ULTRAMARINOS GREGORIO MARTIN",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "The Beginning",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "In 1835, José María Gurtubay, a Bilbao merchant, made a mistake in his order to his suppliers. He wrote \"Envíenme primer barco que toque puerto de Bilbao 100 o 120 bacaladas primera superior\". However, the \"o\" was misinterpreted as a 0, and instead of receiving 100 or 120 bacaladas, he received **1,000,120**! This unexpected surplus of bacalao became the staple food for the people of Bilbao during the summer of that year, leading to the creation of many recipes.",
                fontSize = 12.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            Text(
                text = "The Tradition Continues",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "In the following decades, bacalao became an essential ingredient in traditional Bilbao cuisine, and bacaladerías (dried cod shops) became a common sight in the city's streets and squares. Bilbao and bacalao were forever linked.",
                fontSize = 12.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            Text(
                text = "Gregorio Martín: A Visionary Entrepreneur",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "In 1931, Gregorio Martín, a young entrepreneur from Medina del Campo, opened his own business in Artecalle 22, Bilbao. Thanks to his friendship with an importer, Gregorio innovated his business by specializing in bacalao and placed a sign that read: \"Gregorio Martín. Especialidad en bacalao remojado todos los días. Tno.- 13.707\". This marked the beginning of \"La Bacalada\" de Artecalle, a business that would become a staple in Bilbao's culinary scene.",
                fontSize = 12.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            Text(
                text = "Our Story Today",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Today, at Gregorio Martín, we continue to offer the same high-quality products, including enormous bacaladas, bulk legumes, cheeses, cured meats, preserves, wines, condiments, and chocolates. Our secret lies in the origin of our products and the personalized service our employees have provided for decades.",
                fontSize = 12.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            Text(
                text = "Our Goal",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "We aim to continue providing the best products at the best prices, while maintaining our authenticity and professionalism. We believe that our personalized service and high-quality products set us apart, and we're proud to be a reference point in Bilbao's culinary scene.",
                fontSize = 12.sp,
                color = if (lightmode) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )
        }
    }
}
