package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.SeriesInterface
import pl.matiu.kalistenika.trainingModel.TimeExercise

class SeriesViewModel: ViewModel() {

    val timeSeriesDao = MainApplication.timeSeriesDatabase.getTimeSeriesDao()
    val repetitionSeriesDao = MainApplication.repetitionSeriesDatabase.getRepetitionSeriesDao()

    private var _exerciseList = MutableStateFlow<List<SeriesInterface>?>(null)
    val exerciseList = _exerciseList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllTimeAndRepetitionSeriesById()
    }

    fun getAllTimeAndRepetitionSeriesById() {
        _isLoading.value = true
        _exerciseList.value = timeSeriesDao.getAllTimeExercise() + repetitionSeriesDao.getAllRepetitionExercise()
        _isLoading.value = false
    }

    fun getRepetitionSeriesById(exerciseId: Int): RepetitionExercise {
        return repetitionSeriesDao.getRepetitionExerciseById(exerciseId)
    }

    fun getTimeSeriesById(exerciseId: Int): TimeExercise {
        return timeSeriesDao.getTimeExerciseById(exerciseId)
    }

    fun addRepetitionSeries(repetitionExercise: RepetitionExercise) {
        viewModelScope.launch {

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
        }

    }

    fun addTimeSeries(timeExercise: TimeExercise) {
        viewModelScope.launch {
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
        }

    }

    fun deleteRepetitionSeries(repetitionExercise: RepetitionExercise) {
        viewModelScope.launch {

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
        }
    }

    fun deleteTimeSeries(timeExercise: TimeExercise) {
        viewModelScope.launch {

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
        }
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
}

