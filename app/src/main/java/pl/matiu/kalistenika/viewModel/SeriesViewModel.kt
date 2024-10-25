package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.model.training.TrainingModel

class SeriesViewModel : ViewModel() {

    private val exerciseDatabaseService: ExerciseDatabaseService = ExerciseDatabaseService()

    private var _exerciseList = MutableStateFlow<List<SeriesInterface>?>(null)
    val exerciseList = _exerciseList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllTimeAndRepetitionSeriesById()
    }

    fun getAllTimeSeriesByTrainingId() {
        ConsoleLogger().log("series view model", "downloading time series data")
        viewModelScope.launch {
            _isLoading.value = true
            _exerciseList.value = ExerciseDatabaseService().getAllTimeExercise()//implementacja ExerciseDatabaseService - DI
            _isLoading.value = false
        }
    }

    fun getAllRepetitionSeriesByTrainingId() {
        ConsoleLogger().log("series view model", "downloading time series data")
        viewModelScope.launch {
            _exerciseList.value = ExerciseDatabaseService().getAllRepetitionExercise()//implementacja ExerciseDatabaseService - DI
        }
    }

    fun getAllTimeAndRepetitionSeriesById() {
        ConsoleLogger().log("series view model", "downloading all data")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                _exerciseList.value = exerciseDatabaseService.getAllRepetitionExercise() + exerciseDatabaseService.getAllTimeExercise()//implementacja ExerciseDatabaseService - DI
                _isLoading.value = false
            }
        }
    }

    fun addTimeSeries(timeExercise: TimeExercise) {
        ThreadIdLogger(ConsoleLogger()).log("series view model", "adding new training ${timeExercise.timeExerciseName}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exerciseDatabaseService.addTimeSeries(timeExercise)
                exerciseDatabaseService.getAllSeries()
            }
        }
    }

    fun addRepetitionSeries(repetitionExercise: RepetitionExercise) {
        ThreadIdLogger(ConsoleLogger()).log("series view model", "adding new training ${repetitionExercise.repetitionExerciseName}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exerciseDatabaseService.addRepetitionSeries(repetitionExercise)
                exerciseDatabaseService.getAllSeries()
            }
        }
    }

    fun editTimeSeries(timeExercise: TimeExercise) {
        ThreadIdLogger(ConsoleLogger()).log("series view model", "edit series ${timeExercise.timeExerciseName}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exerciseDatabaseService.updateTimeSeries(timeExercise)
                exerciseDatabaseService.getAllSeries()
            }
        }
    }

    fun editRepetitionSeries(repetitionExercise: RepetitionExercise) {
        ThreadIdLogger(ConsoleLogger()).log("series view model", "edit series ${repetitionExercise.repetitionExerciseName}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exerciseDatabaseService.updateRepetitionSeries(repetitionExercise)
                exerciseDatabaseService.getAllSeries()
            }
        }
    }

    fun deleteTimeSeries(timeExercise: TimeExercise) {
        ThreadIdLogger(ConsoleLogger()).log("series view model", "deleting series ${timeExercise.timeExerciseName}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exerciseDatabaseService.deleteTimeSeries(timeExercise)
                exerciseDatabaseService.getAllSeries()
            }
        }
    }

    fun deleteRepetitionSeries(repetitionExercise: RepetitionExercise) {
        ThreadIdLogger(ConsoleLogger()).log("series view model", "deleting series ${repetitionExercise.repetitionExerciseName}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                exerciseDatabaseService.deleteRepetitionSeries(repetitionExercise)
                exerciseDatabaseService.getAllSeries()
            }
        }
    }

//    fun deleteTraining(trainingModel: TrainingModel) {
//        ThreadIdLogger(ConsoleLogger()).log("training view model", "deleting training ${trainingModel.name}")
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                exerciseDatabaseService.deleteTraining(trainingModel)
//                exerciseDatabaseService.getAllTraining()
//            }
//        }
//    }
}

