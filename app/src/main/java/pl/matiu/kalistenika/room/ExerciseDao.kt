package pl.matiu.kalistenika.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.matiu.kalistenika.trainingModel.RepetitionAndTimeExercise
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.SeriesInterface
import pl.matiu.kalistenika.trainingModel.TimeExercise

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM RepetitionExercise")
    fun getAllRepetitionExercise(): List<RepetitionExercise>

    @Query("SELECT * FROM TimeExercise")
    fun getAllTimeExercise(): List<TimeExercise>

    @Query("SELECT * FROM TimeExercise WHERE timeExerciseId = :exerciseId")
    fun getTimeExerciseById(exerciseId: Int): TimeExercise

    @Query("SELECT * FROM RepetitionExercise WHERE repetitionExerciseId = :exerciseId")
    fun getRepetitionExerciseById(exerciseId: Int): RepetitionExercise

    @Query("SELECT RepetitionExercise.*, TimeExercise.* FROM RepetitionExercise INNER JOIN TimeExercise " +
            "ON RepetitionExercise.repetitionTrainingId = TimeExercise.timeTrainingId")
    fun getAllExercise(): List<RepetitionAndTimeExercise>

    @Query("SELECT RepetitionExercise.*, TimeExercise.* FROM RepetitionExercise INNER JOIN TimeExercise " +
            "ON RepetitionExercise.repetitionTrainingId = TimeExercise.timeTrainingId where RepetitionExercise.repetitionTrainingId = :trainingId")
    fun getAllExerciseByTrainingId(trainingId: Int): List<RepetitionAndTimeExercise>

    @Insert
    fun addRepetitionExercise(exercise: RepetitionExercise)
    @Query("DELETE FROM RepetitionExercise where repetitionExerciseId = :exerciseId")
    fun deleteRepetitionExercise(exerciseId: Int)
    @Query("DELETE FROM RepetitionExercise WHERE repetitionTrainingId = :trainingId")
    fun deleteRepetitionExerciseByTrainingId(trainingId: Int)
    @Query("UPDATE RepetitionExercise SET repetitionExercisePositionInTraining = repetitionExercisePositionInTraining + 1 WHERE repetitionTrainingId = :trainingId AND repetitionExercisePositionInTraining >= :newExerciseNumber")
    fun updatePositionWhileAddingInRepetitionExercise(trainingId: Int, newExerciseNumber: Int)
    @Query("UPDATE RepetitionExercise SET repetitionExercisePositionInTraining = repetitionExercisePositionInTraining - 1 WHERE repetitionTrainingId = :trainingId AND repetitionExercisePositionInTraining >= :newExerciseNumber")
    fun updatePositionWhileDeletingInRepetitionExercise(trainingId: Int, newExerciseNumber: Int)

    @Insert
    fun addTimeExercise(exercise: TimeExercise)
    @Query("DELETE FROM TimeExercise where timeExerciseId = :exerciseId")
    fun deleteTimeExercise(exerciseId: Int)
    @Query("DELETE FROM TimeExercise WHERE timeTrainingId = :trainingId")
    fun deleteTimeExerciseByTrainingId(trainingId: Int)
    @Query("UPDATE TimeExercise SET timeExercisePositionInTraining = timeExercisePositionInTraining + 1 WHERE timeTrainingId = :trainingId AND timeExercisePositionInTraining >= :newExerciseNumber")
    fun updatePositionWhileAddingInTimeExercise(trainingId: Int, newExerciseNumber: Int)
    @Query("UPDATE TimeExercise SET timeExercisePositionInTraining = timeExercisePositionInTraining - 1 WHERE timeTrainingId = :trainingId AND timeExercisePositionInTraining >= :newExerciseNumber")
    fun updatePositionWhileDeletingInTimeExercise(trainingId: Int, newExerciseNumber: Int)
}