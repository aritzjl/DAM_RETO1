package com.reto1.ultramarinos.viewmodels;

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reto1.ultramarinos.models.Idioma

class MainViewModel(context: Context) : ViewModel() {

    // Temas
    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> = _darkTheme

    fun toggleTheme(context: Context) {
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
    private val _currentLanguage  = MutableLiveData<String>()
    val currentLanguage: LiveData<String> = _currentLanguage

    val allIdiomas = listOf(
        Idioma("Castellano","es"),
        Idioma("English","en"),
        Idioma("Euskara","eu"),
    )

    fun setLanguage(context: Context, idiomacod: String) {
        val prefs: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        _currentLanguage.value = idiomacod

        prefs.edit().putString("language", idiomacod).apply()
    }

    fun getLanguage(context: Context): String? {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        return prefs.getString("language", "es")
    }

}
