package pl.matiu.kalistenika.sharedPrefs

import android.app.Activity
import android.content.Context
import pl.matiu.kalistenika.sharedPrefs.saveSeries.SaveSeries

interface SharedPrefsRepository {
    fun getLocalLanguage(context: Context?): String
    fun setLocalLanguage(language: String, context: Context?)
    fun getIsSeriesActive(context: Context?): SaveSeries
    fun setIsSeriesActive(isActive: String, trainingName: String, trainingId: Int, actualPage: Int, context: Context?)
}