package com.reto1.ultramarinos.viewmodels;

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reto1.ultramarinos.CambiarIdiomaClass
import com.reto1.ultramarinos.models.Idioma

class MainViewModel(context: Context) : ViewModel() {

    // Temas
    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> = _darkTheme

    fun toggleTheme(context: Context){
        val prefs: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        // Cambia el value del tema
        _darkTheme.value = _darkTheme.value?.not()

        // Guarda el nuevo value en SharedPreference
        prefs.edit().putBoolean("dark_theme",_darkTheme.value ?: false).apply()
    }

    fun getTheme(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        // Initialize _darkTheme based on SharedPreferences
        _darkTheme.value = prefs.getBoolean("dark_theme", false)
    }

    // Idiomas
    val cambiarIdiomaClass by lazy {
        CambiarIdiomaClass()
    }

    fun setLanguage(context: Context, idioma: String) {
        val prefs: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        prefs.edit().putString("language", idioma).apply()
    }

    fun getLanguage(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "es")
    }

    val allIdiomas = listOf(
        Idioma("Castellano","es"),
        Idioma("English","en"),
        Idioma("Euskara","eu"),
    )

}
