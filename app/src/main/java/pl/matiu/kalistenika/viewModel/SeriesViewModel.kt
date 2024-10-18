package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.model.training.SeriesInterface

class SeriesViewModel : ViewModel() {

    private var _exerciseList = MutableStateFlow<List<SeriesInterface>?>(null)
    val exerciseList = _exerciseList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllTimeAndRepetitionSeriesById()
    }

    fun getAllTimeAndRepetitionSeriesById() {
        ConsoleLogger().log("pobieranie dnaych", "seriesViewModel")
        viewModelScope.launch {
            _isLoading.value = true
            _exerciseList.value = ExerciseDatabaseService().getAllRepetitionExercise() + ExerciseDatabaseService().getAllTimeExercise()//implementacja ExerciseDatabaseService - DI
            _isLoading.value = false
        }
    }
}

