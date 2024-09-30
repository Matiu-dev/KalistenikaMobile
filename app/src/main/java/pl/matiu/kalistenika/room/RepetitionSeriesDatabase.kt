package pl.matiu.kalistenika.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.TrainingModel

@Database(entities = [RepetitionExercise::class], version = 1)
abstract class RepetitionSeriesDatabase: RoomDatabase() {

    companion object {
        const val NAME = "Repetition_Exercise_DB"
    }

    abstract fun getRepetitionSeriesDao(): RepetitionSeriesDao
}