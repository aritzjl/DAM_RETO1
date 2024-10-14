package com.reto1.ultramarinos.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reto1.ultramarinos.MainActivity
import com.reto1.ultramarinos.R
import com.reto1.ultramarinos.Register
import com.reto1.ultramarinos.models.Idioma
import com.reto1.ultramarinos.viewmodels.MainViewModel


@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    isLightMode: Boolean,
    idiomaList: List<Idioma>,
    activity: Context,
    context: Context,
    register: Register,
) {
    var showMenu by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(idiomaList.first { it.codigo == mainViewModel.getLanguage(activity) }) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
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
                color = MaterialTheme.colorScheme.onBackground,
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
                        id = if (isLightMode) R.drawable.baseline_email_24 else R.drawable.baseline_email_perfil_24),
                    contentDescription = "email",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(40.dp)
                )
                Text(
                    text = "email@email.com",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
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
                        id = if (isLightMode) R.drawable.baseline_light_g_translate_24 else R.drawable.baseline_g_translate_24),
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
                        text = selectedItem.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(start = 10.dp),
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    for (i in idiomaList.indices) {
                        val item = idiomaList[i]
                        DropdownMenuItem(
                            text = {
                                Text(text = item.title)
                            },
                            onClick = {
                                selectedItem = item
                                val idiomaNuevo = selectedItem.codigo
                                showMenu = false

                                // Update language preference
                                mainViewModel.setLanguage(activity,idiomaNuevo)

                                // Change language using CambiarIdiomaClass
                                mainViewModel.cambiarIdiomaClass.setLocale(activity,idiomaNuevo)

                                // Reload the current activity
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                activity.startActivity(intent)
//                                (activity as AppCompatActivity).finish()

                            }
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                SwitchVista(isLightMode = !mainViewModel.darkTheme.value!!, mainViewModel = mainViewModel, context = context)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (isLightMode) R.drawable.baseline_light_exit_to_app_24 else R.drawable.baseline_exit_to_app_24),
                    contentDescription = "cerrar",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(40.dp)
                )
                TextButton(
                    onClick = {
                        register.logOut()
                    }
                )  {
                    Text(
                        text = stringResource(id = R.string.log_out),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(start = 10.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchVista(isLightMode: Boolean, mainViewModel: MainViewModel, context: Context) {
    Image(
        painter = painterResource(
            id =
            if (isLightMode) {
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
        if (isLightMode) {
            stringResource(id = R.string.light_mode)
        } else {
            stringResource(id = R.string.dark_mode)
        },
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .padding(10.dp),
    )
    Switch(
        checked = isLightMode,
        enabled = true,
        onCheckedChange = {
            mainViewModel.toggleTheme(context)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
            checkedTrackColor = MaterialTheme.colorScheme.onBackground,
            uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedTrackColor = MaterialTheme.colorScheme.onBackground
        )
    )
}