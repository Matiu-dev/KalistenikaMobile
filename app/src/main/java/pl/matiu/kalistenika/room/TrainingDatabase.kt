package pl.matiu.kalistenika.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.matiu.kalistenika.trainingModel.TrainingModel

@Database(entities = [TrainingModel::class], version = 1)
abstract class TrainingDatabase: RoomDatabase() {

    companion object {
        const val NAME = "Training_DB"
    }

    abstract fun getTrainingDao(): TrainingDao
}