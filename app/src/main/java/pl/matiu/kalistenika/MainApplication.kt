package pl.matiu.kalistenika

import android.app.Application
import androidx.room.Room
import pl.matiu.kalistenika.room.RepetitionSeriesDatabase
import pl.matiu.kalistenika.room.TimeSeriesDatabase
import pl.matiu.kalistenika.room.TrainingDatabase

class MainApplication: Application() {

    companion object {
        lateinit var trainingDatabase: TrainingDatabase
        lateinit var timeSeriesDatabase: TimeSeriesDatabase
        lateinit var repetitionSeriesDatabase: RepetitionSeriesDatabase
    }

    override fun onCreate() {
        super.onCreate()
        trainingDatabase = Room.databaseBuilder(
            applicationContext,
            TrainingDatabase::class.java,
            TrainingDatabase.NAME
        )
            .allowMainThreadQueries()
            .build()

        timeSeriesDatabase = Room.databaseBuilder(
            applicationContext,
            TimeSeriesDatabase::class.java,
            TimeSeriesDatabase.NAME
        )
            .allowMainThreadQueries()
            .build()

        repetitionSeriesDatabase = Room.databaseBuilder(
            applicationContext,
            RepetitionSeriesDatabase::class.java,
            RepetitionSeriesDatabase.NAME
        )
            .allowMainThreadQueries()
            .build()
    }
}