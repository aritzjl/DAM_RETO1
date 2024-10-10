package com.reto1.ultramarinos.viewmodels;

import android.app.Activity
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reto1.ultramarinos.CambiarIdiomaClass
import com.reto1.ultramarinos.MainActivity
import com.reto1.ultramarinos.models.Idioma
import java.util.Locale

class MainViewModel(private val context: Context) : ViewModel() {

    // Temas
    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> = _darkTheme

    fun toggleTheme(){
        _darkTheme.value = _darkTheme.value?.not()
    }

    // Idiomas

    val cambiarIdiomaClass by lazy {
        CambiarIdiomaClass()
    }

    private val _idioma = MutableLiveData<String>()
    val idioma: LiveData<String> = _idioma

    val allIdiomas = listOf(
        Idioma("Castellano","es"),
        Idioma("English","en"),
        Idioma("Euskara","eu"),
    )

    fun onCurrentLanguageChange(idioma: Idioma, activity: Activity){
        val locale = Locale(idioma.codigo)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.locale = locale
        context.createConfigurationContext(config)
        _idioma.value = idioma.codigo
        cambiarIdiomaClass.cambiarIdioma(context, idioma.codigo)
        activity.recreate()
    }

    init {
        // Temas
        _darkTheme.value = false

        // Idiomas
        val currentLanguageCode: String = cambiarIdiomaClass.getIdiomaCode(context)
        _idioma.value = currentLanguageCode
    }

}
