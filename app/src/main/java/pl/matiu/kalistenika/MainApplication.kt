package pl.matiu.kalistenika

import android.app.Application
import androidx.room.Room
import pl.matiu.kalistenika.room.ExerciseDatabase

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
        )
            .allowMainThreadQueries()
            .build()
    }
}