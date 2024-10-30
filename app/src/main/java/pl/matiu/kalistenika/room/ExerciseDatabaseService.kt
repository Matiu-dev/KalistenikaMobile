package pl.matiu.kalistenika.room

import androidx.room.Transaction
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.model.training.RepetitionAndTimeExercise
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise
import javax.inject.Inject

class ExerciseDatabaseService @Inject constructor(private val exerciseDao: ExerciseDao) {

    fun getAllSeries(): List<RepetitionAndTimeExercise> {
        return exerciseDao.getAllExercise()
    }

    fun getAllRepetitionExercise(): List<RepetitionExercise> {
        return exerciseDao.getAllRepetitionExercise()
    }

    fun getAllTimeExercise(): List<TimeExercise> {
        return exerciseDao.getAllTimeExercise()
    }

    @Transaction
    fun addRepetitionSeries(repetitionExercise: RepetitionExercise) {

        repetitionExercise.trainingId?.let {
            repetitionExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileAddingInRepetitionExercise(
                    trainingId = it,
                    newExerciseNumber = it1
                )
            }
        }

        repetitionExercise.trainingId?.let {
            repetitionExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileAddingInTimeExercise(
                    trainingId = it,
                    newExerciseNumber = it1
                )
            }
        }

        exerciseDao.addRepetitionExercise(repetitionExercise)
    }

    @Transaction
    fun addTimeSeries(timeExercise: TimeExercise) {

        timeExercise.trainingId?.let {
            timeExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileAddingInRepetitionExercise(
                    trainingId = it,
                    newExerciseNumber = it1
                )
            }
        }

        timeExercise.trainingId?.let {
            timeExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileAddingInTimeExercise(
                    trainingId = it,
                    newExerciseNumber = it1
                )
            }
        }

        exerciseDao.addTimeExercise(timeExercise)
    }

    @Transaction
    fun deleteRepetitionSeries(repetitionExercise: RepetitionExercise) {

        repetitionExercise.trainingId?.let {
            repetitionExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileDeletingInRepetitionExercise(
                    it,
                    it1
                )
            }
        }

        repetitionExercise.trainingId?.let {
            repetitionExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileDeletingInTimeExercise(
                    it,
                    it1
                )
            }
        }

        exerciseDao.deleteRepetitionExercise(repetitionExercise.repetitionExerciseId)
    }

    @Transaction
    fun deleteTimeSeries(timeExercise: TimeExercise) {

        timeExercise.trainingId?.let {
            timeExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileDeletingInRepetitionExercise(
                    it,
                    it1
                )
            }
        }

        timeExercise.trainingId?.let {
            timeExercise.positionInTraining?.let { it1 ->
                exerciseDao.updatePositionWhileDeletingInTimeExercise(
                    it,
                    it1
                )
            }
        }

        exerciseDao.deleteTimeExercise(timeExercise.timeExerciseId)
    }

    @Transaction
    fun updateRepetitionSeries(repetitionExerciseNew: RepetitionExercise) {

        //pobieranie aktualnej obiektu repetition
        val repetitionExerciseOld: RepetitionExercise =
            getRepetitionSeriesById(exerciseId = repetitionExerciseNew.repetitionExerciseId)
        //delete
        deleteRepetitionSeries(repetitionExercise = repetitionExerciseOld)
        //add
        addRepetitionSeries(repetitionExercise = repetitionExerciseNew)
    }

    @Transaction
    fun updateTimeSeries(timeExerciseNew: TimeExercise) {
        //pobieranie aktualnej obiektu repetition
        val timeExerciseOld: TimeExercise =
            getTimeSeriesById(exerciseId = timeExerciseNew.timeExerciseId)
        //delete sprawdzic ktora pozycja jest podawana / stara czy nowa (timeExercise)
        deleteTimeSeries(timeExercise = timeExerciseOld)
        //add
        addTimeSeries(timeExercise = timeExerciseNew)
    }

    @Transaction
    fun deleteAllExerciseByTrainingId(trainingId: Int) {
        exerciseDao.deleteTimeExerciseByTrainingId(trainingId = trainingId)
        exerciseDao.deleteRepetitionExerciseByTrainingId(trainingId = trainingId)
    }

    fun getRepetitionSeriesById(exerciseId: Int): RepetitionExercise {
        return exerciseDao.getRepetitionExerciseById(exerciseId)
    }

    fun getTimeSeriesById(exerciseId: Int): TimeExercise {
        return exerciseDao.getTimeExerciseById(exerciseId)
    }
}