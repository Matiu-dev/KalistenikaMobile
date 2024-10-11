package pl.matiu.kalistenika.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.model.training.TrainingModel

@Database(entities = [RepetitionExercise::class, TimeExercise::class, TrainingModel::class], version = 1)
abstract class ExerciseDatabase: RoomDatabase() {

    companion object {
        const val NAME = "Training_DB"
    }

    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTrainingDao(): TrainingDao
}