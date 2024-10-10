package com.reto1.ultramarinos;

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class CambiarIdiomaClass {

    fun cambiarIdioma(context: Context, languageCodigo: String) {

        // version >= 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageCodigo)
        } else {
            // version < 13
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(languageCodigo)
            )
        }
    }

    fun getIdiomaCode(context: Context,): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales[0].toLanguageTag()
                .split("-").first().toString()
        } else {
            context.resources.configuration.locales[0]?.toLanguageTag()?.split("-")?.first().toString()
        }
    }
}
