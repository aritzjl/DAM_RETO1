package com.reto1.ultramarinos

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

class CambiarIdiomaClass {

    fun cambiarIdioma(context: Context, languageCodigo: String) {
        // version >= Android 13 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)?.applicationLocales =
                LocaleList.forLanguageTags(languageCodigo)
        } else {
            // version < 13
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(languageCodigo)
            )
        }
    }

    fun setLocale(context: Context, language: String): Context {
        var newContext = context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            newContext = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
        return newContext
    }
}
