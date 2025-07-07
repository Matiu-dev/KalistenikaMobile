package pl.matiu.kalistenika.sharedPrefs

import android.app.Activity
import android.content.Context
import android.util.Log
import pl.matiu.kalistenika.sharedPrefs.saveSeries.SaveSeries

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

    override fun getIsSeriesActive(context: Context?): SaveSeries {
        Log.d("isSeriesActive", "getting series status")

        var saveSeries = SaveSeries()

        if(context is Activity) {
            val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
            saveSeries.isActive = sharedPref.getString("isActive", "false") ?: "false"
            saveSeries.trainingName = sharedPref.getString("trainingName", "") ?: ""
            saveSeries.trainingId = sharedPref.getString("trainingId", "") ?: ""
            saveSeries.actualPage = sharedPref.getString("actualPage", "") ?: ""
        }

        return saveSeries
    }

    override fun setIsSeriesActive(isActive: String, trainingName: String, trainingId: Int, actualPage: Int, context: Context?) {
        Log.d("isSeriesActive", "saving series status $isActive")

        if(context is Activity) {
            val sharedPref = context.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("isActive", isActive)
                putString("trainingName", trainingName)
                putString("trainingId", trainingId.toString())
                putString("actualPage", actualPage.toString())
                apply()
            }
        }
    }
}