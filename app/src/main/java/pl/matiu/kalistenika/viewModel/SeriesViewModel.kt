package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.SeriesInterface
import pl.matiu.kalistenika.trainingModel.TimeExercise

class SeriesViewModel: ViewModel() {

    private val timeSeriesDao = MainApplication.timeSeriesDatabase.getTimeSeriesDao()
    private val repetitionSeriesDao = MainApplication.repetitionSeriesDatabase.getRepetitionSeriesDao()

    private var _exerciseList = MutableStateFlow<List<SeriesInterface>?>(null)
    val exerciseList = _exerciseList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllTimeAndRepetitionSeriesById()
    }

    fun getAllTimeAndRepetitionSeriesById() {
        _isLoading.value = true
//        _exerciseList.value = timeSeriesDao.getAllTimeExercise() + repetitionSeriesDao.getAllRepetitionExercise()
        _exerciseList.value = ExerciseDatabaseService().getAllSeries()
        _isLoading.value = false
    }
}

