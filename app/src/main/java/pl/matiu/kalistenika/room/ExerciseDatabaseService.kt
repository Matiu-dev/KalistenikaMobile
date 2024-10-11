package pl.matiu.kalistenika.room

import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.model.training.RepetitionAndTimeExercise
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise

class ExerciseDatabaseService {

    private val exerciseDao = MainApplication.exerciseDatabase.getExerciseDao()
    fun getAllSeries(): List<RepetitionAndTimeExercise> {
        return exerciseDao.getAllExercise()
    }

    fun getAllRepetitionExercise(): List<RepetitionExercise> {
        return exerciseDao.getAllRepetitionExercise()
    }

    fun getAllTimeExercise(): List<TimeExercise> {
        return exerciseDao.getAllTimeExercise()
    }

    fun addRepetitionSeries(repetitionExercise: RepetitionExercise) {
//        viewModelScope.launch {

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
//        }

    }

    fun addTimeSeries(timeExercise: TimeExercise) {

        //ustawia kolejnosc wszystkich cwiczen dla treningu


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

        //ustawia kolejnosc wszystkich cwiczen dla treningu

        exerciseDao.addTimeExercise(timeExercise)

    }

    fun deleteRepetitionSeries(repetitionExercise: RepetitionExercise) {
//        viewModelScope.launch {

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
//        }
    }

    fun deleteTimeSeries(timeExercise: TimeExercise) {
//        viewModelScope.launch {

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
//        }
    }

    fun updateRepetitionSeries(repetitionExerciseNew: RepetitionExercise) {

        //pobieranie aktualnej obiektu repetition
        val repetitionExerciseOld: RepetitionExercise =
            getRepetitionSeriesById(exerciseId = repetitionExerciseNew.repetitionExerciseId)
        //delete
        deleteRepetitionSeries(repetitionExercise = repetitionExerciseOld)
        //add
        addRepetitionSeries(repetitionExercise = repetitionExerciseNew)
    }

    fun updateTimeSeries(timeExerciseNew: TimeExercise) {
        //pobieranie aktualnej obiektu repetition
        val timeExerciseOld: TimeExercise =
            getTimeSeriesById(exerciseId = timeExerciseNew.timeExerciseId)
        //delete sprawdzic ktora pozycja jest podawana / stara czy nowa (timeExercise)
        deleteTimeSeries(timeExercise = timeExerciseOld)
        //add
        addTimeSeries(timeExercise = timeExerciseNew)
    }

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