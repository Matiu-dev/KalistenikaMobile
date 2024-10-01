package pl.matiu.kalistenika.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.TimeExercise

@Dao
interface TimeSeriesDao {
    @Query("SELECT * FROM TimeExercise")
    fun getAllTimeExercise(): List<TimeExercise>
    @Query("SELECT * FROM TimeExercise WHERE trainingId = :trainingId")
    fun getTimeExerciseByTrainingId(trainingId: Int): List<TimeExercise>
    @Query("SELECT * FROM TimeExercise WHERE exerciseId = :exerciseId")
    fun getTimeExerciseById(exerciseId: Int): TimeExercise
    @Insert
    fun addTimeExercise(exercise: TimeExercise)
    @Query("DELETE FROM TimeExercise where exerciseId = :exerciseId")
    fun deleteTimeExercise(exerciseId: Int)
    @Query("DELETE FROM TimeExercise WHERE trainingId = :trainingId")
    fun deleteTimeExerciseByTrainingId(trainingId: Int)
    @Query("UPDATE TimeExercise SET positionInTraining = positionInTraining + 1 WHERE trainingId = :trainingId AND positionInTraining >= :newExerciseNumber")
    fun updatePositionWhileAdding(trainingId: Int, newExerciseNumber: Int)
    @Query("UPDATE TimeExercise SET positionInTraining = positionInTraining - 1 WHERE trainingId = :trainingId AND positionInTraining >= :newExerciseNumber")
    fun updatePositionWhileDeleting(trainingId: Int, newExerciseNumber: Int)
}