package pl.matiu.kalistenika.sharedPrefs

import android.app.Activity
import android.content.Context

interface SharedPrefsRepository {
    fun getLocalLanguage(context: Context?): String
    fun setLocalLanguage(language: String, context: Context?)
}