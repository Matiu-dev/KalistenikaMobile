package pl.matiu.kalistenika.language

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import pl.matiu.kalistenika.sharedPrefs.SharedPrefsRepository
import java.util.Locale
import javax.inject.Inject

class AppLanguage @Inject constructor(private val sharedPrefsRepository: SharedPrefsRepository){

    fun setLocalLanguage(language: String, context: Context?) {
        sharedPrefsRepository.setLocalLanguage(language, context = context)
    }

    fun changeLanguage(context: Context?) {
        (context as ComponentActivity).changeLanguage()
    }

    private fun ComponentActivity.changeLanguage() {

        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(sharedPrefsRepository.getLocalLanguage(this)))

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            this.changeLanguageOnApiBeforeTiramisu(language = sharedPrefsRepository.getLocalLanguage(this))
        } else {
            getSystemService(LocaleManager::class.java)?.applicationLocales = LocaleList.forLanguageTags(sharedPrefsRepository.getLocalLanguage(this))
        }
    }



    private fun Context.changeLanguageOnApiBeforeTiramisu(language: String) {
        val locale = Locale(language)
        val configuration = Configuration(this.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        this.resources.updateConfiguration(configuration, this.resources.displayMetrics)
        this.createConfigurationContext(configuration)
    }
}