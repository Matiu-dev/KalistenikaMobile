package pl.matiu.kalistenika.room

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.SeriesInterface
import pl.matiu.kalistenika.trainingModel.TimeExercise

class ExerciseDatabaseService {

    private val timeSeriesDao = MainApplication.timeSeriesDatabase.getTimeSeriesDao()
    private val repetitionSeriesDao = MainApplication.repetitionSeriesDatabase.getRepetitionSeriesDao()

    fun getAllSeries(): List<SeriesInterface> {
        return timeSeriesDao.getAllTimeExercise() + repetitionSeriesDao.getAllRepetitionExercise()
    }
    fun addRepetitionSeries(repetitionExercise: RepetitionExercise) {
//        viewModelScope.launch {

            repetitionExercise.trainingId?.let {
                repetitionExercise.positionInTraining?.let { it1 ->
                    repetitionSeriesDao.updatePositionWhileAdding(
                        trainingId = it,
                        newExerciseNumber = it1
                    )
                }
            }

            repetitionExercise.trainingId?.let {
                repetitionExercise.positionInTraining?.let { it1 ->
                    timeSeriesDao.updatePositionWhileAdding(
                        trainingId = it,
                        newExerciseNumber = it1
                    )
                }
            }

            repetitionSeriesDao.addRepetitionExercise(repetitionExercise)
//        }

    }

    fun addTimeSeries(timeExercise: TimeExercise) {
//        viewModelScope.launch {
            //ustawia kolejnosc wszystkich cwiczen dla treningu

            timeExercise.trainingId?.let {
                timeExercise.positionInTraining?.let { it1 ->
                    repetitionSeriesDao.updatePositionWhileAdding(
                        trainingId = it,
                        newExerciseNumber = it1
                    )
                }
            }

            timeExercise.trainingId?.let {
                timeExercise.positionInTraining?.let { it1 ->
                    timeSeriesDao.updatePositionWhileAdding(
                        trainingId = it,
                        newExerciseNumber = it1
                    )
                }
            }


            //ustawia kolejnosc wszystkich cwiczen dla treningu

            timeSeriesDao.addTimeExercise(timeExercise)
//        }

    }

    fun deleteRepetitionSeries(repetitionExercise: RepetitionExercise) {
//        viewModelScope.launch {

            repetitionExercise.trainingId?.let {
                repetitionExercise.positionInTraining?.let { it1 ->
                    repetitionSeriesDao.updatePositionWhileDeleting(
                        it,
                        it1
                    )
                }
            }

            repetitionExercise.trainingId?.let {
                repetitionExercise.positionInTraining?.let { it1 ->
                    timeSeriesDao.updatePositionWhileDeleting(
                        it,
                        it1
                    )
                }
            }

            repetitionSeriesDao.deleteRepetitionExercise(repetitionExercise.exerciseId)
//        }
    }

    fun deleteTimeSeries(timeExercise: TimeExercise) {
//        viewModelScope.launch {

            timeExercise.trainingId?.let {
                timeExercise.positionInTraining?.let { it1 ->
                    repetitionSeriesDao.updatePositionWhileDeleting(
                        it,
                        it1
                    )
                }
            }

            timeExercise.trainingId?.let {
                timeExercise.positionInTraining?.let { it1 ->
                    timeSeriesDao.updatePositionWhileDeleting(
                        it,
                        it1
                    )
                }
            }

            timeSeriesDao.deleteTimeExercise(timeExercise.exerciseId)
//        }
    }

    fun updateRepetitionSeries(repetitionExerciseNew: RepetitionExercise) {

        //pobieranie aktualnej obiektu repetition
        val repetitionExerciseOld: RepetitionExercise = getRepetitionSeriesById(exerciseId = repetitionExerciseNew.exerciseId)
        //delete
        deleteRepetitionSeries(repetitionExercise = repetitionExerciseOld)
        //add
        addRepetitionSeries(repetitionExercise = repetitionExerciseNew)
    }

    fun updateTimeSeries(timeExerciseNew: TimeExercise) {
        //pobieranie aktualnej obiektu repetition
        val timeExerciseOld: TimeExercise = getTimeSeriesById(exerciseId = timeExerciseNew.exerciseId)
        //delete sprawdzic ktora pozycja jest podawana / stara czy nowa (timeExercise)
        deleteTimeSeries(timeExercise = timeExerciseOld)
        //add
        addTimeSeries(timeExercise = timeExerciseNew)
    }

    fun deleteAllExerciseByTrainingId(trainingId: Int) {
        timeSeriesDao.deleteTimeExerciseByTrainingId(trainingId = trainingId)
        repetitionSeriesDao.deleteRepetitionExerciseByTrainingId(trainingId = trainingId)
    }

    fun getRepetitionSeriesById(exerciseId: Int): RepetitionExercise {
        return repetitionSeriesDao.getRepetitionExerciseById(exerciseId)
    }

    fun getTimeSeriesById(exerciseId: Int): TimeExercise {
        return timeSeriesDao.getTimeExerciseById(exerciseId)
    }
}