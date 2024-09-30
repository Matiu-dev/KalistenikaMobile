package pl.matiu.kalistenika.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.matiu.kalistenika.trainingModel.TrainingModel

@Dao
interface TrainingDao {
    @Query("SELECT * FROM TrainingModel")
    fun getAllTrainings(): List<TrainingModel>
    @Insert
    fun addTraining(training: TrainingModel)
    @Query("DELETE FROM TrainingModel where trainingId = :trainingId")
    fun deleteTraining(trainingId: Int)
}