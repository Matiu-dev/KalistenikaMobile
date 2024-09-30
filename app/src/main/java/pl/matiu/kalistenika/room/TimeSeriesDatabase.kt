package pl.matiu.kalistenika.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.TimeExercise

@Database(entities = [TimeExercise::class], version = 1)
abstract class TimeSeriesDatabase: RoomDatabase() {

    companion object {
        const val NAME = "Time_Exercise_DB"
    }

    abstract fun getTimeSeriesDao(): TimeSeriesDao
}