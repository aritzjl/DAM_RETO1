package com.reto1.ultramarinos

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

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

    fun getIdiomaCode(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Retrieve the locale list and check if it's not empty
            val locales = context.getSystemService(LocaleManager::class.java)?.applicationLocales
            if (locales != null && locales.size() > 0) {
                locales[0]?.toLanguageTag()?.split("-")?.first().orEmpty()
            } else {
                "es" // default to English or any other fallback language
            }
        } else {
            // Fallback for versions below Android 13
            val locales = context.resources.configuration.locales
            if (locales != null && locales.size() > 0) {
                locales[0]?.toLanguageTag()?.split("-")?.first().orEmpty()
            } else {
                "es" // default to English or any other fallback language
            }
        }
    }
}
