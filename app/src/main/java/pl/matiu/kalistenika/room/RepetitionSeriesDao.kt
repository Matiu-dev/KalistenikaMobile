package pl.matiu.kalistenika.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.matiu.kalistenika.trainingModel.RepetitionExercise

@Dao
interface RepetitionSeriesDao {

    @Query("SELECT * FROM RepetitionExercise")
    fun getAllRepetitionExercise(): List<RepetitionExercise>
    @Query("SELECT * FROM RepetitionExercise WHERE trainingId = :trainingId")
    fun getRepetitionExerciseByTrainingId(trainingId: Int): List<RepetitionExercise>
    @Query("SELECT * FROM RepetitionExercise WHERE exerciseId = :exerciseId")
    fun getRepetitionExerciseById(exerciseId: Int): RepetitionExercise
    @Insert
    fun addRepetitionExercise(exercise: RepetitionExercise)
    @Query("DELETE FROM RepetitionExercise where exerciseId = :exerciseId")
    fun deleteRepetitionExercise(exerciseId: Int)
    @Query("DELETE FROM RepetitionExercise WHERE trainingId = :trainingId")
    fun deleteRepetitionExerciseByTrainingId(trainingId: Int)
    @Query("UPDATE RepetitionExercise SET positionInTraining = positionInTraining + 1 WHERE trainingId = :trainingId AND positionInTraining >= :newExerciseNumber")
    fun updatePositionWhileAdding(trainingId: Int, newExerciseNumber: Int)

    @Query("UPDATE RepetitionExercise SET positionInTraining = positionInTraining - 1 WHERE trainingId = :trainingId AND positionInTraining >= :newExerciseNumber")
    fun updatePositionWhileDeleting(trainingId: Int, newExerciseNumber: Int)
}