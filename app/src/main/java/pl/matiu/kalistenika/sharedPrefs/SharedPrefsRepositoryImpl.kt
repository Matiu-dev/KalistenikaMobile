package pl.matiu.kalistenika.sharedPrefs

import android.app.Activity
import android.content.Context
import android.util.Log

class SharedPrefsRepositoryImpl: SharedPrefsRepository {
    override fun getLocalLanguage(context: Context?): String {
        Log.d("language", "get local language")

        var appLanguage = "pl"

        if(context is Activity) {
            val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
            appLanguage = sharedPref.getString("appLanguage", "pl") ?: "pl"
        }

        return appLanguage
    }

    override fun setLocalLanguage(language: String, context: Context?) {
        Log.d("language", "set local language")

        if(context is Activity) {
            val sharedPref = context.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("appLanguage", language)
                apply()
            }
        }
    }
}