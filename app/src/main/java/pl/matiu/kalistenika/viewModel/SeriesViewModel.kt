package pl.matiu.kalistenika.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.model.training.TrainingModel

//TODO problem jest, bo dane nie zdaza sie zaktualizowac przed wyrenderowaniem ekranu cwiczen,
//przez co dane sa nieaktualne, ale po odswiezeniu dane sie zgadzaja
class SeriesViewModel : ViewModel() {

    private val exerciseDatabaseService: ExerciseDatabaseService = ExerciseDatabaseService()

    private var _exerciseList = MutableStateFlow<List<SeriesInterface>?>(null)
    val exerciseList = _exerciseList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _repetition_exercise = MutableStateFlow<RepetitionExercise?>(null)
    val repetitionExercise = _repetition_exercise.asStateFlow()

    private var _time_exercise = MutableStateFlow<TimeExercise?>(null)
    val timeExercise = _time_exercise.asStateFlow()

    init {
        getAllTimeAndRepetitionSeriesById()
    }

    fun getTimeSeriesByExerciseId(exerciseId: Int) {
        ConsoleLogger().log("series view model", "downloading time series data")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                _time_exercise.value = ExerciseDatabaseService().getAllTimeExercise()
                    .find { it.timeExerciseId == exerciseId }!!//implementacja ExerciseDatabaseService - DI
                _isLoading.value = false
            }
        }
    }

    fun getRepetitionSeriesByExerciseId(exerciseId: Int) {
        ConsoleLogger().log("series view model", "downloading repetition series data")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                _repetition_exercise.value = ExerciseDatabaseService().getAllRepetitionExercise()
                    .find { it.repetitionExerciseId == exerciseId }!!//implementacja ExerciseDatabaseService - DI

                _isLoading.value = false
            }
        }
    }

    fun getAllTimeAndRepetitionSeriesById() {
        ConsoleLogger().log("series view model", "downloading all data")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                _exerciseList.value =
                    exerciseDatabaseService.getAllRepetitionExercise() + exerciseDatabaseService.getAllTimeExercise()//implementacja ExerciseDatabaseService - DI
                _isLoading.value = false
            }
        }
    }

    fun addTimeSeries(timeExercise: TimeExercise) {
        ThreadIdLogger(ConsoleLogger()).log(
            "series view model",
            "adding new training ${timeExercise.timeExerciseName}"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                exerciseDatabaseService.addTimeSeries(timeExercise)
                exerciseDatabaseService.getAllSeries()
                _isLoading.value = false
            }
        }
    }

    fun addRepetitionSeries(repetitionExercise: RepetitionExercise) {
        ThreadIdLogger(ConsoleLogger()).log(
            "series view model",
            "adding new training ${repetitionExercise.repetitionExerciseName}"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                exerciseDatabaseService.addRepetitionSeries(repetitionExercise)
                exerciseDatabaseService.getAllSeries()
                _isLoading.value = false
            }
        }
    }

    fun editTimeSeries(timeExercise: TimeExercise) {
        ThreadIdLogger(ConsoleLogger()).log(
            "series view model",
            "edit series ${timeExercise.timeExerciseName}"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                exerciseDatabaseService.updateTimeSeries(timeExercise)
                exerciseDatabaseService.getAllSeries()
                _isLoading.value = false
            }
        }
    }

    fun editRepetitionSeries(repetitionExercise: RepetitionExercise) {
        ThreadIdLogger(ConsoleLogger()).log(
            "series view model",
            "edit series ${repetitionExercise.repetitionExerciseName}"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                exerciseDatabaseService.updateRepetitionSeries(repetitionExercise)
                exerciseDatabaseService.getAllSeries()
                _isLoading.value = false
            }
        }
    }

    fun deleteTimeSeries(timeExercise: TimeExercise) {
        ThreadIdLogger(ConsoleLogger()).log(
            "series view model",
            "deleting series ${timeExercise.timeExerciseName}"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                exerciseDatabaseService.deleteTimeSeries(timeExercise)
//                exerciseDatabaseService.getAllSeries()
                _isLoading.value = false
            }
        }
    }

    fun deleteRepetitionSeries(repetitionExercise: RepetitionExercise) {
        ThreadIdLogger(ConsoleLogger()).log(
            "series view model",
            "deleting series ${repetitionExercise.repetitionExerciseName}"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                exerciseDatabaseService.deleteRepetitionSeries(repetitionExercise)
//                exerciseDatabaseService.getAllSeries()
                _isLoading.value = false
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

