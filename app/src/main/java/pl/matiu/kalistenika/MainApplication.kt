package pl.matiu.kalistenika

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.exerciseApi.NinjaApi
import pl.matiu.kalistenika.media.StartSong
import pl.matiu.kalistenika.room.ExerciseDatabase
import pl.matiu.kalistenika.ui.StartTimeSeries

@HiltAndroidApp
class MainApplication: Application() {

    companion object {
        lateinit var exerciseDatabase: ExerciseDatabase
    }

    override fun onCreate() {
        super.onCreate()

        exerciseDatabase = Room.databaseBuilder(
            applicationContext,
            ExerciseDatabase::class.java,
            ExerciseDatabase.NAME
        ).build()
    }
}